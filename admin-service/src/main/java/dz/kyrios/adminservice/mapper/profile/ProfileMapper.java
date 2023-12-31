package dz.kyrios.adminservice.mapper.profile;

import dz.kyrios.adminservice.dto.profile.ProfileRequest;
import dz.kyrios.adminservice.dto.profile.ProfileResponse;
import dz.kyrios.adminservice.entity.Profile;

public interface ProfileMapper {

    Profile requestToEntity(ProfileRequest request);

    ProfileResponse entityToResponse(Profile entity);
}
