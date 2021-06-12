package com.exadel.sandbox.team5.service.impl;

import com.exadel.sandbox.team5.dao.EmployeeDiscountDAO;
import com.exadel.sandbox.team5.entity.EmployeeDiscount;
import com.exadel.sandbox.team5.service.EmployeeDiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class EmployeeDiscountImpl extends AbstractService<EmployeeDiscount, EmployeeDiscountDAO> implements EmployeeDiscountService {

    public EmployeeDiscountImpl(EmployeeDiscountDAO repository) {
        super(repository);
    }
}
