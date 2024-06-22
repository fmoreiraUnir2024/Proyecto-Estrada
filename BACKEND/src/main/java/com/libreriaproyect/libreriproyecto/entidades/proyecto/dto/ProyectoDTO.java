package com.libreriaproyect.libreriproyecto.entidades.proyecto.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProyectoDTO {
    @NotNull
    private String nombre;
    private Integer id;
    @NotNull
    private String descripcion;

    @NotNull
    private String tipo_articulo;

    @NotNull
    private Long usuario_id;

    @NotNull
    private Long plantilla_id;
}
