package com.libreriaproyect.libreriproyecto.entidades.documento;

import com.libreriaproyect.libreriproyecto.entidades.proyecto.Proyecto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "documentos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Documento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del documento es obligatorio")
    private String nombre;

    @NotBlank(message = "El formato del documento es obligatorio")
    private String formato;
    private String contenido;
    @ManyToOne
    @JoinColumn(name = "proyecto_id", nullable = false)
    private Proyecto proyecto;
}