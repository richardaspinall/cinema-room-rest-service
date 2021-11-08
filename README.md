# cinema-room-rest-service
> **Date started**: 25 September 21

> **Date ended**: 04 October 21

## Description
REST service project from the Java Backend Developer track at JetBrains Academy

https://www.jetbrains.com/academy/

## Install
* Install gradle: `brew install gradle`
* Run Wrapper to build the project `gradle wrapper`
* Build the project `./gradlew build`
* Start the service: `java -jar build/libs/cinema-room-rest-service.jar`

## Usage

### Display all sets in cinema

> GET `localhost:28852/seats`
> 
> `curl --location --request GET 'localhost:28852/seats'`

### Purchase a seat

> POST `localhost:28852/purchase`
> 
> ```
> curl --location --request POST 'localhost:28852/purchase' \
> --header 'Content-Type: application/json' \
> --data-raw '{
> "row": 1,
> "column": 1
> }'
> ```

### Return a seat

> POST `localhost:28852/return`
> 
> ```
>  curl --location --request POST 'localhost:28852/return' \
> --header 'Content-Type: application/json' \
> --data-raw '{
> "token": ""
> }'
> ```

### Get statistics allocation of seats

> POST `localhost:28852/stats?password=super_secret`
> 
> ```
> curl --location --request POST 'localhost:28852/stats?password=super_secret' \
> --data-raw ''
> ```
---
