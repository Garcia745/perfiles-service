# Usamos Java 17 runtime ligero sobre Alpine Linux
FROM eclipse-temurin:17-jre-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el JAR compilado al contenedor
COPY build/libs/*.jar app.jar

# Puerto del servicio
EXPOSE 8083

# Comando para ejecutar la aplicación al iniciar el contenedor
ENTRYPOINT ["java", "-jar", "app.jar"]