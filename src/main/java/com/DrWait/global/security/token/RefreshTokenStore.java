package com.DrWait.global.security.token;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// TODO: 실제 서비스에서는 Redis에 저장해야 보안/확장성 확보
@Component
public class RefreshTokenStore {

    private final Map<String , String> store = new ConcurrentHashMap<>();

    public void save(String userId, String refreshToken){
        store.put(userId, refreshToken);
    }

    public String get(String userId){
        return store.get(userId);
    }

    public void remove(String userId){
        store.remove(userId);
    }
}
