package com.city.service.mapper.user;

import static com.city.dao.entity.Role.ALLOW_EDIT;

import com.city.dao.entity.User;
import com.city.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserToUserDtoMapper implements Function<User, UserDto> {
    @Override
    public UserDto apply(User user) {
        return new UserDto(
                user.getFirstname(),
                user.getLastname(),
                user.getRoles().contains(ALLOW_EDIT)
        );
    }
}
