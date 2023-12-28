package dz.kyrios.adminservice.repository;

import dz.kyrios.adminservice.entity.AuthorityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityTypeRepository extends JpaRepository<AuthorityType, Long>, JpaSpecificationExecutor<AuthorityType> {
}
