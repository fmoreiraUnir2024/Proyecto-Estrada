package com.libreriaproyect.libreriproyecto.entidades.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProyectoDTO {
    @NotNull
    private String nombre;

    @NotNull
    private String descripcion;

    @NotNull
    private String tipo_articulo;

    @NotNull
    private Long usuario_id;

    @NotNull
    private Long plantilla_id;
}
