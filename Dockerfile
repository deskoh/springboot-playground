FROM gradle:8.7.0-jdk21-alpine AS builder

# Set working directory
WORKDIR /app

# Copy the Gradle build files
COPY build.gradle .
COPY settings.gradle .
# COPY gradle.properties .

# Copy the application source code
COPY src ./src

# Build the application
RUN gradle bootJar -x test

RUN java -Djarmode=layertools -jar build/libs/demo-0.0.1-SNAPSHOT.jar extract

# ARG JAR_FILE=target/*.jar
# COPY ${JAR_FILE} application.jar
# RUN java -Djarmode=layertools -jar application.jar extract


# Use AdoptOpenJDK 11 as base image for smaller image size
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /app/dependencies/ ./
COPY --from=builder /app/snapshot-dependencies/ ./
COPY --from=builder /app/spring-boot-loader/ ./
COPY --from=builder /app/application/ ./

# Expose the port
EXPOSE 8080

# Health check against Actuator
HEALTHCHECK --interval=30s --timeout=10s --start-period=10s --retries=3 \
  CMD wget -qO- http://localhost:9001/actuator/health || exit 1

ENV SPRING_PROFILES_ACTIVE=production

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} org.springframework.boot.loader.launch.JarLauncher ${0} ${@}"]
