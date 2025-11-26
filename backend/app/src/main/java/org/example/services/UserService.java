package org.example.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.example.dto.ProfileDto;
import org.example.dto.QuoteDto;
import org.example.dto.TagDto;
import org.example.dto.requests.auth.LoginRequest;
import org.example.dto.requests.auth.RegisterRequest;
import org.example.exceptions.IllegalRequestArgumentsException;
import org.example.exceptions.InvalidUsernamePasswordCombinationException;
import org.example.exceptions.TagNotFoundException;
import org.example.exceptions.UserNotFoundException;
import org.example.exceptions.UsernameAlreadyUsedException;
import org.example.models.Quote;
import org.example.models.QuoteTag;
import org.example.models.Tag;
import org.example.models.User;
import org.example.repositories.QuoteRepository;
import org.example.repositories.TagRepository;
import org.example.repositories.UserRepository;
import org.example.util.PasswordHasher;
import org.example.util.Validator;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final QuoteRepository quoteRepository;
    private final PasswordHasher passwordHasher;

    public UserService(UserRepository userRepository, TagRepository tagRepository, QuoteRepository quoteRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.quoteRepository = quoteRepository;
        this.passwordHasher = passwordHasher;
    }

    public void registerUser(RegisterRequest request) {
        if (!Validator.validatePassword(request.getPassword()) || !Validator.validateUsername(request.getUsername())) {
            throw new IllegalRequestArgumentsException("username или password");
        }

        Optional<User> userOptional = userRepository.findByUsername(request.getUsername());
        if (userOptional.isPresent()) {
            throw new UsernameAlreadyUsedException();
        }

        User user = new User(request.getUsername(), passwordHasher.hashPassword(request.getPassword()));
        userRepository.saveAndFlush(user);
    }

    public User authUser(LoginRequest request) {
        if (!Validator.validatePassword(request.getPassword()) || !Validator.validateUsername(request.getUsername())) {
            throw new IllegalRequestArgumentsException("username или password");
        }

        Optional<User> userOptional = userRepository.findByUsername(request.getUsername());
        if (!userOptional.isPresent() || !passwordHasher.checkPassword(request.getPassword(), userOptional.get().getPasswordHash())) {
            throw new InvalidUsernamePasswordCombinationException();
        }
        return userOptional.get();
    }

    public List<QuoteDto> getUserSavedQuotes(Integer userId, Integer startIndex) {
        List<Quote> quotes = quoteRepository.getSavedUserQuotes(userId, startIndex);
        List<QuoteDto> quoteDtos = new ArrayList<QuoteDto>();
        quotes.forEach((Quote q) -> {
            List<QuoteTag> tags = q.getTags();
            List<TagDto> tagDtos = new ArrayList<TagDto>();
            tags.forEach((QuoteTag t) -> {
                Optional<Tag> tag = tagRepository.findById(t.getTag().getId());
                if (!tag.isPresent()) {
                    throw new TagNotFoundException("Тег с id = " + t.getId() + " не найден");
                }
                tagDtos.add(new TagDto(tag.get().getId(), tag.get().getName()));
            });
            quoteDtos.add(new QuoteDto(q.getId(), q.getAuthor().getName(), tagDtos, q.getText(), q.getContext()));
        });
        return quoteDtos;
    }

    public ProfileDto getProfile(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("Пользователь с id = " + userId + " не найден");
        }
        User user = userOptional.get();
        return new ProfileDto(user.getUsername(), user.getPrivilegeLevel());
    }

    public boolean isAdmin(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("Пользователь с id = " + userId + " не найден");
        }
        User user = userOptional.get();
        return user.getPrivilegeLevel() >= 5;
    }
}
