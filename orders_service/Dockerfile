FROM eclipse-temurin:17-jdk AS build
WORKDIR /workspace
COPY . .
RUN sed -i 's/\r$//' gradlew && chmod +x gradlew && ./gradlew :orders_service:bootJar -x test --no-daemon

FROM eclipse-temurin:17-jre-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY --from=build /workspace/orders_service/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]