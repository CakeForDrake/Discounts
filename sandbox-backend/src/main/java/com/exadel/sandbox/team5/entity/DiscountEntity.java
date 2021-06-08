package com.exadel.sandbox.team5.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data

@Entity
@Table(name = "discount")
public class DiscountEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Temporal(TemporalType.DATE)
    @Column(name = "periodStart", nullable = false)
    private Date periodStart;

    @Temporal(TemporalType.DATE)
    @Column(name = "periodEnd", nullable = false)
    private Date periodEnd;

    @Column(name = "quantity", columnDefinition = "int default 10")
    private int quantity;

    @Column(name = "promocode", nullable = false, length = 50)
    private String promoCode;

    @Column(name = "companyId")
    private  int companyId;

    @Temporal(TemporalType.DATE)
    @Column(name = "modified")
    private Date modified;

    @Column(name = "modifiedBy", length = 50)
    private String modifiedBy;


}
