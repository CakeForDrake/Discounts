package com.exadel.sandbox.team5.dao;

import com.exadel.sandbox.team5.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountDAO extends JpaRepository<Discount, Long> {

    List<Discount> getByNameContainingAndTagsIn(String name, List<String> tags);
}
