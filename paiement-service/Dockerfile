
# "alpine" signifie que c'est une version Linux ultra-légère (5MB)
FROM eclipse-temurin:21-jdk-alpine

# 2. Information sur le mainteneur (optionnel mais pro)
LABEL maintainer="ismail.bouasria@ynov.com"

# 3. On crée un volume temporaire (Spring Boot utilise /tmp pour Tomcat)
VOLUME /tmp

# 4. On définit une variable pour cibler ton fichier .jar généré par Maven
# Cela prendra n'importe quel fichier .jar dans le dossier target
ARG JAR_FILE=target/*.jar

# 5. On copie ce fichier .jar de ton PC vers l'intérieur du conteneur
# et on le renomme "app.jar" pour que ce soit plus simple
COPY ${JAR_FILE} app.jar

# 6. La commande qui s'exécute quand le conteneur démarre
ENTRYPOINT ["java", "-jar", "/app.jar"]