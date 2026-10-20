# ===================================================
# FASE 1: BUILD DA APLICAÇÃO (Maven + JDK 21)
# ===================================================

# jarBuilder é o nome temporário dado a essa imagem. 
# Essa imagem só baixa o maven e as dependências e faz o build da aplicação.
# Será utilizada a frente como origem do JAR compilado.
FROM maven:3.9.6-eclipse-temurin-21-alpine AS jarBuilder

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia os arquivos de dependência primeiro para otimizar o cache de camadas do Docker
# Aqui a origem é copy [origem] [destino] aonde origem é a maquina real e destino é o container.
# vai pra dentro da pasta /app que marcamos no WORKDIR.
COPY pom.xml .

# Executa o comando do maven para baixar as dependências do POM.xml acima.
RUN mvn dependency:go-offline -B

# Copia o código-fonte da aplicação
# Perceba a pasta src (local) para /app/src (container)
COPY src ./src

# Compila o projeto e gera o arquivo .jar ignorando os testes unitários
RUN mvn clean package -DskipTests

# ===================================================
# FASE 2: RUNTIME DA APLICAÇÃO (JRE 21 Leve)
# ===================================================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Cria um usuário não-root por motivos de segurança
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copia o .jar compilado na Fase 1 para a imagem final
COPY --from=jarBuilder /app/target/*.jar appEstoque.jar

# Render injeta a porta dinamicamente via variável de ambiente PORT (padrão 10000)
EXPOSE 8080


# Comando para iniciar a aplicação Java
# java -jar appEstoque.jar

ENTRYPOINT ["java", "-jar", "appEstoque.jar"]
