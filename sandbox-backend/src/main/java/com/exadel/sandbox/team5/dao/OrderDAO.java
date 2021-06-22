package com.exadel.sandbox.team5.dao;

import com.exadel.sandbox.team5.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderDAO extends JpaRepository<Order, Long> {

    List<Order> findAllByEmployeeId(Long id);

    Order getBookingListByDiscountIdAndEmployeePromocode(Long id, String promoCode);

    @Transactional
    @Modifying
    @Query(value = "update `order` o set o.promoCodeStatus = :status where o.employeePromocode = :promoCode", nativeQuery = true)
    void setPromoCodeStatus(@Param("status") Boolean status, @Param("promoCode") String promoCode);
}