package com.gamesup.api.controller.author;

import com.gamesup.api.dto.author.AuthorDto;
import com.gamesup.api.dto.author.CreateAuthorDto;
import com.gamesup.api.dto.author.UpdateAuthorDto;
import com.gamesup.api.response.ApiResponse;
import com.gamesup.api.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/authors")
public class AuthorAdminController {

    private final AuthorService authorService;

    public AuthorAdminController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AuthorDto>> createAuthor(@Valid @RequestBody CreateAuthorDto dto) {
        AuthorDto created = authorService.createAuthor(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED.value(), "Author created successfully", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AuthorDto>> updateAuthor(@PathVariable Long id, @Valid @RequestBody UpdateAuthorDto dto) {
        AuthorDto updated = authorService.updateAuthor(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Author updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Author deleted successfully"));
    }
}
