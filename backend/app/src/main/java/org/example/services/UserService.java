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
import org.example.models.User;
import org.example.models.UserQuote;
import org.example.repositories.QuoteRepository;
import org.example.repositories.TagRepository;
import org.example.repositories.UserQuoteRepository;
import org.example.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final QuoteRepository quoteRepository;
    private final UserQuoteRepository userQuoteRepository;

    public UserService(UserRepository userRepository, TagRepository tagRepository, QuoteRepository quoteRepository, UserQuoteRepository userQuoteRepository) {
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.quoteRepository = quoteRepository;
        this.userQuoteRepository = userQuoteRepository;
    }

    public List<QuoteDto> getUserSavedQuotes(Integer userId, Integer startIndex) {
        List<Quote> quotes = quoteRepository.getSavedUserQuotes(userId, startIndex);
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

    public void addQuoteToUser(Integer userId, Integer quoteId) {
        User user = userRepository.getReferenceById(userId);
        Quote quote = quoteRepository.getReferenceById(quoteId);
        UserQuote note = new UserQuote(user, quote);
        userQuoteRepository.save(note);
    }

    public void deleteQuoteFromUser(Integer userId, Integer quoteId) {
        Optional<UserQuote> note = userQuoteRepository.findByUserIdAndQuoteId(userId, quoteId);
        if (note.isPresent()) {
            userQuoteRepository.delete(note.get());
            userQuoteRepository.flush();
        }
    }

    public List<QuoteDto> getRandomNotAddedQuotes(Integer userId) {
        List<Quote> quotes = quoteRepository.getRandomNotSavedUserQuotes(userId);
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
