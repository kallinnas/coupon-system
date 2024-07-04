package com.system.coupon.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.system.coupon.data.ex.AlreadyPurchasedCouponException;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.system.coupon.data.model.Client.NO_ID;

@Getter
@Setter
@Entity
@Table(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "company_id")
    @JsonIgnore
    private Company company;

    @ManyToMany(mappedBy = "coupons", cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE})
    @JsonIgnore
    private List<Customer> customers;

    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private int category;
    private int amount;
    private double price;
    private String description;
    private String imageURL;

    public Coupon() {
        this.customers = new ArrayList<>();
    }

    public Coupon(long id) {
        this.id = id;
    }

    public void addCustomer(Customer customer) throws AlreadyPurchasedCouponException {
        if (amount > 0) {
            customers.add(customer);
        } else throw new AlreadyPurchasedCouponException(String.format("All coupons %s were sold out!", description));
    }

    public void deleteCustomer(Customer customer) {
        customers.remove(customer);
    }

    public static Coupon empty() {
        return new Coupon(NO_ID);
    }

    @JsonIgnore
    public void setCompanyId(long company_id) {
        Company company = new Company();
        company.setId(company_id);
        this.company = company;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", company=" + company +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", category=" + category +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }

    public boolean similarCoupon(Coupon coupon){
        return this.getTitle().equals(coupon.getTitle())
                && this.getCategory() == coupon.getCategory()
                && this.getPrice() == coupon.getPrice()
                && this.getDescription().equals(coupon.getDescription())
                && this.getImageURL().equals(coupon.getImageURL());
    }
}
