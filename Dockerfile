FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# Copia apenas o necessário para cache de dependências
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# 🔧 Garante permissão de execução
RUN chmod +x mvnw

# 🔥 Baixa dependências (camada cacheável)
RUN ./mvnw dependency:go-offline

# Copia o restante do projeto
COPY src ./src

# Build do JAR
RUN ./mvnw clean package -DskipTests

# =========================
# Stage final (runtime leve)
# =========================
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copia apenas o artefato final
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]
