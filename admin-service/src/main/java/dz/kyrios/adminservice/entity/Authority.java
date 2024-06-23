package dz.kyrios.adminservice.entity;

import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "authority")
public class Authority implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "authority_type_id", referencedColumnName = "id", nullable = false)
    private AuthorityType authorityType;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "module_id", referencedColumnName = "id", nullable = false)
    private Module module;

    @Column(name = "actif")
    private Boolean actif = Boolean.TRUE;
}
