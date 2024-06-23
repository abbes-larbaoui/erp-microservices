package dz.kyrios.adminservice.entity;

import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "module")
public class Module implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "module_name", nullable = false, unique = true)
    private String moduleName;

    @NotBlank
    @Column(name = "module_code", nullable = false, unique = true)
    private String moduleCode;

    @NotBlank
    @Column(name = "color", nullable = false)
    private String color;

    @NotBlank
    @Column(name = "icon", nullable = false)
    private String icon;

    @NotBlank
    @Column(name = "uri", nullable = false)
    private String uri;

    @Column(name = "actif")
    private Boolean actif = Boolean.TRUE;
}
