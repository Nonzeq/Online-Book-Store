package com.kobylchak.bookstore.service.user;

import com.kobylchak.bookstore.dto.user.UserRegistrationRequestDto;
import com.kobylchak.bookstore.dto.user.UserResponseDto;
import com.kobylchak.bookstore.exception.RegistrationException;
import com.kobylchak.bookstore.mapper.UserMapper;
import com.kobylchak.bookstore.model.User;
import com.kobylchak.bookstore.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.findUserByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException(
                "User with email: " + requestDto.getEmail() + " already exist");
        }
        User user = userRepository.save(userMapper.toModel(requestDto));
        return userMapper.toDto(user);
    }
}
