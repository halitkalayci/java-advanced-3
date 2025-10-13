package com.turkcell.productservice.query.list;

import com.turkcell.productservice.entity.Product;
import com.turkcell.productservice.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListProductQueryHandler {
    // Farklı bir repo ile farklı bir db'e bağlanabilir.
    private ProductRepository productRepository;

    public ListProductQueryHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> getProducts(ListProductQuery query) {
        Pageable pageable = PageRequest.of(query.pageIndex(),query.pageSize());
        return productRepository.findAll(pageable);
    }
}
