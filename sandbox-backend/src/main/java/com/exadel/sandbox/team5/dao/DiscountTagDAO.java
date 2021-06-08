package com.exadel.sandbox.team5.dao;

import com.exadel.sandbox.team5.entity.DiscountTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountTagDAO extends JpaRepository<DiscountTag, Long> {
}
