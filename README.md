### Pokemon API - Spring Boot Project  

## Overview:  
This project is a Java Spring Boot application that provides a simple REST API to fetch and manage Pokemon data. The application interacts with the external Pokemon API (https://pokeapi.co) to retrieve Pokemon information and serves it via HTTP endpoints.  

## Endpoints  
1. Get All Pokemon, URL: /api/pokemon  
Method: GET  
Description: Fetches a list of 50 Pokemon information for each Pokemon, including ID, name, and images. 

2. Get Pokemon by ID , URL: /api/pokemon/{id}  
Method: GET  
Description: Retrieves a Pokemon by its ID.  
Example: /api/pokemon/25  

3. Get Pokemon by Name, URL: /api/pokemon/name/{name}  
Method: GET  
Description: Retrieves a Pokemon by its name.  
Example: /api/pokemon/name/pikachu

## Clone the repository: 
git clone https://github.com/muralidodi/pokemonapi.git

Build and run the application to access the API: The application will be available at http://localhost:8088. You can access the API endpoints using a tool like Postman or a web browser.  

## Cross-Origin Resource Sharing (CORS)  
This application allows CORS requests from http://localhost:4200, which is the default address for Angular applications. Modify the @CrossOrigin annotation in the controller if you need to change the allowed origins. 
