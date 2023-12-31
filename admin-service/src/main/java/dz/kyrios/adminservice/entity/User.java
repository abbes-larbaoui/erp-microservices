package dz.kyrios.adminservice.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "t_user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "uuid", nullable = false)
    private String uuid;

    @NotBlank
    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "user" ,cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Profile> profiles;

    @NotNull
    @OneToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id", nullable = false)
    private Profile actifProfile;

    public void addProfile(Profile profile) {
        if (this.getProfiles() != null && !this.getProfiles().isEmpty()) {
            this.getProfiles().add(profile);
            profile.setUser(this);
        }
    }
}
