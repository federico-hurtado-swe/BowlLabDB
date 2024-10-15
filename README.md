# BowlLabDB

## Pre-reqs

1. Install maven
2. Install docker
3. Start docker

## Commands (do in this order)

1. Connect to DB: docker-compose up 
    ## you can also do docker-compose up -d to run in detached mode
2. Compile project: mvn compile
3. Build project: mvn clean package
4. Run project: mvn spring-boot:run

### Notes

- The data is only persited as long as the docker container is running. If you terminate Docker,
  the data will be lost. I think there is a way to make it so that docker stores this data
  forever, but I haven't looked into it yet.
