# Use an official Maven image as the base image
FROM maven:3.8.5-openjdk-18 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the entire project
COPY . .

# Build the application
RUN mvn clean package -DskipTests

# Use Tomcat as the base image for deployment
FROM tomcat:9.0.76-jdk17-temurin-focal

# Remove the default Tomcat applications
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy the built WAR file from the build stage
COPY --from=build /app/target/ServiceBooking.war /usr/local/tomcat/webapps/ROOT.war

# Expose the port your app runs on
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]