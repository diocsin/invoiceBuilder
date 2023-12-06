FROM openjdk:17-slim

# Установите необходимые зависимости
RUN apt-get update && \
    apt-get install -y libfreetype6 fontconfig && \
    rm -rf /var/lib/apt/lists/*

# Создайте рабочий каталог
WORKDIR /app

# Копируйте JAR-файл в каталог
COPY build/libs/InvoiceBuilder-0.0.1-SNAPSHOT.jar .

# Запустите приложение при старте контейнера
CMD ["java", "-jar", "InvoiceBuilder-0.0.1-SNAPSHOT.jar"]
