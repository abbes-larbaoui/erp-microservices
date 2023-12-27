package dz.kyrios.adminservice.dto.module;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModuleResponse {
    private Long id;
    private String moduleName;
    private String color;
    private String icon;
}
