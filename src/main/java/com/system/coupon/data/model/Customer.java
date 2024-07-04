package com.system.coupon.data.model;

import com.system.coupon.data.ex.AlreadyPurchasedCouponException;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer extends Client {

    @Column(name = "`first_Name`")
    private String firstName;

    @Column(name = "`last_Name`")
    private String lastName;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "customer_coupon",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id"))
    private List<Coupon> coupons;

    public Customer() {
        this.coupons = new ArrayList<>();
    }

    public Customer(long id) {
        setId(id);
    }

    public static Customer empty() {
        return new Customer(NO_ID);
    }

    public void add(Coupon coupon) throws AlreadyPurchasedCouponException {
        coupon.addCustomer(this);
        coupons.add(coupon);
    }

}
