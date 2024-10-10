package io.agamivriddhi.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String city;

}
