package com.DrWait.domain.PlaceRecommendation.dto;

import lombok.Data;

@Data
public class PlaceRecommendationResultDto {
    private String placeName;
    private int distance;       // 거리(미터)
    private int walkingTime;    // 왕복 도보 시간(분)
    private int stayTime;       // 예상 체류? 시간
    private int totalTime; // 총 예상시간
}
