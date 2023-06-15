package com.agudodiego.quePague.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder // lo necesito en la entidad para poder mapear luego en el dto
@AllArgsConstructor // @Builder necesita de esta annotation
@NoArgsConstructor
@Table(name = "person")
public class Person implements UserDetails {

    @Id
    @Column(name = "username", length = 45, nullable = false, unique = true)
    private String username;
    @Column(name = "pass", length = 255, nullable = false)
    private String pass;
    @Column(name = "email", length = 45, nullable = false)
    private String email;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "username") // como es una relacion UNIDIRECCIONAL agrego esta annotation para que no cree una tabla intermedia y pueda borrar el pago sin acceder al padre de la relacion
    private Set<Payment> payments;

    // Debido a que Role es un enum hay que decirselo a Spring con la annotation @Enumerated
    @Enumerated(EnumType.STRING)
    private Role role;

    // este metodo devuelve una lista de roles por eso es necesario crear la entidad Role como un enumerado
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    // username es como security le llama a aquel dato inequivoco de la entidad, es este caso tmb es el username pero podria ser el mail
    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return pass;
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
