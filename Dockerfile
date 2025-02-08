# Utiliza una imagen base de Java
FROM openjdk:17

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia el archivo JAR de la aplicación al contenedor
COPY target/quePague-0.0.1-SNAPSHOT.jar quePague_backend.jar

# Las variables de entorno las paso en con el comando RUN o a traves del docker-compose.yml

# Expone el puerto especificado
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "quePague_backend.jar"]
