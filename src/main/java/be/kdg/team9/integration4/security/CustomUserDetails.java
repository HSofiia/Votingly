package be.kdg.team9.integration4.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;

public class CustomUserDetails extends User {
    private final long userId;
    private final String firstName;
    private final String lastName;
    private final String userType;

    public CustomUserDetails(String email,
                             String password,
                             Collection<? extends GrantedAuthority> authorities,
                             long userId,
                             String firstName,
                             String lastName, String userType) {
        super(email, password, authorities);
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
    }

    public long getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserType() {
        return userType;
    }
    public static boolean hasRole(String role) {
        Collection<? extends GrantedAuthority> authorities =
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        return authorities.contains(new SimpleGrantedAuthority(role));
    }
}
