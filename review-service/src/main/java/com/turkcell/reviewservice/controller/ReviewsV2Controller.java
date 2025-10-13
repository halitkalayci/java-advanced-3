package com.turkcell.reviewservice.controller;

import com.turkcell.reviewservice.contract.v2.api.ReviewsV2Api;
import com.turkcell.reviewservice.contract.v2.model.CreateReview201Response;
import com.turkcell.reviewservice.contract.v2.model.CreateReviewRequest;
import com.turkcell.reviewservice.contract.v2.model.CreateReviewRequestV2;
import com.turkcell.reviewservice.contract.v2.model.CreateReviewV2201Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewsV2Controller implements ReviewsV2Api {

    @Override
    public ResponseEntity<CreateReviewV2201Response> createReviewV2(CreateReviewRequestV2 createReviewRequestV2) {
        System.out.println("v2 çalıştı");
        return null;
    }
}
