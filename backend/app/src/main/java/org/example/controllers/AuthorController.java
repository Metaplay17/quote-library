package org.example.controllers;

import java.util.List;

import org.example.dto.AuthorDto;
import org.example.dto.DefaultResponse;
import org.example.dto.admin.CreateAuthorRequest;
import org.example.dto.responses.authors.GetAllAuthorsResponse;
import org.example.exceptions.AccessDeniedException;
import org.example.services.AuthorService;
import org.example.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    public AuthorController(AuthorService authorService, UserService userService) {
        this.authorService = authorService;
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<GetAllAuthorsResponse> getAuthors() {
        logger.info("Запрос на получение всех авторов");
        List<AuthorDto> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(new GetAllAuthorsResponse(HttpStatus.OK, "OK", authors));
    }

    @PostMapping("")
    public ResponseEntity<DefaultResponse> addAuthor(Authentication authentication, @RequestBody @Valid CreateAuthorRequest request) {
        Integer userId = (Integer)authentication.getPrincipal();
        if (!userService.isAdmin(userId)) {
            throw new AccessDeniedException("Доступ только для администраторов");
        }
        authorService.createAuthor(request);
        return ResponseEntity.ok(new DefaultResponse(HttpStatus.OK, "OK"));
    }
    
}
