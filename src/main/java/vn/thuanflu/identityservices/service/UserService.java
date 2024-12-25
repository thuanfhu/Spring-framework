package vn.thuanflu.identityservices.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.thuanflu.identityservices.dto.request.UserCreationRequest;
import vn.thuanflu.identityservices.dto.request.UserUpdateRequest;
import vn.thuanflu.identityservices.dto.response.UserResponse;
import vn.thuanflu.identityservices.entity.Role;
import vn.thuanflu.identityservices.entity.User;
import vn.thuanflu.identityservices.exception.AppException;
import vn.thuanflu.identityservices.exception.ErrorCode;
import vn.thuanflu.identityservices.mapper.UserMapper;
import vn.thuanflu.identityservices.repository.RoleRepository;
import vn.thuanflu.identityservices.repository.UserRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

//    @PreAuthorize("hasAuthority('CREATE_USER')")
    public UserResponse createUser(UserCreationRequest request){
        if(userRepository.existsByUsername(request.getUsername())) throw new AppException(ErrorCode.USER_EXISTED);
        User user = userMapper.toUser(request);

        // Hash password before save
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Set default role USER for new user
        Role userRole = this.roleRepository.findByName(vn.thuanflu.identityservices.enums.Role.USER.name()).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        Set<Role> roles = new HashSet<>(); roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers(){
        return userMapper.toUserResponses(userRepository.findAll());
    }

    @PostAuthorize("returnObject.username == authentication.name || hasRole('ADMIN')")
    public UserResponse getUserById(String id){
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
    }

    public UserResponse updateUserById(String id, UserUpdateRequest request){
        User currentUser = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        userMapper.updateUser(currentUser, request);
        currentUser.setPassword(passwordEncoder.encode(request.getPassword()));

        // Update roles for user
        List<Role> roles = this.roleRepository.findAllById(request.getRoleIds());
        currentUser.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(currentUser));
    }

    public void deleteUserById(String id){
        this.userRepository.deleteById(id);
    }

    public UserResponse getMyInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userMapper.toUserResponse(userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
    }
}
