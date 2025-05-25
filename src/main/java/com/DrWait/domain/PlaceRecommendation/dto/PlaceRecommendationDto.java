package com.DrWait.domain.PlaceRecommendation.dto;

import lombok.Data;

@Data
public class PlaceRecommendationDto {
    private Long userId;
    private String placeName;
    private double x;
    private double y;
    private String address;
    private String category;
}
