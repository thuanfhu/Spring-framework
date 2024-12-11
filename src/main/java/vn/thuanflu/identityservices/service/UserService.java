package vn.thuanflu.identityservices.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.thuanflu.identityservices.dto.request.UserCreationRequest;
import vn.thuanflu.identityservices.dto.request.UserUpdateRequest;
import vn.thuanflu.identityservices.entity.User;
import vn.thuanflu.identityservices.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserCreationRequest request){
        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(request.getPassword());
        user.setUsername(request.getUsername());
        user.setBirthDate(request.getBirthDate());

        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(String id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUserById(String id, UserUpdateRequest request){
        User currentUser = this.getUserById(id);

        currentUser.setFirstName(request.getFirstName());
        currentUser.setLastName(request.getLastName());
        currentUser.setPassword(request.getPassword());
        currentUser.setBirthDate(request.getBirthDate());

        return userRepository.save(currentUser);
    }

    public void deleteUserById(String id){
        this.userRepository.deleteById(id);
    }
}
