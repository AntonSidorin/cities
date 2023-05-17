# Cities

Cities with corresponding photo and possibility to search by name, edit city name and photo

Available users:

| Username | Password |
|----------|----------|
| user     | user     |
| admin    | admin    |

## Dev environment
Add proxy in package.json to http://localhost:8080/
```
"proxy":"http://localhost:8080"
```

## How to build
```
mvn clean package
```

## How to run
* docker
```
docker-compose up
```
* docker with build
 ```
docker-compose up --build
```
```
open http://localhost:3000 in browser
```

## How to run locally
* cities-be
```
run CitiesApplication
```
* cities-fe
```
npm start
```
```
open http://localhost:3000 in browser
```
