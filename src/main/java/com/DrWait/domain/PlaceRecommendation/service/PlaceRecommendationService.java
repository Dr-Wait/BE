package com.DrWait.domain.PlaceRecommendation.service;

import com.DrWait.domain.PlaceRecommendation.dto.PlaceRecommendationDto;
import com.DrWait.domain.PlaceRecommendation.dto.PlaceRecommendationResultDto;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlaceRecommendationService {

    public List<PlaceRecommendationResultDto> calculateTotalTime(
            List<PlaceRecommendationDto> placeList,
            double userLat,
            double userLng
    ) {
        // 체류 시간 매핑
        Map<String, Integer> stayTimes = Map.of(
                "공원", 0,
                "편의점", 5,
                "빵집", 5,
                "서점", 15,
                "드럭스토어", 15,
                "생활용품점", 20,
                "카페", 20,
                "은행", 20,
                "패스트푸드점", 25,
                "죽집", 25
        );

        List<PlaceRecommendationResultDto> result = new ArrayList<>();

        for (PlaceRecommendationDto place : placeList) {
            double distance = calculateDistance(userLat, userLng, place.getY(), place.getX());
            int walkingTime = convertToWalkingTime(distance) * 2;
            int stayTime = stayTimes.get(place.getCategory());
            int totalTime = walkingTime + stayTime;

            PlaceRecommendationResultDto dto = new PlaceRecommendationResultDto();
            dto.setPlaceName(place.getPlaceName());
            dto.setDistance((int) distance);
            dto.setWalkingTime(walkingTime);
            dto.setStayTime(stayTime);
            dto.setTotalTime(totalTime);

            result.add(dto);
        }

        result.sort(Comparator.comparingInt(PlaceRecommendationResultDto::getTotalTime));

        return result;
    }

    private double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        double R = 6371000;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    private int convertToWalkingTime(double distance) {
        return (int) Math.ceil(distance / 66);
    }
}
