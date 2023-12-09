FROM openjdk:17
ARG JAR_FILE=build/libs/pharmacyRecommendSystem.jar
COPY ${JAR_FILE} ./pharmacyRecommendSystem.jar
ENV TZ=Asia/Seoul
ENTRYPOINT ["java", "-jar", "./pharmacyRecommendSystem.jar"]
