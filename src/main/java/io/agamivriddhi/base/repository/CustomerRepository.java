package io.agamivriddhi.base.repository;

import io.agamivriddhi.base.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
