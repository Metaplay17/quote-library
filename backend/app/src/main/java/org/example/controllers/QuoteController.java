package org.example.controllers;

import java.util.List;

import org.example.dto.DefaultResponse;
import org.example.dto.QuoteDto;
import org.example.dto.admin.CreateQuoteRequest;
import org.example.dto.requests.quotes.FindByPatternAndAuthorsAndTagsRequest;
import org.example.dto.responses.quotes.QuotesListResponse;
import org.example.exceptions.AccessDeniedException;
import org.example.services.QuoteService;
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
@RequestMapping("/quotes")
public class QuoteController {
    private final QuoteService quoteService;
    private final UserService userService;

    public QuoteController(QuoteService quoteService, UserService userService) {
        this.quoteService = quoteService;
        this.userService = userService;
    }

    @PostMapping("/find")
    public ResponseEntity<QuotesListResponse> findQuotes(Authentication authentication, @Valid @RequestBody FindByPatternAndAuthorsAndTagsRequest request) {
        Integer userId = (Integer)authentication.getPrincipal();
        List<QuoteDto> quotes = quoteService.findByPatternAndAuthorsAndTags(false, userId, request.getPattern(), request.getTagsId(), request.getAuthorsId(), request.getStartIndex());
        return ResponseEntity.ok(new QuotesListResponse(HttpStatus.OK, "OK", quotes));
    }

    @GetMapping("/random")
    public ResponseEntity<QuotesListResponse> getRandomNotAddedQuotes(Authentication authentication) {
        Integer userId = (Integer)authentication.getPrincipal();
        List<QuoteDto> quotes = quoteService.getRandomNotAddedQuotes(userId);
        return ResponseEntity.ok(new QuotesListResponse(HttpStatus.OK, "OK", quotes));
    }

    @PostMapping("")
    public ResponseEntity<DefaultResponse> createQuote(Authentication authentication, @RequestBody @Valid CreateQuoteRequest request) {
        Integer userId = (Integer)authentication.getPrincipal();
        if (!userService.isAdmin(userId)) {
            throw new AccessDeniedException("Доступ только для администраторов");
        }
        quoteService.createQuote(request);
        return ResponseEntity.ok(new DefaultResponse(HttpStatus.OK, "OK"));
    }
}
