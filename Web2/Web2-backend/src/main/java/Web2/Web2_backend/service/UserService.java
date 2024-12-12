package Web2.Web2_backend.service;

import java.util.List;

import Web2.Web2_backend.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto getUserbyId(Long Id);

    List<UserDto> getAllUsers();

    UserDto updateUser(Long userId, UserDto updatedUser);

    void deleteUser(Long userId);
}
