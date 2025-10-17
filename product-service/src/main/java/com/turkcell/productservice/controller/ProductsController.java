package com.turkcell.productservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.turkcell.productservice.command.create.CreateProductCommand;
import com.turkcell.productservice.command.create.CreateProductCommandHandler;
import com.turkcell.productservice.entity.Product;
import com.turkcell.productservice.query.getById.GetByIdProductQuery;
import com.turkcell.productservice.query.getById.GetByIdProductQueryHandler;
import com.turkcell.productservice.query.list.ListProductQuery;
import com.turkcell.productservice.query.list.ListProductQueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {
    private final CreateProductCommandHandler createProductCommandHandler;
    private final ListProductQueryHandler listProductQueryHandler;
    private final GetByIdProductQueryHandler getByIdProductQueryHandler;

    public ProductsController(CreateProductCommandHandler createProductCommandHandler, ListProductQueryHandler listProductQueryHandler, GetByIdProductQueryHandler getByIdProductQueryHandler) {
        this.createProductCommandHandler = createProductCommandHandler;
        this.listProductQueryHandler = listProductQueryHandler;
        this.getByIdProductQueryHandler = getByIdProductQueryHandler;
    }

    @PostMapping
    public UUID create(@RequestBody CreateProductCommand command) throws JsonProcessingException {
        return createProductCommandHandler.handle(command);
    }

    @GetMapping
    public Page<com.turkcell.productservice.entity.Product> getProducts(@RequestParam int pageIndex, @RequestParam int pageSize) {
        ListProductQuery query = new ListProductQuery(pageIndex, pageSize);
        return listProductQueryHandler.getProducts(query);
    }

    @GetMapping("{id}")
    public com.turkcell.productservice.entity.Product getProduct(@PathVariable UUID id) {
        System.out.println("Get product by id: " + id);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        GetByIdProductQuery query = new GetByIdProductQuery(id);
        return getByIdProductQueryHandler.handle(query);
    }

    //@PreAuthorize("#id == authentication.credentials.claims.sub or hasAuthority('Admin')")
    @GetMapping("user")
    @PreAuthorize("hasAnyAuthority('Product.Read','Admin')")
    public String getUser(@AuthenticationPrincipal Jwt jwt)
    {
        return "Giriş yapılmış kullanıcı id: " + jwt.getSubject();
    }



    public record Product(int id, String name, String description) {}
}
