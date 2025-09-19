FROM eclipse-temurin:24-jdk

# Create app directory
WORKDIR /app

# info: Docker builds images in layeres, every COPY and RUN creats a new layer
# Docker caches layers and rebuilds only if  the files in the layer are different

# Copy Gradle wrapper and build files first for better layer caching
# these files/dirs rarely chang
COPY gradlew .
COPY gradle/ gradle/
COPY build.gradle .
COPY settings.gradle* .

# change file permissions -> +x add executive permission
# to be able to run the gradlew script
RUN chmod +x ./gradlew

# Download dependencies in a separate layer,
# cached and only rebuilt if build.gradle changes
# This will change if build files change (new deps)
# --no-daemon keeps memory down and avoids caching issues
RUN ./gradlew dependencies --no-daemon

# Copy source code
# Changes often, but copying is fast
COPY src/ src/

# Expose the port Spring Boot uses
EXPOSE 8080

# Use bootRun with continuous build for development with hot reload
# The --continuous flag enables automatic rebuilding when files change
#CMD ["./gradlew", "bootRun", "--continuous", "--no-daemon"]
CMD ["./gradlew", "bootRun", "--no-daemon"]

# reload fast with docker exec spb_ang_pg-api-1 ./gradlew compileJava --no-daemon
