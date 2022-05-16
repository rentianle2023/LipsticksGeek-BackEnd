FROM openjdk:11
EXPOSE 8080
ADD target/springboot-lipstick.jar springboot-lipstick.jar
ENTRYPOINT ["java","-Dspring.profiles.active=dev","-jar","springboot-lipstick.jar"]