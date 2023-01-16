package com.onebox.eCommerce.service;


import com.onebox.eCommerce.model.Product;

import java.util.Optional;

public interface ProductService {

    Iterable<Product> getAllProducts();

    Product getProduct(Long id);

    Product save(Product product);


}
