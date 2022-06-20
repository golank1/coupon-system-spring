package com.jb.coupon_system_spring.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @JoinTable(name = "customers_vs_coupons",
               joinColumns =@JoinColumn(name = "customer_id"),
               inverseJoinColumns = @JoinColumn(name = "coupon_id"),
               uniqueConstraints = {@UniqueConstraint(columnNames = {"customer_id","coupon_id"})})
    @Singular
    @ManyToMany(cascade = CascadeType.ALL)
    //@JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<Coupon> coupons = new ArrayList<>();


}
