package org.example.controllers;

import org.example.dto.DefaultResponse;
import org.example.dto.admin.CreateAuthorRequest;
import org.example.dto.admin.CreateQuoteRequest;
import org.example.dto.admin.CreateTagRequest;
import org.example.services.AuthorService;
import org.example.services.QuoteService;
import org.example.services.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final TagService tagService;
    private final QuoteService quoteService;
    private final AuthorService authorService;

    public AdminController(TagService tagService, QuoteService quoteService, AuthorService authorService) {
        this.quoteService = quoteService;
        this.tagService = tagService;
        this.authorService = authorService;
    }

    @PostMapping("/quotes")
    public ResponseEntity<DefaultResponse> createQuote(@RequestBody CreateQuoteRequest request) {
        quoteService.createQuote(request);
        return ResponseEntity.ok(new DefaultResponse(HttpStatus.OK, "OK"));
    }

    @DeleteMapping("/quotes")
    public ResponseEntity<DefaultResponse> deleteQuote(@RequestParam Integer quoteId) {
        quoteService.deleteQuote(quoteId);
        return ResponseEntity.ok(new DefaultResponse(HttpStatus.OK, "OK"));
    }

    @PostMapping("/tags")
    public ResponseEntity<DefaultResponse> createTag(@RequestBody CreateTagRequest request) {
        tagService.createTag(request);
        return ResponseEntity.ok(new DefaultResponse(HttpStatus.OK, "OK"));
    }

    @DeleteMapping("/tags")
    public ResponseEntity<DefaultResponse> deleteTag(@RequestParam Integer tagId) {
        tagService.deleteTag(tagId);
        return ResponseEntity.ok(new DefaultResponse(HttpStatus.OK, "OK"));
    }

    @PostMapping("/authors")
    public ResponseEntity<DefaultResponse> createAuthor(@RequestBody CreateAuthorRequest request) {
        authorService.createAuthor(request);
        return ResponseEntity.ok(new DefaultResponse(HttpStatus.OK, "OK"));
    }

    @DeleteMapping("/authors")
    public ResponseEntity<DefaultResponse> deleteAuthor(@RequestParam Integer authorId) {
        authorService.deleteAuthor(authorId);
        return ResponseEntity.ok(new DefaultResponse(HttpStatus.OK, "OK"));
    }


}
