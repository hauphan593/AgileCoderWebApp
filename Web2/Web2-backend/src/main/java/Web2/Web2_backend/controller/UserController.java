package Web2.Web2_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Web2.Web2_backend.dto.UserDto;
import Web2.Web2_backend.service.UserService;
import lombok.AllArgsConstructor;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    //build Add User REST API
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto savedUser = userService.createUser(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    } 

    //build Get User REST API
    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserbyId(@PathVariable("id") Long userID) {
        UserDto userDto = userService.getUserbyId(userID);
        return ResponseEntity.ok(userDto);
    }    
    
    //build Get All user REST API 
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    //Build Update User REST API
    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long userId, @RequestBody UserDto updatedUser) {
        UserDto userDto = userService.updateUser(userId, updatedUser);
        return ResponseEntity.ok(userDto);
    }

    //Build Delete User REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id")  Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully!.");
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> Login(@RequestBody UserDto userDto) {
        
        UserDto logged = userService.login(userDto.getUsername(), userDto.getPassword());
        return ResponseEntity.ok(null);     
    }                   
        
}
