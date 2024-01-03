package dz.kyrios.adminservice.mapper.user;

import dz.kyrios.adminservice.dto.user.UserCreateRequest;
import dz.kyrios.adminservice.dto.user.UserRequest;
import dz.kyrios.adminservice.dto.user.UserResponse;
import dz.kyrios.adminservice.entity.User;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {

    User requestToEntity(UserRequest request);

    User requestToEntity(UserCreateRequest request);

    UserResponse entityToResponse(User entity);
}
