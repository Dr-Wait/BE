##############################
# 1. Build Stage
##############################
FROM gradle:8.5-jdk21 AS builder
# (여기서 사용하는 Gradle 이미지는 8.x 버전이고 Java 21을 포함합니다.)
# 필요 시 gradle 버전은 공식 https://hub.docker.com/_/gradle 에서 확인 후 조정 가능

# 작업 디렉터리 설정
WORKDIR /home/gradle/project

# 캐시 효율을 위해 먼저 build.gradle, gradle wrapper, settings.gradle 만 복사
COPY build.gradle settings.gradle ./
# (만약 서브모듈 Gradle 파일이 있으면 해당 경로도 추가)

# 소스의 의존성을 미리 받아 빌드 캐싱을 활용
RUN gradle dependencies --no-daemon

# 소스 전체를 복사
COPY . .

# 실제 JAR 생성 (테스트 및 기타 옵션은 필요 시 추가)
RUN gradle clean bootJar --no-daemon -x test

##############################
# 2. Runtime Stage
##############################
FROM eclipse-temurin:21-jdk-jammy AS runtime
# OpenJDK 21 기반의 슬림 이미지를 사용합니다(또는 openjdk:21-jdk-slim 등)
# Jammy 기반 Temurin 이미지는 보안 업데이트가 비교적 빠르게 제공됩니다.

# 애플리케이션 JAR을 담을 디렉터리
WORKDIR /app

# 빌드 스테이지에서 생성된 fat JAR을 복사
COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar

# (필요하다면 .env 파일을 컨테이너 내부로 복사하거나, 실행 시 `-e` 옵션을 통해 주입)
# 예: COPY .env ./

# 컨테이너 환경 변수 설정 (필요하면 여기서 설정 or Docker run 시 주입)
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# 기본적으로 8080 포트를 사용한다고 가정
EXPOSE 8080

# 실행 커맨드 (Gradle 없이 순수 java -jar)
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
