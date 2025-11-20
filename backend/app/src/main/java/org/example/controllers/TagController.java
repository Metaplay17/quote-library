package org.example.controllers;

import java.util.List;

import org.example.dto.TagDto;
import org.example.dto.responses.quotes.TagsListResponse;
import org.example.services.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("")
    public ResponseEntity<TagsListResponse> getTags() {
        List<TagDto> tagDtos = tagService.getAllTags();
        return ResponseEntity.ok(new TagsListResponse(HttpStatus.OK, "OK", tagDtos));
    }
}
