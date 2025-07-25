package com.gamesup.api.service;

import com.gamesup.api.dto.author.AuthorDto;
import com.gamesup.api.dto.author.CreateAuthorDto;
import com.gamesup.api.dto.author.UpdateAuthorDto;
import com.gamesup.api.exception.ResourceNotFoundException;
import com.gamesup.api.model.Author;
import com.gamesup.api.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(a -> new AuthorDto(a.getId(), a.getName()))
                .toList();
    }

    public AuthorDto getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));
        return new AuthorDto(author.getId(), author.getName());
    }

    public AuthorDto createAuthor(CreateAuthorDto dto) {
        Author author = new Author();
        author.setName(dto.name());
        Author saved = authorRepository.save(author);
        return new AuthorDto(saved.getId(), saved.getName());
    }

    public AuthorDto updateAuthor(Long id, UpdateAuthorDto dto) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));
        author.setName(dto.name());
        Author updated = authorRepository.save(author);
        return new AuthorDto(updated.getId(), updated.getName());
    }

    public void deleteAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));
        authorRepository.deleteById(author.getId());
    }
}
