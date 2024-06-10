package com.libreriaproyect.libreriproyecto.entidades.proyecto;

import com.libreriaproyect.libreriproyecto.entidades.usuario.Usuario;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "plantillas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Plantilla {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la plantilla es obligatorio")
    private String nombre;

    @NotBlank(message = "El formato de la plantilla es obligatorio")
    private String formato;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}