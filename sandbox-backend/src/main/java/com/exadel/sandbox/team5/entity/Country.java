package com.exadel.sandbox.team5.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "country")
public class Country extends BaseEntity implements Serializable {

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "country")
    private Set<City> cities;
}
