package dz.kyrios.notificationservice.repository;

import dz.kyrios.notificationservice.entity.NotificationTemplate;
import dz.kyrios.notificationservice.enums.NotificationTemplateCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, Long>, JpaSpecificationExecutor<NotificationTemplate> {
    Optional<NotificationTemplate> findByTemplateCode(NotificationTemplateCode code);
}
