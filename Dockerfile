# Utiliser l'image Gradle avec JDK 17
FROM docker.io/gradle:jdk17 AS build


# Définir le répertoire de travail dans le conteneur
WORKDIR /app
#COPY . /app

# Copier tout le projet dans le conteneur
#RUN gradle --no-daemon build 


# Installer Kobweb CLI
RUN wget https://github.com/varabyte/kobweb-cli/releases/download/v0.9.18/kobweb-0.9.18.zip \
    && unzip kobweb-0.9.18.zip \
    && mv kobweb-0.9.18 /usr/local/kobweb \
    && chmod +x /usr/local/kobweb/bin/kobweb \
    && ln -s /usr/local/kobweb/bin/kobweb /usr/local/bin/kobweb


# Exposer le port 8080 (ou celui défini dans ton projet)
EXPOSE 8080



# Lancer Kobweb en mode dev
CMD ./gradlew kobwebStart -PkobwebEnv=DEV -PkobwebRunLayout=FULLSTACK && tail -f /dev/null
#CMD ["./gradlew", "kobwebStart", "-PkobwebEnv=DEV", "-PkobwebRunLayout=FULLSTACK", "--continuous"]





