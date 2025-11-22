package org.example.services;

import org.example.dto.admin.CreateAuthorRequest;
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
}
