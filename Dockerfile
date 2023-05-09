FROM openjdk:17-bullseye
WORKDIR /app
COPY ./build/libs/.jar app.jar
COPY start.sh /app
RUN pip install --user --no-cache-dir -r start.sh
RUN chmod 770 start.sh
CMD ./start.sh
# TODO: Copy the compiled jar
# TODO: Copy the start.sh script
# TODO: Make start.sh executable
# TODO: Set the start command