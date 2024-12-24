package vn.thuanflu.identityservices.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import vn.thuanflu.identityservices.dto.request.UserCreationRequest;
import vn.thuanflu.identityservices.dto.request.UserUpdateRequest;
import vn.thuanflu.identityservices.dto.response.UserResponse;
import vn.thuanflu.identityservices.entity.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    void updateUser(@MappingTarget User user, UserUpdateRequest request);

//    @Mapping(source = "firstName", target = "lastName") // mapping field firstName (user) to field lastName (userResponse)
//    @Mapping(target = "lastName", ignore = true) // don't mapping field lastName
    UserResponse toUserResponse(User user);

    List<UserResponse> toUserResponses(List<User> users);

}
