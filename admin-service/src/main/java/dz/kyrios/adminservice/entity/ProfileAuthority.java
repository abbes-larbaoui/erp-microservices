package dz.kyrios.adminservice.entity;

import lombok.*;

import jakarta.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "profile_authority")
public class ProfileAuthority  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id", nullable = false)
    private Profile profile;

    @ManyToOne
    @JoinColumn(name = "authority_id", referencedColumnName = "id", nullable = false)
    private Authority authority;

    @Column(name = "granted")
    private Boolean granted;
}
