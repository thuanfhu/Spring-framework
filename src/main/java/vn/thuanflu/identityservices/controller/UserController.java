package vn.thuanflu.identityservices.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import vn.thuanflu.identityservices.dto.request.UserCreationRequest;
import vn.thuanflu.identityservices.dto.request.UserUpdateRequest;
import vn.thuanflu.identityservices.entity.User;
import vn.thuanflu.identityservices.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestBody @Valid UserCreationRequest request){
        return this.userService.createUser(request);
    }

    @GetMapping
    public List<User> getAllUsers(){
        return this.userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable("userId") String id){
        return this.userService.getUserById(id);
    }

    @PutMapping("/{userId}")
    public User updateUserById(@PathVariable String userId, @RequestBody UserUpdateRequest request){
        return this.userService.updateUserById(userId, request);
    }

    @DeleteMapping("/{userId}")
    public String deleteUserById(@PathVariable String userId){
        this.userService.deleteUserById(userId);
        return "User has been deleted";
    }
}
