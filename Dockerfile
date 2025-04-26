# Utiliza una imagen base de Java
FROM openjdk:17

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia el archivo JAR y el script wait-for-it.sh
COPY target/quePague-0.0.1-SNAPSHOT.jar quePague_backend.jar
COPY wait-for-it.sh wait-for-it.sh

# Da permisos de ejecución al script
RUN chmod +x wait-for-it.sh

# Las variables de entorno las paso en con el comando RUN o a traves del docker-compose.yml

# Expone el puerto especificado
EXPOSE 8080

# Usa wait-for-it para esperar a MySQL (reemplaza host y puerto según tu setup)
CMD ["./wait-for-it.sh", "mysql:3306", "--", "java", "-jar", "quePague_backend.jar"]
