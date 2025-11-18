package org.example.controllers;

import java.util.List;

import org.example.dto.QuoteDto;
import org.example.dto.requests.quotes.FindByAuthorsAndTagsRequest;
import org.example.dto.responses.quotes.QuotesListResponse;
import org.example.services.QuoteService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<QuotesListResponse> findQuotes(@RequestBody FindByAuthorsAndTagsRequest request) {
        List<QuoteDto> quotes = quoteService.findByAuthorAndTag(request.getTagsId(), request.getAuthorsId(), request.getStartIndex());
        return ResponseEntity.ok(new QuotesListResponse(HttpStatus.OK, "OK", quotes));
    }

    @GetMapping("/random")
    public ResponseEntity<QuotesListResponse> getRandomNotAddedQuotes(Authentication authentication) {
        Integer userId = (Integer)authentication.getPrincipal();
        List<QuoteDto> quotes = quoteService.getRandomNotAddedQuotes(userId);
        return ResponseEntity.ok(new QuotesListResponse(HttpStatus.OK, "OK", quotes));
    }
}
