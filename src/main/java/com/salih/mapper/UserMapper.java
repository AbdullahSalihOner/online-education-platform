package com.salih.mapper;

import com.salih.dto.request.LoginRequest;
import com.salih.dto.request.SignupRequest;
import com.salih.dto.response.UserInfoResponse;
import com.salih.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // LoginRequest to User conversion (for authentication)
    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    User loginRequestToUser(LoginRequest loginRequest);

    // SignupRequest to User conversion (for registration)
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "roles", ignore = true) // Roles will be set separately
    User signupRequestToUser(SignupRequest signupRequest);

    // User to UserInfoResponse conversion (for returning user info)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "roles", expression = "java(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toList()))")
    UserInfoResponse userToUserInfoResponse(User user);

    // List<User> to List<UserInfoResponse> conversion
    List<UserInfoResponse> usersToUserInfoResponses(List<User> users);

    // Convert Set of roles from SignupRequest to User entity roles
    default Set<String> mapRoles(Set<String> roles) {
        return roles.stream().collect(Collectors.toSet());
    }
}
