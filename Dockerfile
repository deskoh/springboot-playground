FROM gradle:8.7.0-jdk17-alpine AS builder

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

# Optional:
# RUN mkdir -p build/extracted
# RUN java -Djarmode=layertools -jar build/libs/demo-0.0.1-SNAPSHOT.jar extract --destination build/extracted

# Use AdoptOpenJDK 11 as base image for smaller image size
FROM eclipse-temurin:17-jre-alpine

# Set working directory
WORKDIR /app

# Option 1: Copy the JAR file from the previous stage
ARG JAR_FILE=demo-0.0.1-SNAPSHOT.jar
COPY --from=builder /app/build/libs/${JAR_FILE} app.jar

# Option 2: Copy the compiled classes and resources from the Gradle build directory
# ARG DEPENDENCY=/app/build/extracted
# COPY --from=builder ${DEPENDENCY}/dependencies/BOOT-INF/lib lib/
# COPY --from=builder ${DEPENDENCY}/application/BOOT-INF/classes .
# COPY --from=builder ${DEPENDENCY}/application/META-INF META-INF/

# Expose the port
EXPOSE 8080

# Health check against Actuator
HEALTHCHECK --interval=30s --timeout=10s --start-period=10s --retries=3 \
  CMD wget -qO- http://localhost:9001/actuator/health || exit 1

ENV SPRING_PROFILES_ACTIVE=production

# Option 1: Run the jar
# See https://spring.io/guides/topicals/spring-boot-docker
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar ${0} ${@}"]

# Option 2: Run the classes
# Replace com.example.Application with your main class
# ENTRYPOINT ["java","-cp","lib/*","com.example.demo.DemoApplication"]
# ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -cp lib/* com.example.demo.DemoApplication ${0} ${@}"]

