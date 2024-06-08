package com.libreriaproyect.libreriproyecto.entitys.dto;

import lombok.*;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class IncubacionDto {
    private  Long id;
    private Integer state;
    private String deString;
    private String date;
}
