package org.example.controllers;

import org.example.dto.DefaultResponse;
import org.example.dto.requests.user.AddQuoteRequest;
import org.example.dto.requests.user.DeleteQuoteRequest;
import org.example.dto.responses.user.UserSavedQuotesResponse;
import org.example.services.QuoteService;
import org.example.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<UserSavedQuotesResponse> getMethodName(Authentication authentication) {
        Integer userId = (Integer)authentication.getPrincipal();
        // TODO
        return null;
    }

    @PostMapping("/add")
    public ResponseEntity<DefaultResponse> addQuoteToUser(@RequestBody AddQuoteRequest request) {
        //TODO
        return null;
    }

    @PostMapping("/delete")
    public ResponseEntity<DefaultResponse> deleteQuoteFromUser(@RequestBody DeleteQuoteRequest request) {
        //TODO
        return null;
    }
    
    
}
