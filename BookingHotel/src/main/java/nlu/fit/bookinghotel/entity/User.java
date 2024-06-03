package nlu.fit.bookinghotel.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BasicEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "full_name",nullable = false, columnDefinition = "VARCHAR(300) DEFAULT ''")
    private String fullName;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "phone_number",nullable = false,length = 10,unique = true)
    private String phoneNumber;

    @Column(name = "facebook_account_id",columnDefinition = "INT DEFAULT 0")
    private int facebookAccountId;

    @Column(name = "google_account_id",columnDefinition = "INT DEFAULT 0")
    private int googleAccountId;

    @Column(name = "is_ative", nullable = false,columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean isAtive;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_"+getRole().getName().toUpperCase()));
        //authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        return authorityList;
    }

    @Override
    public String getUsername() {
        return phoneNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
