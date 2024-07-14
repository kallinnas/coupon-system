package com.system.coupon.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.INTEGER)
public abstract class Client {
    public static final int NO_ID = -1;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
