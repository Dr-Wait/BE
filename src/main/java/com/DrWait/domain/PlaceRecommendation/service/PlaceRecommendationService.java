package com.DrWait.domain.PlaceRecommendation.service;

import com.DrWait.domain.PlaceRecommendation.dto.PlaceRecommendationResultDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaceRecommendationService {

    public PlaceRecommendationResultDto recommendedCategory(int waitingTime) {
        int availableTime = waitingTime - 5; //최대이동시간5분

        List<String> categories = new ArrayList<>();
        if (availableTime >= 0) categories.add("공원");
        if (availableTime >= 5) categories.add("편의점");
        if (availableTime >= 5) categories.add("빵집");
        if (availableTime >= 15) categories.add("서점");
        if (availableTime >= 15) categories.add("드럭스토어");
        if (availableTime >= 20) categories.add("생활용품점");
        if (availableTime >= 20) categories.add("카페");
        if (availableTime >= 20) categories.add("은행");
        if (availableTime >= 25) categories.add("패스트푸드점");
        if (availableTime >= 25) categories.add("죽집");

        PlaceRecommendationResultDto resultDto = new PlaceRecommendationResultDto();
        resultDto.setCategory(categories);

        return resultDto;
    }
}
