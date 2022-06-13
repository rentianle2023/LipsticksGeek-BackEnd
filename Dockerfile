FROM openjdk:11
EXPOSE 8080
ADD target/springboot-lipstick.jar springboot-lipstick.jar
ENTRYPOINT ["java","-Djasypt.encryptor.password=${ENCRYPTOR_PASSWORD}","-Dspring.profiles.active=prod","-jar","springboot-lipstick.jar"]