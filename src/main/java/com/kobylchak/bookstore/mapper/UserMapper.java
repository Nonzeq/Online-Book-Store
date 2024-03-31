package com.kobylchak.bookstore.mapper;

import com.kobylchak.bookstore.config.MapperConfig;
import com.kobylchak.bookstore.dto.user.UserRegistrationRequestDto;
import com.kobylchak.bookstore.dto.user.UserResponseDto;
import com.kobylchak.bookstore.model.Role;
import com.kobylchak.bookstore.model.User;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    @Mapping(target = "roles", ignore = true)
    UserResponseDto toDto(User user);

    @AfterMapping
    default void setRoleIds(User user, @MappingTarget UserResponseDto responseDto) {
        Set<Long> rolesIds = user.getRoles().stream()
                                .map(Role::getId)
                                .collect(Collectors.toSet());
        responseDto.setRoles(rolesIds);
    }

    User toModel(UserRegistrationRequestDto userRegistrationRequestDto);
}
