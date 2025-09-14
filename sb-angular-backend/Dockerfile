FROM eclipse-temurin:24-jdk AS build

from build AS setup
WORKDIR /app
# Copy source code and Gradle wrapper
COPY . /app

from setup AS dev_runner
RUN echo "Starting dev runner"
RUN chmod +x /app/start.sh
RUN echo "did script"
CMD ["/app/start.sh"]