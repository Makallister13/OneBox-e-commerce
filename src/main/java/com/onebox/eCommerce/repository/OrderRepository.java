package com.onebox.eCommerce.repository;

import com.onebox.eCommerce.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
