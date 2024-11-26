# Etapa 1: Build usando Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Define o diretório de trabalho para o build
WORKDIR /app

# Copia o código fonte para o contêiner
COPY . .

# Executa o Maven para gerar o .jar
RUN mvn clean package -DskipTests

# Etapa 2: Execução da aplicação
FROM eclipse-temurin:21-jdk-alpine

# Cria usuário não-root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Define o diretório de trabalho para a aplicação
WORKDIR /app

# Copia o .jar gerado na etapa anterior
COPY --from=build /app/target/escola-0.0.1-SNAPSHOT.jar app-v1.jar

# Expondo a porta
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app-v1.jar"]
