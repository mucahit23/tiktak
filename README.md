# tiktak

# to set-up db

# run

run docker-compose.yml

# if execute not visible install docker plugin

# example api curls

# Get- latest expertise for car

curl --location 'localhost:8181/responses/123456'

# create expertise

curl --location 'localhost:8181/responses' \
--header 'Content-Type: application/json' \
--data '{
"carId": 123456,
"questionResponses": [
{
"questionId": 1,
"response": true,
"imageUrls": [
"https://example.com/image145454431.jpg",
"https://example.com/image25rew44323.jpg",
"https://example.com/image25rew4433.jpg"
],
"description": "Description for question 1"
},
{
"questionId": 2,
"response": false,
"description": "Description for question 2"
},
{
"questionId": 3,
"response": true,
"imageUrls": [
"https://example.com/image42.jpg"
],
"description": "Description for question 3"
}
]
}'

#to build project
mvn clean compile install

#start project
run or debug TiktakApplication.java class

#api document url
http://localhost:8181/swagger-ui/index.html


