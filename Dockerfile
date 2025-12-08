# Stage 1: Build the application
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code and build the package
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the smaller runtime image
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# FIXED: Removed the space in '*. jar'
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# FIXED: Removed the space in 'app. jar'
ENTRYPOINT ["java", "-jar", "app.jar"]