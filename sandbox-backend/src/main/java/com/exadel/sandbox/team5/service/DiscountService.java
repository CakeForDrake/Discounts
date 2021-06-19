package com.exadel.sandbox.team5.service;

import com.exadel.sandbox.team5.dto.DiscountDto;
import com.exadel.sandbox.team5.util.DiscountSearchCriteria;
import org.springframework.data.domain.Page;

public interface DiscountService extends CRUDService<DiscountDto> {
    Page<Discount> getByCriteria(DiscountSearchCriteria searchCriteria);
}
