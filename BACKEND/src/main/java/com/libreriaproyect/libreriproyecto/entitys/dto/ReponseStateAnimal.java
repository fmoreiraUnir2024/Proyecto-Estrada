package com.libreriaproyect.libreriproyecto.entitys.dto;

import lombok.*;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReponseStateAnimal {
        private Animal animal;
        private String tittelState;
        private String Message;

}
