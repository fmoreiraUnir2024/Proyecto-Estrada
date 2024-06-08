package com.libreriaproyect.libreriproyecto.entitys;

import javax.persistence.*;
import lombok.*;

import javax.persistence.Entity;

@Entity
@Table(name = "userrol")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRol {
    @Id
    @GeneratedValue(strategy = GenerationType. IDENTITY)
    private Long usuarioRolId;
    @ManyToOne (fetch = FetchType.EAGER)
    private User usuario;
    @ManyToOne
    private Rol rol;

}
