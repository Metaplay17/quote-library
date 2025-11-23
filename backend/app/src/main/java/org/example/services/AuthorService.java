package org.example.services;

import java.util.ArrayList;
import java.util.List;

import org.example.dto.AuthorDto;
import org.example.dto.admin.CreateAuthorRequest;
import org.example.models.Author;
import org.example.repositories.AuthorRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void createAuthor(CreateAuthorRequest request) {
        authorRepository.createAuthor(request.getName());
    }

    public void deleteAuthor(Integer authorId) {
        authorRepository.deleteAuthor(authorId);
    }

    public List<AuthorDto> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        List<AuthorDto> authorDtos = new ArrayList<AuthorDto>();
        authors.forEach((Author a) -> {
            authorDtos.add(new AuthorDto(a.getId(), a.getName()));
        });
        return authorDtos;
    }
}
