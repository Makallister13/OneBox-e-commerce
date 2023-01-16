package com.onebox.eCommerce.controller;

import com.onebox.eCommerce.dto.OrderProductDto;
import com.onebox.eCommerce.model.Order;
import com.onebox.eCommerce.model.OrderProduct;
import com.onebox.eCommerce.model.Product;
import com.onebox.eCommerce.service.OrderProductService;
import com.onebox.eCommerce.service.OrderService;
import com.onebox.eCommerce.service.ProductService;
import com.onebox.eCommerce.utils.OrderForm;
import com.onebox.eCommerce.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController{

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;

    OrderProductService orderProductService;

    @GetMapping(value = {"", "/"})
    public @NotNull Iterable<Product> getProducts() {
        return productService.getAllProducts();
    }


    @PostMapping
    public ResponseEntity<Order> create(@RequestBody OrderForm form) {
        List<OrderProductDto> formDtos = form.getProductOrders();
        Validator.validateProductsExistence(formDtos, productService);
        Order order = new Order();
        order = this.orderService.create(order);
        List<OrderProduct> orderProducts = new ArrayList<>();
        for (OrderProductDto dto : formDtos) {
            orderProducts.add(orderProductService.create(new OrderProduct(order, productService.getProduct(dto
                    .getProduct()
                    .getId()), dto.getQuantity())));
        }
        order.setOrderProducts(orderProducts);
        this.orderService.update(order);

        String uri = ServletUriComponentsBuilder
                .fromCurrentServletMapping()
                .path("/orders/{id}")
                .buildAndExpand(order.getId())
                .toString();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", uri);

        return new ResponseEntity<>(order, headers, HttpStatus.CREATED);

    };


}

