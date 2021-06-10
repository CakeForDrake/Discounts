package com.exadel.sandbox.team5.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "location")
public class Location extends AuditableEntity {

    @Column(name = "city")
    private String city;

//    @ManyToMany
//    @JoinTable(
//            name="company_location",
//            joinColumns = @JoinColumn (name = "locationId"),
//            inverseJoinColumns = @JoinColumn(name = "companyId"))
//    Set<Company> companies = new HashSet<>();
}
