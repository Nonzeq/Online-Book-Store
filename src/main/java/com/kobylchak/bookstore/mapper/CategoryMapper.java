package com.kobylchak.bookstore.mapper;

import com.kobylchak.bookstore.config.MapperConfig;
import com.kobylchak.bookstore.dto.category.CategoryDto;
import com.kobylchak.bookstore.dto.category.CreateCategoryRequestDto;
import com.kobylchak.bookstore.model.Category;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    Category toModel(CreateCategoryRequestDto requestDto);

    CategoryDto toDto(Category category);

    List<CategoryDto> toListDto(List<Category> categoryList);
}
