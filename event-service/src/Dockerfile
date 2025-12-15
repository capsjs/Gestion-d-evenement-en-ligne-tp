FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

# Copier le wrapper Maven et les fichiers de configuration
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Télécharger les dépendances (layer mis en cache)
RUN ./mvnw dependency:go-offline -B

# Copier le code source
COPY src src

# Construire l'application
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
