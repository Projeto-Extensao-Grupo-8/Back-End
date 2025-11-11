# ===============================
# 1️⃣ Etapa de Build (compilação)
# ===============================
FROM eclipse-temurin:21-jdk AS builder

# Diretório de trabalho dentro do container
WORKDIR /app

# Copia os arquivos de configuração do projeto (para cache das dependências)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Faz o download das dependências antes de copiar o código-fonte
RUN ./mvnw dependency:go-offline

# Copia o código-fonte
COPY src ./src

# Compila o projeto e empacota em um .jar
RUN ./mvnw clean package -DskipTests

# ===============================
# 2️⃣ Etapa Final (runtime leve)
# ===============================
FROM eclipse-temurin:21-jre

# Diretório da aplicação
WORKDIR /app

# Copia o artefato da etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
