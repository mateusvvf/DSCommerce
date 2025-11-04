# DS Commerce 
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/mateusvvf/DSCommerce/blob/main/LICENSE) 

# About the Project

**DSCommerce** is a **back-end e-commerce application** developed during the **Java Spring Professional** course by [DevSuperior](https://devsuperior.com "DevSuperior Website").

The system simulates the main operations of an online store, allowing the management of users, products, categories, and orders, with authentication and authorization using **Spring Security** and **JWT**.  
It was built for **educational purposes**, demonstrating best practices in architecture, layered design, and data persistence using **JPA/Hibernate**.

## Conceptual model
![Conceptual Model](https://github.com/mateusvvf/DSCommerce/blob/main/Assets/DSCommerce_Model.png)

# Technologies
## Back end
- Java 21
- Spring Boot
- Spring Data JPA / Hibernate
- Spring Security
- Maven
- Databases: H2 (tests) / PostgreSQL (production)

# How to run

## Back end
**Requirements:** Java 21 and Maven

```bash
# clone repository
git clone https://github.com/mateusvvf/DSCommerce

# navigate to project folder
cd DSCommerce

# run the project
./mvnw spring-boot:run
```

## Once started, the system will be available at:
https://localhost:8080

## Using the H2 database, the console can be accessed at:
https://localhost:8080/h2-console

# Author

Mateus Viana Ventura Figueiredo

https://www.linkedin.com/in/mateus-viana-dev

