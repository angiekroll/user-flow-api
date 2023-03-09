package com.neoris.user_flow_api.repository;

import com.neoris.user_flow_api.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
