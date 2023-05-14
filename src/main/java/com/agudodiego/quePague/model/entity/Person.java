package com.agudodiego.quePague.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@Builder // lo necesito en la entidad para poder mapear luego en el dto
@AllArgsConstructor // @Builder necesita de esta annotation
@NoArgsConstructor
@Table(name = "person")
public class Person {

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

}
