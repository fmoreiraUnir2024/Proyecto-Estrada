package com.libreriaproyect.libreriproyecto.entidades.proyecto;

import com.libreriaproyect.libreriproyecto.entidades.usuario.Usuario;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "proyectos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre del proyecto es obligatorio")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotBlank(message = "El tipo de artículo es obligatorio")
    @Column(name = "tipo_articulo")
    private String tipoArticulo;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "plantilla_id")
    private Plantilla plantilla;

    @JoinColumn(name = "contenido" ,columnDefinition = "TEXT" )
    private String contenido;

    @JoinColumn(name = "resumen"  )
    private String resumen;
    @JoinColumn(name = "palabras_clave"  )
    private String palabrasClave;

}