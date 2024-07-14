package com.system.coupon.data.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@DiscriminatorValue("2")
public class Company extends Client {

    @Column(name = "`name`")
    private String name;
    @Column(name = "`imageURL`")
    private String imageURL;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Coupon> coupons = new ArrayList<>();

    public Company(){
        coupons = new ArrayList<>();
    }

    public Company(int id) {
        setId(id);
    }

    public static Company empty() {
        return new Company(NO_ID);
    }

    public void add(Coupon coupon) {
        coupon.setCompany((this));
        coupons.add(coupon);
    }

}
