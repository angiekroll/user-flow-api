package com.neoris.user_flow_api.repository;

import com.neoris.user_flow_api.domain.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementRepository extends JpaRepository<Movement, Long> {

}
