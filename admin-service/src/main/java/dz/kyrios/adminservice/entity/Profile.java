package dz.kyrios.adminservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "profile")
public class Profile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Module> modules = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "profile" ,cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProfileAuthority> authorities = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private Group group;

    @Column(name = "actif")
    private Boolean actif = Boolean.TRUE;

    public void addModule(Module module) {
        this.getModules().add(module);
    }

    public void removeModule(Module module) {
        this.getModules().remove(module);
    }

    public void addRole(Role role) {
        this.getRoles().add(role);
    }

    public void removeRole(Role role) {
        this.getRoles().remove(role);
    }

    public void addAuthority(ProfileAuthority profileAuthority) {
        this.getAuthorities().add(profileAuthority);
    }

    public void removeAuthority(ProfileAuthority profileAuthority) {
        this.getAuthorities().remove(profileAuthority);
    }
}
