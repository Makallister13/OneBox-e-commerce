package com.onebox.eCommerce.service;

import com.onebox.eCommerce.model.Order;
import com.onebox.eCommerce.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderRepository orderRepository ;
    @Override
    public Iterable<Order> getAllOrders() {
        return this.orderRepository.findAll();
    }

    @Cacheable("order")
    @Override
    public Order create(Order order) {
        return this.orderRepository.save(order);
    }

    @Cacheable("order")
    @Override
    public void update(Order order) {
        this.orderRepository.save(order);

    }

    @Override
    @CacheEvict(value = "order", allEntries = true)
    @Scheduled(fixedRateString = "${caching.spring.orderTTL}")
    public void emptyOrderCache() {

    }
}
