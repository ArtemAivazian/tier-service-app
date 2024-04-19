package cz.cvut.fel.nss.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue
    private Long userId;

    @Column(nullable=false, length=50)
    private String firstName;

    @Column(nullable=false, length=50)
    private String lastName;

    @Column(nullable=false, length=120, unique=true)
    private String email;

    @Column(nullable=false, unique=true)
    private String encryptedPassword;

    @Enumerated(EnumType.STRING)
    private Role role;


    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }
}
