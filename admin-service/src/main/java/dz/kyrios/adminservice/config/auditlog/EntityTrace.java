package dz.kyrios.adminservice.config.auditlog;

import lombok.Data;

import java.time.LocalDateTime;

@IgnoreAudit
@Data
public class EntityTrace {

    private String entityName;

    private Long entityId;

    private String operation;

    private String data;

    private String utilisateur;

    private LocalDateTime timestamp;

    private String module;
}