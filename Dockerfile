FROM openjdk:17-bullseye
WORKDIR /app
COPY ./build/libs/H10E01-Containers-1.0.0.jar app.jar
COPY start.sh .
RUN chmod 770 start.sh
CMD ./start.sh
# TODO: Copy the compiled jar
# TODO: Copy the start.sh script
# TODO: Make start.sh executable
# TODO: Set the start command