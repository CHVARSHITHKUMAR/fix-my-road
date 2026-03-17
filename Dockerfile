FROM maven:3.9.9-eclipse-temurin-21

WORKDIR /app

COPY . .

RUN mvn clean install -DskipTests

CMD ["java", "-jar", "target/fixmyroad-0.0.1-SNAPSHOT.jar"]