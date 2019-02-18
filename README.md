# Build
mvn clean package && docker build -t com.airhacks/javaee8-essentials-archetype .

# RUN

docker rm -f javaee8-essentials-archetype || true && docker run -d -p 8080:8080 -p 4848:4848 --name javaee8-essentials-archetype com.airhacks/javaee8-essentials-archetype 