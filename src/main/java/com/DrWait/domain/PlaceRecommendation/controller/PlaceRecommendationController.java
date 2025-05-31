package com.DrWait.domain.PlaceRecommendation.controller;

import com.DrWait.domain.PlaceRecommendation.dto.PlaceRecommendationResultDto;
import com.DrWait.domain.PlaceRecommendation.service.PlaceRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/recommend")
@RequiredArgsConstructor
public class PlaceRecommendationController {

    private final PlaceRecommendationService placeRecommendationService;

    @PostMapping
    public ResponseEntity<PlaceRecommendationResultDto> recommendPlaces(
            @RequestParam int waitingTime
    ) {
        PlaceRecommendationResultDto result =
                placeRecommendationService.recommendedCategory(waitingTime);

        return ResponseEntity.ok(result);
    }
}