package com.exadel.sandbox.team5.dao;

import com.exadel.sandbox.team5.entity.Employee;
import com.exadel.sandbox.team5.util.Pair;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeDAO extends CommonRepository<Employee> {

    Employee getByLogin(String login);

    @Query(value = """
            SELECT new com.exadel.sandbox.team5.util.Pair(e.email, d.name)  FROM Employee e
                JOIN e.subscriptions c
                JOIN Discount d ON d.category=c.id 
                JOIN Params p ON p.name='lastExecution'
            WHERE d.periodStart > p.value
                GROUP BY e.email,d.name
            """)
    List<Pair> getNewDiscounts();

    @Query(value = """
            SELECT new java.lang.String(e.email) FROM Employee e
            """)
    List<String> getAllEmails();
}
