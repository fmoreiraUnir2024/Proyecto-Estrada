package com.libreriaproyect.libreriproyecto.entidades.documento;

import com.libreriaproyect.libreriproyecto.entidades.proyecto.Seccion;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "retroalimentaciones")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Retroalimentacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El contenido de la retroalimentación es obligatorio")
    @Column(columnDefinition = "TEXT")
    private String contenido;

    @ManyToOne
    @JoinColumn(name = "seccion_id", nullable = false)
    private Seccion seccion;
}