# Bu src kodun, sıfır bir makinede nasıl kurulacağı konusunda yönergeler içermelidir.
FROM maven:3.9-eclipse-temurin-21-alpine

# alpine linuxla çalışan, içinde 21 jdk kurulu + maven kurulu bir sistem var.
WORKDIR /app

# Bir download işlemi copy işlemine genellikle tercih edilir.
COPY pom.xml ./
# Image'ın build aşaması.
RUN mvn -DskipTests dependency:go-offline
COPY src ./src
# Image'ın build aşaması.
RUN mvn -DskipTests clean package

# Image'ın container oluşturma aşaması (çalışma aşaması.)
ENTRYPOINT ["sh", "-c", "java -jar target/*.jar"]

# 11.20