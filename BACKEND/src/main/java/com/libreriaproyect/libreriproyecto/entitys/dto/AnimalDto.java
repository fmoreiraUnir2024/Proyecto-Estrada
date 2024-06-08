package com.libreriaproyect.libreriproyecto.entitys.dto;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AnimalDto {

    private Long id;
    private String name;
    private String description;
    private Integer countAnimal;
    private Double price;
    private String dateRegistre;
    private String genero;
    private Long tipo;
    private String strTipo;
    private Date incubationdate;
    private String  incubationDescription;
    private String incubationState;
    private Integer state;
}
