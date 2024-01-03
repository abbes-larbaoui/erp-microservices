package dz.kyrios.adminservice.mapper.user;

import dz.kyrios.adminservice.dto.profile.ProfileResponse;
import dz.kyrios.adminservice.dto.user.UserCreateRequest;
import dz.kyrios.adminservice.dto.user.UserRequest;
import dz.kyrios.adminservice.dto.user.UserResponse;
import dz.kyrios.adminservice.entity.User;
import dz.kyrios.adminservice.mapper.profile.ProfileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserMapperImp implements UserMapper{

//    private final ProfileMapper profileMapper;
//
//    @Autowired
//    public UserMapperImp(@Lazy ProfileMapper profileMapper) {
//        this.profileMapper = profileMapper;
//    }

    @Override
    public User requestToEntity(UserRequest request) {
        return User.builder()
                .userName(request.getUserName())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName((request.getLastName()))
                .build();
    }

    @Override
    public User requestToEntity(UserCreateRequest request) {
        return User.builder()
                .userName(request.getUserName())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName((request.getLastName()))
                .build();
    }

    @Override
    public UserResponse entityToResponse(User entity) {
//        Set<ProfileResponse> profileResponses = new HashSet<>();
//        if (entity.getProfiles() != null && !entity.getProfiles().isEmpty()) {
//            entity.getProfiles().forEach(profile -> profileResponses.add(profileMapper.entityToResponse(profile)));
//        }
        return UserResponse.builder()
                .id(entity.getId())
                .uuid(entity.getUuid())
                .email(entity.getEmail())
                .userName(entity.getUserName())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
//                .actifProfile(profileMapper.entityToResponse(entity.getActifProfile()))
//                .profileResponses(profileResponses)
                .build();
    }
}
