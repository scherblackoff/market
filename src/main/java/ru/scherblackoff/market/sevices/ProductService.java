package ru.scherblackoff.market.sevices;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import ru.scherblackoff.market.models.Product;

import java.util.List;

public interface ProductService {

    Page<Product> getAll(Specification<Product> spec, Integer page);

    Product get(Long id);

    Product saveOrUpdate(Product product);

    Product remove(Product product);
}
