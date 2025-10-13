package com.turkcell.reviewservice.controller;

import com.turkcell.reviewservice.contract.v2.api.ReviewsV2Api;
import com.turkcell.reviewservice.contract.v2.model.CreateReview201Response;
import com.turkcell.reviewservice.contract.v2.model.CreateReviewRequest;
import com.turkcell.reviewservice.contract.v2.model.CreateReviewRequestV2;
import com.turkcell.reviewservice.contract.v2.model.CreateReviewV2201Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReviewsV2Controller implements ReviewsV2Api {

    @Override
    public ResponseEntity<CreateReviewV2201Response> createReviewV2(CreateReviewRequestV2 createReviewRequestV2) {
        System.out.println("v2 çalıştı");
        return null;
    }
    @RequestMapping("/api/v2/reviews")
    @GetMapping
    public List<Review> getReviewsByProductId(@RequestParam int productId) {
        return List.of(
                new Review(1,"Ali",5,"Mükemmel ürün!"),
                new Review(2,"Veli",2, "Ortalama ürün.")
        );
    }

    public record Review(int id, String author, int rating, String description) {}
}
