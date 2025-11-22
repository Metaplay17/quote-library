package org.example.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.example.dto.QuoteDto;
import org.example.dto.TagDto;
import org.example.dto.admin.CreateQuoteRequest;
import org.example.exceptions.IllegalRequestException;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class QuoteService {
    private final QuoteRepository quoteRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final UserQuoteRepository userQuoteRepository;

    public QuoteService(QuoteRepository quoteRepository, TagRepository tagRepository, UserRepository userRepository, UserQuoteRepository userQuoteRepository) {
        this.quoteRepository = quoteRepository;
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
        this.userQuoteRepository = userQuoteRepository;
    }

    public List<QuoteDto> findByAuthorAndTag(List<Integer> tagsId, List<Integer> authorsId, Integer startIndex) {
        List<Quote> quotes = quoteRepository.findQuotesByAuthorAndTag(tagsId, authorsId, startIndex);
        List<QuoteDto> quoteDtos = new ArrayList<QuoteDto>();
        quotes.forEach((Quote q) -> {
            List<QuoteTag> tags = q.getTags();
            List<TagDto> tagDtos = new ArrayList<TagDto>();
            tags.forEach((QuoteTag t) -> {
                Optional<Tag> tag = tagRepository.findById(t.getTag().getId());
                if (!tag.isPresent()) {
                    throw new TagNotFoundException("Тег с id = " + t.getTag().getId() + " не найден");
                }
                tagDtos.add(new TagDto(tag.get().getId(), tag.get().getName()));
            });
            quoteDtos.add(new QuoteDto(q.getId(), q.getAuthor().getName(), tagDtos, q.getText(), q.getContext()));
        });
        return quoteDtos;
    }

    public void addQuoteToUser(Integer userId, Integer quoteId) {
        try {
            User user = userRepository.getReferenceById(userId);
            Quote quote = quoteRepository.getReferenceById(quoteId);
            UserQuote note = new UserQuote(user, quote);
            userQuoteRepository.save(note);
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalRequestException("Цитата уже добавлена");
        }
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
            quoteDtos.add(new QuoteDto(q.getId(), q.getAuthor().getName(), tagDtos, q.getText(), q.getContext()));
        });
        return quoteDtos;
    }

    public List<QuoteDto> searchQuotes(String pattern, Integer startIndex) {
        List<Quote> quotes = quoteRepository.searchQuotes(pattern, startIndex);
        List<QuoteDto> quoteDtos = new ArrayList<QuoteDto>();
        quotes.forEach((Quote q) -> {
            List<QuoteTag> tags = q.getTags();
            List<TagDto> tagDtos = new ArrayList<TagDto>();
            tags.forEach((QuoteTag t) -> {
                Optional<Tag> tag = tagRepository.findById(t.getTag().getId());
                if (!tag.isPresent()) {
                    throw new TagNotFoundException("Тег с id = " + t.getTag().getId() + " не найден");
                }
                tagDtos.add(new TagDto(tag.get().getId(), tag.get().getName()));
            });
            quoteDtos.add(new QuoteDto(q.getId(), q.getAuthor().getName(), tagDtos, q.getText(), q.getContext()));
        });
        return quoteDtos;
    }

    public void createQuote(CreateQuoteRequest request) {
        quoteRepository.createQuotes(request.getText(), request.getAuthorId(), request.getContext(), request.getTags());
    }

    public void deleteQuote(Integer quoteId) {
        quoteRepository.deleteQuote(quoteId);
    }
}
