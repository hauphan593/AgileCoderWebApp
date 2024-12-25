package Web2.Web2_backend.service.impl;


import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import Web2.Web2_backend.dto.UserDto;
import Web2.Web2_backend.entity.AccountRole;
import Web2.Web2_backend.entity.User;
import Web2.Web2_backend.exception.ResourceNotFoundException;
import Web2.Web2_backend.mapper.UserMapper;
import Web2.Web2_backend.repository.AccountRoleRepository;
import Web2.Web2_backend.repository.UserRepository;
import Web2.Web2_backend.service.UserService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private AccountRoleRepository accountRoleRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.mapToUser(userDto,accountRoleRepository);

        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        user.setCreatedBy("admin");
        user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        user.setUpdatedBy("admin");

        User savedUser = userRepository.save(user);
        return UserMapper.mapToUserDto(savedUser);
    }

    @Override
    public UserDto getUserbyId(Long userId) {
        User user = userRepository.findById(userId)
             .orElseThrow(() -> new ResourceNotFoundException("User is not exists with given id: " + userId));

        return UserMapper.mapToUserDto(user); 
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users =  userRepository.findAll();
        return users.stream().map((user) -> UserMapper.mapToUserDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(Long userId, UserDto updatedUser) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User is not exists with given id: " + userId));
        //update data
        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword());
        user.setEmail(updatedUser.getEmail());

        //update Role
        AccountRole role = accountRoleRepository.findById(updatedUser.getRoleId())
                            .orElseThrow(() -> new ResourceNotFoundException("Role is not exists with given id: " + updatedUser.getRoleId()));
        user.setAccountRole(role);

        //update times and author
        user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        user.setUpdatedBy("admin");

        User updatedUserObj = userRepository.save(user);

        return UserMapper.mapToUserDto(updatedUserObj);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User is not exists with given id: " + userId));
        user.setAccountRole(null);
        userRepository.save(user);
        userRepository.deleteById(userId);
    }

    @Override
    public UserDto getUserbyName(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserbyName'");
    }

    @Override
    public UserDto login(String userName, String password) {
        List<User> users =  userRepository.findAll();
        for (User user : users) {
            if( user.getUsername().equals(userName)) {
                if(user.getPassword().equals(password)) {
                    User validUser = user;
                    return UserMapper.mapToUserDto(validUser);
                }
            }
        }
        // throw new RuntimeException("Invalid username or password");
        return null;
    }



    

}
