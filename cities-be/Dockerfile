FROM eclipse-temurin:17-jdk-alpine AS builder

RUN addgroup -S cities && adduser -S cities-be -G cities
USER cities-be

WORKDIR /opt/cities-be-build
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw -B clean verify
 
FROM eclipse-temurin:17-jre-alpine
WORKDIR /opt/cities-be
EXPOSE 8080
COPY --from=builder /opt/cities-be-build/target/cities-be.jar /opt/cities-be/cities-be.jar
ENTRYPOINT ["java", "-jar", "/opt/cities-be/cities-be.jar" ]
