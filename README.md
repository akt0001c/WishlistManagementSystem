
# Project Title
 ## WishList Management System
 
## Introduction
Welcome to the Wishlist Management System, a robust and efficient application for managing user wishlists, products, and more. This project is built using Spring Boot, JPA-Hibernate, MySQL for database storage, and Spring Security for ensuring secure access to the system.

## Project Type
 Backend 



## Directory Structure
```
my-app/
├─ wms1(Backend)/
|-- dbname.sql(dbfile)
```
 




## Features
- User Management: Create and manage user accounts with ease.
- Product Catalog: Maintain a comprehensive catalog of products to be added to wishlists.
- Wishlist Functionality: Allow users to create and manage wishlists, adding their desired products.
- RESTful API: Explore a set of well-defined endpoints for seamless integration with other applications.
- Security: Utilize Spring Security to ensure a secure and protected environment.

## design decisions or assumptions
### db digram
![wmsdb](https://github.com/akt0001c/WishlistManagementSystem/assets/110126989/0df11d91-4d87-4816-b7df-be1f30d4289b)
### db assumptions doc
[----- WishlistManagementSystem ----.txt](https://github.com/akt0001c/WishlistManagementSystem/files/14333013/-----.WishlistManagementSystem.----.txt)

## Installation & Getting started
 ### Clone the repository
Detailed instructions on how to install ,import the database, configure, and get the project running.
### Import the database
  - Create an empty database
  - download dbname.sql file from Repository and save it on your system somewhere
  #### Open the terminal and follow below steps to import database
```
   CD C:\Program Files\MySQL\MySQL Server 8.0\bin
   mysql -u username -p database_name < desiredplace\dbname.sql   
```
- These steps will help you to setup database on your sytem
### Before testing wishlist endpoint for manage wishlist , first add Product using product endpoints then provide valid product id while calling wishlist endpoints 

### Now you can run springBoot application using tools like sts,intellige 



## Credentials
 ### Before Running project first set properties file  , one example of properties file is below  
 ```
  server.port=8080

#db specific properties
spring.datasource.name=db_name
spring.datasource.url=jdbc:mysql://localhost:3306/db_name
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password= your password

#ORM system specific properties
#spring.jpa.generate-ddl=true
#spring.jpa.hibernate.ddl-auto=create
#spring.jpa.show-sql=true
```



## API Endpoints
### user
   - Post:: /api/signUp
   - Get:: /api/logIn

### Product
  - Post:: /api/add/product (Request body - Product product)
  - Get:: /api/get/products
  - Delete:: /api/product/{pid}  (PathVariable (pid)= ProductId)

### Wishlist
  - Post:: /api/wishlists (RequestParam("pid") -  {Integer pid} )
  - Delete:: /api/wishlists/{id}  (id-> Product Id)
  - Get:: /api/wishlists


## Technology Stack
List and provide a brief overview of the technologies used in the project.

- Spring Boot: The foundation for building our wishlist management system.
- JPA-Hibernate: For efficient and easy interaction with the database.
- MySQL: A robust relational database management system.
- Spring Security: Ensuring secure authentication and authorization
- Lombok
- Swagger

