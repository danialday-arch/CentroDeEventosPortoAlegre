# Etapa 1: Compilación (Usamos Maven con Java 21)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos primero el pom.xml y descargamos dependencias (Optimiza el tiempo de build)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiamos el código fuente y compilamos saltando los tests
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagen ligera de ejecución (Solo el JRE, pesa mucho menos)
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copiamos el .jar generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Comando para ejecutar el microservicio
ENTRYPOINT ["java", "-jar", "app.jar"]