package com.DrWait.domain.PlaceRecommendation.controller;

import com.DrWait.domain.PlaceRecommendation.dto.PlaceRecommendationDto;
import com.DrWait.domain.PlaceRecommendation.dto.PlaceRecommendationResultDto;
import com.DrWait.domain.PlaceRecommendation.service.PlaceRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommend")
@RequiredArgsConstructor
public class PlaceRecommendationController {

    private final PlaceRecommendationService placeRecommendationService;

    @PostMapping
    public ResponseEntity<List<PlaceRecommendationResultDto>> recommendPlaces(
            @RequestBody List<PlaceRecommendationDto> placeList,
            @RequestParam double userLat,
            @RequestParam double userLng
    ) {
        List<PlaceRecommendationResultDto> result =
                placeRecommendationService.calculateTotalTime(placeList, userLat, userLng);

        return ResponseEntity.ok(result);
    }
}
