package dz.kyrios.adminservice.repository;

import dz.kyrios.adminservice.entity.ProfileAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileAuthorityRepository extends JpaRepository<ProfileAuthority, Long>, JpaSpecificationExecutor<ProfileAuthority> {
    Optional<ProfileAuthority> findProfileAuthorityByProfile_IdAndAuthority_Id(Long profileId, Long authorityId);
}
