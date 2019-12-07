package ru.hetoiiblpb.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role", nullable = false)
    private String role;

    @ManyToMany(mappedBy = "roles")
    private Set<User> roles = new HashSet<>();

    public Role() {
    }

    public Role(Long id, String role) {
        this.id = id;
        this.role = role;
    }


    @Override
    public String getAuthority() {
        return role;
    }

    @Override
    public String toString() {
        return role;
    }
}
