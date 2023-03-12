package com.neoris.user_flow_api.repository;

import com.neoris.user_flow_api.domain.Movement;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MovementRepository extends JpaRepository<Movement, Long> {

  @Query(value =
      "SELECT CAST(mov.date AS DATE) AS date, acc.customer_id, acc.account_number, acc.account_type, mov.initial_balance, acc.state, mov.amount, mov.balance, mov.movement_type "
          + "FROM CUSTOMER cus "
          + "RIGHT JOIN ACCOUNT acc ON acc.customer_id = cus.customer_id "
          + "RIGHT JOIN MOVEMENT mov ON acc.account_number = mov.account_number "
          + "WHERE acc.customer_id=:customerId "
          + "AND mov.date BETWEEN :startDate AND :endDate", nativeQuery = true)
  List<Object[]> findByCustomerIdAndDataRange(
      @Param("customerId") Long customerId,
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate);

}
