package org.example.controllers;

import java.util.List;

import org.example.dto.DefaultResponse;
import org.example.dto.TagDto;
import org.example.dto.admin.CreateTagRequest;
import org.example.dto.responses.quotes.TagsListResponse;
import org.example.exceptions.AccessDeniedException;
import org.example.services.TagService;
import org.example.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;
    private final UserService userService;

    public TagController(TagService tagService, UserService userService) {
        this.tagService = tagService;
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<TagsListResponse> getTags() {
        List<TagDto> tagDtos = tagService.getAllTags();
        return ResponseEntity.ok(new TagsListResponse(HttpStatus.OK, "OK", tagDtos));
    }

    @PostMapping("")
    public ResponseEntity<DefaultResponse> addTag(Authentication authentication, @RequestBody @Valid CreateTagRequest request) {
        Integer userId = (Integer)authentication.getPrincipal();
        if (!userService.isAdmin(userId)) {
            throw new AccessDeniedException("Доступ только для администраторов");
        }
        tagService.createTag(request);
        return ResponseEntity.ok(new DefaultResponse(HttpStatus.OK, "OK"));
    }
    
}
