package com.DrWait.core.security.token;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// TODO: 실제 서비스에서는 Redis에 저장해야 보안/확장성 확보
@Component
public class RefreshTokenStore {

    private final Map<String , String> store = new ConcurrentHashMap<>();

    public void save(String email, String refreshToken){
        store.put(email, refreshToken);
    }

    public String get(String email){
        return store.get(email);
    }

    public void remove(String email){
        store.remove(email);
    }
}
