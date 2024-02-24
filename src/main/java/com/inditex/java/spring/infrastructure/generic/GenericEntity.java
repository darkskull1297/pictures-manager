package com.inditex.java.spring.infrastructure.generic;


import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@MappedSuperclass
@EqualsAndHashCode(callSuper = false)
public class GenericEntity {
    @Id
    private int id;
}
