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
public class PlantillaDTO {
    @NotNull
    private String nombre;

    @NotNull
    private String formato;

    @NotNull
    private Long usuario_id;
}
