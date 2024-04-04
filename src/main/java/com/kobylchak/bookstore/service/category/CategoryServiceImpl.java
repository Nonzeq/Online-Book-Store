package com.kobylchak.bookstore.service.category;

import com.kobylchak.bookstore.dto.category.CategoryDto;
import com.kobylchak.bookstore.dto.category.CreateCategoryRequestDto;
import com.kobylchak.bookstore.mapper.CategoryMapper;
import com.kobylchak.bookstore.model.Category;
import com.kobylchak.bookstore.repository.category.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> findAll(Pageable pageable) {
        return categoryMapper.toListDto(categoryRepository.findAll(pageable).getContent());
    }

    @Override
    public CategoryDto getById(Long id) {
        return categoryMapper.toDto(categoryRepository.findById(id)
                                            .orElseThrow(
                                                    () -> new EntityNotFoundException(
                                                            "Category with id: "
                                                            + id
                                                            + ", not "
                                                            + "found")));
    }

    @Override
    public CategoryDto save(CreateCategoryRequestDto categoryDto) {
        Category category = categoryMapper.toModel(categoryDto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto update(Long id, CreateCategoryRequestDto categoryDto) {
        if (categoryRepository.existsById(id)) {
            Category category = categoryMapper.toModel(categoryDto);
            category.setId(id);
            return categoryMapper.toDto(categoryRepository.save(category));
        }
        throw new EntityNotFoundException("Category with id: " + id + ", not found");
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
