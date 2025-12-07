# ---------- 1. BUILD STAGE ----------
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Копируем всё, что нужно для сборки
COPY pom.xml .
COPY src ./src

# Собираем JAR (без тестов — быстрее)
RUN mvn clean package -DskipTests


# ---------- 2. RUN STAGE ----------
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Копируем собранный JAR
COPY --from=build /app/target/*.jar app.jar

# Порт Spring Boot
EXPOSE 9193

# Запуск
ENTRYPOINT ["java", "-jar", "app.jar"]
