package dz.kyrios.adminservice.config.auditlog;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EntityTraceDetail {
    private String property;
    private Object oldValue;
    private Object newValue;
}
