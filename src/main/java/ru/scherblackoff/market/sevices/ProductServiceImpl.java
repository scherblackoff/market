package ru.scherblackoff.market.sevices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.scherblackoff.market.exeptions.ProductNotFoundException;
import ru.scherblackoff.market.models.Product;
import ru.scherblackoff.market.repositories.ProductsRepository;

import java.util.List;


@Service
public class ProductServiceImpl implements ProductService{

    private ProductsRepository productsRepository;

    @Autowired
    public void setProductsRepository(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> getAll(Specification<Product> spec, Integer page) {
        if (page < 1L) {
            page = 1;
        }
        return productsRepository.findAll(spec, PageRequest.of(page - 1, 10));
    }

    @Override
    @Transactional(readOnly = true)
    public Product get(Long id) {
        return productsRepository.findById(id).orElseThrow((
        )  -> new ProductNotFoundException("Can't found student with id = " + id));
    }

    @Override
    @Transactional
    public Product saveOrUpdate(Product product) {
       return productsRepository.save(product);
    }


    @Override
    @Transactional
    public Product remove(Product product) {
        productsRepository.delete(product);
        return product;
    }
}
