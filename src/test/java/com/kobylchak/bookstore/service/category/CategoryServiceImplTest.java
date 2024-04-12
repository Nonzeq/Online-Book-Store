package com.kobylchak.bookstore.service.category;

import static org.mockito.Mockito.when;

import com.kobylchak.bookstore.dto.category.CategoryDto;
import com.kobylchak.bookstore.dto.category.CreateCategoryRequestDto;
import com.kobylchak.bookstore.exception.EntityNotFoundException;
import com.kobylchak.bookstore.mapper.CategoryMapper;
import com.kobylchak.bookstore.model.Category;
import com.kobylchak.bookstore.repository.category.CategoryRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void getById_WithValidId_ShouldReturnCategoryDto() {
        Long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);
        CategoryDto expected = new CategoryDto();
        expected.setId(categoryId);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(expected);

        CategoryDto actual = categoryService.getById(categoryId);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update_WithInvalidId_ShouldThrowEntityNotFoundException() {
        Long categoryId = 1L;
        when(categoryRepository.existsById(categoryId)).thenReturn(false);
        EntityNotFoundException exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> categoryService.update(categoryId, new CreateCategoryRequestDto()));
        String actualMessage = exception.getMessage();
        String expectedMessage = "Category with id: " + categoryId + ", not found";
        ;
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void update_WithValidId_ShouldReturnCategoryDto() {
        Long categoryId = 1L;
        CreateCategoryRequestDto createCategoryRequestDto = new CreateCategoryRequestDto();
        createCategoryRequestDto.setName("test");
        Category category = new Category();
        category.setId(categoryId);
        category.setName(createCategoryRequestDto.getName());
        CategoryDto expected = new CategoryDto();
        expected.setId(category.getId());
        expected.setName(category.getName());

        when(categoryRepository.existsById(categoryId)).thenReturn(true);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toModel(createCategoryRequestDto)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(expected);

        CategoryDto actual = categoryService.update(categoryId, createCategoryRequestDto);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getById_WithInvalidId_ShouldThrowEntityNotFoundException() {
        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());
        EntityNotFoundException exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> categoryService.getById(categoryId));
        String actualMessage = exception.getMessage();
        String expectedMessage = "Category with id: " + categoryId + ", not found";
        Assertions.assertEquals(expectedMessage, actualMessage);
    }
}
