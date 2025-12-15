FROM eclipse-temurin:21-jdk

# Crée le répertoire de travail
WORKDIR /app

# Copie le jar construit dans le conteneur
COPY target/billetterie-service-0.0.1-SNAPSHOT.jar app.jar

# Définit la commande à exécuter au lancement du conteneur
ENTRYPOINT ["java", "-jar", "app.jar"]
