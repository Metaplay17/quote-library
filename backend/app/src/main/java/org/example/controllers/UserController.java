package org.example.controllers;

import java.util.List;

import org.example.dto.DefaultResponse;
import org.example.dto.QuoteDto;
import org.example.dto.requests.user.AddQuoteRequest;
import org.example.dto.requests.user.DeleteQuoteRequest;
import org.example.dto.responses.quotes.QuotesListResponse;
import org.example.services.QuoteService;
import org.example.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<QuotesListResponse> getSavedQuotes(Authentication authentication, @RequestParam Integer startIndex) {
        Integer userId = (Integer)authentication.getPrincipal();
        List<QuoteDto> quotes = userService.getUserSavedQuotes(userId, startIndex);
        return ResponseEntity.ok(new QuotesListResponse(HttpStatus.OK, "OK", quotes));
    }

    @PostMapping("/add")
    public ResponseEntity<DefaultResponse> addQuoteToUser(Authentication authentication, @RequestBody AddQuoteRequest request) {
        Integer userId = (Integer)authentication.getPrincipal();
        quoteService.addQuoteToUser(userId, request.getQuoteId());
        return ResponseEntity.ok(new DefaultResponse(HttpStatus.OK, "OK"));
    }

    @PostMapping("/delete")
    public ResponseEntity<DefaultResponse> deleteQuoteFromUser(Authentication authentication, @RequestBody DeleteQuoteRequest request) {
        Integer userId = (Integer)authentication.getPrincipal();
        quoteService.deleteQuoteFromUser(userId, request.getQuoteId());
        return ResponseEntity.ok(new DefaultResponse(HttpStatus.OK, "OK"));
    }
}
