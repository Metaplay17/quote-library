package org.example.controllers;

import java.util.List;

import org.example.dto.DefaultResponse;
import org.example.dto.QuoteDto;
import org.example.dto.requests.quotes.FindByPatternAndAuthorsAndTagsRequest;
import org.example.dto.requests.user.AddQuoteRequest;
import org.example.dto.requests.user.DeleteQuoteRequest;
import org.example.dto.responses.quotes.QuotesListResponse;
import org.example.dto.responses.user.ProfileResponse;
import org.example.services.QuoteService;
import org.example.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final QuoteService quoteService;

    public UserController(UserService userService, QuoteService quoteService) {
        this.userService = userService;
        this.quoteService = quoteService;
    }

    @GetMapping("/saved-quotes")
    public ResponseEntity<QuotesListResponse> getSavedQuotes(Authentication authentication, @Valid @RequestParam Integer startIndex) {
        Integer userId = (Integer)authentication.getPrincipal();
        List<QuoteDto> quotes = userService.getUserSavedQuotes(userId, startIndex);
        return ResponseEntity.ok(new QuotesListResponse(HttpStatus.OK, "OK", quotes));
    }

    @PostMapping("/saved-quotes/find")
    public ResponseEntity<QuotesListResponse> findSavedQuotes(Authentication authentication, @Valid @RequestBody FindByPatternAndAuthorsAndTagsRequest request) {
        Integer userId = (Integer)authentication.getPrincipal();
        List<QuoteDto> quotes = quoteService.findByPatternAndAuthorsAndTags(true, userId, request.getPattern(), request.getTagsId(), request.getAuthorsId(), request.getStartIndex());
        return ResponseEntity.ok(new QuotesListResponse(HttpStatus.OK, "OK", quotes));
    }

    @PostMapping("/add")
    public ResponseEntity<DefaultResponse> addQuoteToUser(Authentication authentication, @Valid @RequestBody AddQuoteRequest request) {
        Integer userId = (Integer)authentication.getPrincipal();
        quoteService.addQuoteToUser(userId, request.getQuoteId());
        return ResponseEntity.ok(new DefaultResponse(HttpStatus.OK, "OK"));
    }

    @PostMapping("/delete")
    public ResponseEntity<DefaultResponse> deleteQuoteFromUser(Authentication authentication, @Valid @RequestBody DeleteQuoteRequest request) {
        Integer userId = (Integer)authentication.getPrincipal();
        quoteService.deleteQuoteFromUser(userId, request.getQuoteId());
        return ResponseEntity.ok(new DefaultResponse(HttpStatus.OK, "OK"));
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponse> getProfile(Authentication authentication) {
        Integer userId = (Integer)authentication.getPrincipal();
        return ResponseEntity.ok(new ProfileResponse(HttpStatus.OK, "OK", userService.getProfile(userId)));
    }
}
