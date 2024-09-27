# Use an official Maven image as the base image
FROM maven:3.8.5-openjdk-18 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the entire project
COPY . .

# Build the application
RUN mvn clean package -DskipTests

# Use an official OpenJDK runtime as the base image
FROM openjdk:18-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built WAR file from the build stage
COPY --from=build /app/target/ServiceBooking.war app.war

# Expose the port your app runs on
EXPOSE 8080

# Run the WAR file
ENTRYPOINT ["java", "-jar", "app.war"]