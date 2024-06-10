package com.libreriaproyect.libreriproyecto.entidades.usuario;

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
public class UsuarioRol {
    @Id
    @GeneratedValue(strategy = GenerationType. IDENTITY)
    private Long usuarioRolId;
    @ManyToOne (fetch = FetchType.EAGER)
    private Usuario usuario;
    @ManyToOne
    private Rol rol;

}
