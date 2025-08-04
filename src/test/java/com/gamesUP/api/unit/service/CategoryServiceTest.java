package com.gamesup.api.unit.service;

import com.gamesup.api.dto.category.CategoryDto;
import com.gamesup.api.dto.category.CreateCategoryDto;
import com.gamesup.api.dto.category.UpdateCategoryDto;
import com.gamesup.api.exception.ResourceNotFoundException;
import com.gamesup.api.model.Category;
import com.gamesup.api.repository.CategoryRepository;
import com.gamesup.api.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    private CategoryRepository categoryRepository;
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    void getAllCategories_shouldReturnList() {
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(
                new Category(1L, "Category1", null),
                new Category(2L, "Category2", null)
        ));

        List<CategoryDto> result = categoryService.getAllCategories();

        assertEquals(2, result.size());
        assertEquals("Category1", result.getFirst().name());
    }

    @Test
    void getCategoryById_shouldReturnCategory() {
        Category category = new Category(1L, "Category1", null);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        CategoryDto result = categoryService.getCategoryById(1L);

        assertEquals("Category1", result.name());
    }

    @Test
    void getCategoryById_shouldThrowIfNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.getCategoryById(1L));
    }

    @Test
    void createCategory_shouldSaveAndReturnDto() {
        CreateCategoryDto dto = new CreateCategoryDto("New Category");
        Category saved = new Category(1L, "New Category", null);

        when(categoryRepository.save(any(Category.class))).thenReturn(saved);

        CategoryDto result = categoryService.createCategory(dto);

        assertEquals("New Category", result.name());
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void updateCategory_shouldUpdateName() {
        Category existing = new Category(1L, "Old Name", null);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(categoryRepository.save(any(Category.class))).thenReturn(existing);

        UpdateCategoryDto dto = new UpdateCategoryDto("Updated Name");
        CategoryDto result = categoryService.updateCategory(1L, dto);

        assertEquals("Updated Name", result.name());
    }

    @Test
    void deleteCategory_shouldCallDelete() {
        Category existing = new Category(1L, "To Delete", null);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existing));

        categoryService.deleteCategory(1L);

        verify(categoryRepository).deleteById(1L);
    }
}
