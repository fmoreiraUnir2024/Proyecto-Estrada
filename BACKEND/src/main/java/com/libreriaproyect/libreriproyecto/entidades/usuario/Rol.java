package com.libreriaproyect.libreriproyecto.entidades.usuario;

import javax.persistence.*;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="roles")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;
    private String nombre;

    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "rol")
    private Set<UsuarioRol> userRolSet= new HashSet<>();
}
