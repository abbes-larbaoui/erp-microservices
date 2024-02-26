package dz.kyrios.notificationservice.mapper.user;

import dz.kyrios.notificationservice.dto.user.UserResponse;
import dz.kyrios.notificationservice.entity.User;

public interface UserMapper {
    UserResponse entityToResponse(User entity);
}
