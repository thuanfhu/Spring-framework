package vn.thuanflu.identityservices.service;

import org.springframework.stereotype.Service;
import vn.thuanflu.identityservices.dto.request.UserCreationRequest;
import vn.thuanflu.identityservices.dto.request.UserUpdateRequest;
import vn.thuanflu.identityservices.dto.response.UserResponse;
import vn.thuanflu.identityservices.entity.User;
import vn.thuanflu.identityservices.exception.AppException;
import vn.thuanflu.identityservices.exception.ErrorCode;
import vn.thuanflu.identityservices.mapper.UserMapper;
import vn.thuanflu.identityservices.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponse createUser(UserCreationRequest request){
        if(userRepository.existsByUsername(request.getUsername())) throw new AppException(ErrorCode.USER_EXISTED);
        User user = userMapper.toUser(request);
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    public List<UserResponse> getAllUsers(){
        return userMapper.toUserResponses(userRepository.findAll());
    }

    public UserResponse getUserById(String id){
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
    }

    public UserResponse updateUserById(String id, UserUpdateRequest request){
        User currentUser = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userMapper.updateUser(currentUser, request);
        return userMapper.toUserResponse(userRepository.save(currentUser));
    }

    public void deleteUserById(String id){
        this.userRepository.deleteById(id);
    }
}
