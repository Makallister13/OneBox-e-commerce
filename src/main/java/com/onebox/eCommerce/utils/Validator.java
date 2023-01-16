package com.onebox.eCommerce.utils;

import com.onebox.eCommerce.dto.OrderProductDto;
import com.onebox.eCommerce.service.ProductService;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Validator {
    public static void validateProductsExistence(List<OrderProductDto> orderProducts, ProductService productService) {
        List<OrderProductDto> list = orderProducts
                .stream()
                .filter(op -> Objects.isNull(productService.getProduct(op
                        .getProduct()
                        .getId())))
                .collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(list)) {
            new ResourceNotFoundException("Product not found");
        }
    }
}
