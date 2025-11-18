package org.example.controllers;

import org.example.dto.requests.quotes.FindByAuthorsAndTagsRequest;
import org.example.dto.responses.quotes.AuthorQuotesResponse;
import org.example.services.QuoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/quotes")
public class QuoteController {
    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @PostMapping("/find")
    public String postMethodName(@RequestBody FindByAuthorsAndTagsRequest request) {
        //TODO
        return null;
    }

    @GetMapping("/random")
    public ResponseEntity<AuthorQuotesResponse> getRandomNotAddedQuotes(Authentication authentication) {
        Integer userId = (Integer)authentication.getPrincipal();
        // TODO
        return null;
    }
}
