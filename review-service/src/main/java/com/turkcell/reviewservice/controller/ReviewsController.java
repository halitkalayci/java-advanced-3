package com.turkcell.reviewservice.controller;

import com.turkcell.reviewservice.contract.v1.api.ReviewsApi;
import com.turkcell.reviewservice.contract.v1.model.CreateReview201Response;
import com.turkcell.reviewservice.contract.v1.model.CreateReviewRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewsController implements ReviewsApi {
    @Override
    public ResponseEntity<CreateReview201Response> createReview(CreateReviewRequest createReviewRequest) {
        return null;
    }
}
