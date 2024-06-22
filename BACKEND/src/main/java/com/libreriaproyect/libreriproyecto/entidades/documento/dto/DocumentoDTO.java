package com.libreriaproyect.libreriproyecto.entidades.documento.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentoDTO {
    private Long id;
    private String nombre;
    private String formato;
    private String contenido;
    private Integer proyecto_id;
}
