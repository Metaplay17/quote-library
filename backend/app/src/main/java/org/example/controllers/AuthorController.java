package org.example.controllers;

import java.util.List;

import org.example.dto.AuthorDto;
import org.example.dto.responses.authors.GetAllAuthorsResponse;
import org.example.services.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;
    private final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/")
    public ResponseEntity<GetAllAuthorsResponse> getAuthors() {
        logger.info("Запрос на получение всех авторов");
        List<AuthorDto> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(new GetAllAuthorsResponse(HttpStatus.OK, "OK", authors));
    }
    
}
