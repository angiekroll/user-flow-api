package com.neoris.user_flow_api.repository;

import com.neoris.user_flow_api.domain.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
  Optional<Account> findByAccountNumberAndStateTrue(Long id);

}
