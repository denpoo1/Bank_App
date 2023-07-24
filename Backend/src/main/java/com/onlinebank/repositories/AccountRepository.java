package com.onlinebank.repositories;

import com.onlinebank.models.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Denis Durbalov
 */
@Repository
public interface AccountRepository extends JpaRepository<AccountModel, Integer> {
}
