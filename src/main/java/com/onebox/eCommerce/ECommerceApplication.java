package com.onebox.eCommerce;

import com.onebox.eCommerce.dto.OrderProductDto;
import com.onebox.eCommerce.model.Order;
import com.onebox.eCommerce.model.OrderProduct;
import com.onebox.eCommerce.model.Product;
import com.onebox.eCommerce.service.OrderProductService;
import com.onebox.eCommerce.service.OrderService;
import com.onebox.eCommerce.service.OrderServiceImpl;
import com.onebox.eCommerce.service.ProductService;
import com.onebox.eCommerce.utils.OrderForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ECommerceApplication {




	Product bike = new Product("Bicicle", 120d, "https://images.unsplash.com/photo-1505705694340-019e1e335916?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTB8fGJpa2V8ZW58MHx8MHx8&w=1000&q=80");
	Product car = new Product("Car",60000d, "https://images.pexels.com/photos/170811/pexels-photo-170811.jpeg?auto=compress&cs=tinysrgb&w=400");
	Product pot = new Product("Pot", 15d,"https://images.pexels.com/photos/39521/cheese-noodles-court-eat-delicious-39521.jpeg?auto=compress&cs=tinysrgb&w=400");




	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);

	}

	@Bean
	CommandLineRunner runner(ProductService productService, OrderProductService orderProductService, @NotNull OrderService orderService) {
		return args -> {
			productService.save(bike);
			productService.save(car);
			productService.save(pot);

			OrderForm form = new OrderForm();
			List<OrderProductDto> productOrderList = new ArrayList<>();
			OrderProductDto orderProductDto = new OrderProductDto();
			orderProductDto.setProduct(bike);
			orderProductDto.setQuantity(1);
			productOrderList.add(orderProductDto);
			form.setProductOrders(productOrderList);

			List<OrderProductDto> formDtos = form.getProductOrders();
			// create order logic
			Order order = new Order();
			order = orderService.create(order);
			// populate order with products
			List<OrderProduct> orderProducts = new ArrayList<>();
			for (OrderProductDto dto : formDtos) {
				orderProducts.add(orderProductService.create(new OrderProduct(order, productService.getProduct(dto
						.getProduct()
						.getId()), dto.getQuantity())));
			}



		};



	}
}