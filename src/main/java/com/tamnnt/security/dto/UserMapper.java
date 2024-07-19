package com.tamnnt.security.dto;

import com.tamnnt.security.model.User;

public class UserMapper {
    public static UserDto mapToUserDto(User user) {
     UserDto userDto = new UserDto();
     userDto.setId(user.getId());
     userDto.setEmail(user.getEmail());
     userDto.setFirstName(user.getFirstName());
     userDto.setLastName(user.getLastName());
     return userDto;
    }


}
