package com.onebox.eCommerce.service;

import com.onebox.eCommerce.model.Order;
import com.onebox.eCommerce.model.Product;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface OrderService {

    @NotNull Iterable<Order> getAllOrders();

    Order create(@NotNull(message = "The order cannot be null.") @Valid Order order);

    void update(Order order);


    void emptyOrderCache();
}
