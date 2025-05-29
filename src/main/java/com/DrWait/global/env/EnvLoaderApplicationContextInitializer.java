package com.DrWait.global.env;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

/// .env 값을 application.yml에서 직접 사용하기 위한 파일
/// Spring에 커스텀 PropertySource를 등록해서 .env를 환경 변수처럼 인식
public class EnvLoaderApplicationContextInitializer implements ApplicationContextInitializer {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        Dotenv dotenv = Dotenv.configure()
                .filename("dev.env")
                .ignoreIfMissing()
                .load();

        // 1. Spring Environment에 등록
        Map<String, Object> props = new HashMap<>();
        dotenv.entries().forEach(entry -> {
            props.put(entry.getKey(), entry.getValue());

            // 2. 시스템 프로퍼티로도 설정
            System.setProperty(entry.getKey(), entry.getValue());
        });


        applicationContext.getEnvironment()
                .getPropertySources()
                .addFirst(new MapPropertySource("dotenvProperties", props));


        System.out.println("✅ Loaded from dotenv:");
        System.out.println(" - DB_HOST: " + System.getProperty("DB_HOST"));
        System.out.println(" - DB_NAME: " + System.getProperty("DB_NAME"));
        System.out.println(" - DB_USER: " + System.getProperty("DB_USER"));
        System.out.println(" - DB_PASSWORD: " + System.getProperty("DB_PASSWORD"));
    }
}
