package com.system.coupon.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
//@MappedSuperclass
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Client {
    public static final int NO_ID = -1;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
