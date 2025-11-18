package org.example.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.example.dto.responses.quotes.QuoteDto;
import org.example.dto.responses.quotes.TagDto;
import org.example.exceptions.TagNotFoundException;
import org.example.models.Quote;
import org.example.models.QuoteTag;
import org.example.models.Tag;
import org.example.repositories.QuoteRepository;
import org.example.repositories.TagRepository;
import org.springframework.stereotype.Service;

@Service
public class QuoteService {
    private final QuoteRepository quoteRepository;
    private final TagRepository tagRepository;

    public QuoteService(QuoteRepository quoteRepository, TagRepository tagRepository) {
        this.quoteRepository = quoteRepository;
        this.tagRepository = tagRepository;
    }

    public List<QuoteDto> findByAuthorAndTag(List<Integer> tagsId, List<Integer> authorsId, Integer startIndex) {
        List<Quote> quotes = quoteRepository.findQuotesByAuthorAndTag(tagsId, authorsId, startIndex);
        List<QuoteDto> quoteDtos = new ArrayList<QuoteDto>();
        quotes.forEach((Quote q) -> {
            List<QuoteTag> tags = q.getTags();
            List<TagDto> tagDtos = new ArrayList<TagDto>();
            tags.forEach((QuoteTag t) -> {
                Optional<Tag> tag = tagRepository.findById(t.getId());
                if (!tag.isPresent()) {
                    throw new TagNotFoundException("Тег с id = " + t.getId() + " не найден");
                }
                tagDtos.add(new TagDto(tag.get().getId(), tag.get().getName()));
            });
            quoteDtos.add(new QuoteDto(q.getId(), q.getAuthorId(), tagDtos, q.getText(), q.getContext()));
        });
        return quoteDtos;
    }
}
