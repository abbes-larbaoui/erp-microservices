package dz.kyrios.notificationservice.mapper.user;

import dz.kyrios.notificationservice.dto.user.UserResponse;
import dz.kyrios.notificationservice.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImp implements UserMapper{
    @Override
    public UserResponse entityToResponse(User entity) {
        return UserResponse.builder()
                .id(entity.getId())
                .uuid(entity.getUuid())
                .userName(entity.getUserName())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .build();
    }
}
