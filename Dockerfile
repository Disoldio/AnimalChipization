FROM openjdk:20-jdk-slim
EXPOSE 8080
ADD build/libs/AnimalChipization-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]