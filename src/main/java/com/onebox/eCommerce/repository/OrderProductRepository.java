package com.onebox.eCommerce.repository;

import com.onebox.eCommerce.model.OrderProduct;
import com.onebox.eCommerce.model.OrderProductPK;
import org.springframework.data.repository.CrudRepository;

public interface OrderProductRepository extends CrudRepository<OrderProduct, OrderProductPK> {
}
