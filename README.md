#  Online Book Store

> Online Book Store is a Java Spring Boot application designed to provide 
> a comprehensive solution for managing an online bookstore. With this application, 
> users can browse a wide selection of books, create personalized shopping carts,
> and securely purchase items online. 
> Also, administrators have access for managing inventory, processing orders, and analyzing sales data.

#  Tech stack
- **Backend:** Spring Framework (Spring Boot, Spring Security, Spring Data JPA)

- **Database:** MySQL

- **ORM:** Hibernate

- **Data mapper:** MapStruct

- **Scheme changes:** Liquibase

- **Containerization:** Docker

- **API Documentation:** Swagger

- **Testing:** JUnit, Mockito, Test Containers

- **Build Tool:** Maven

## Database Scheme

![sheme](scheme.png)

## Features

### Authentication

🔑 Token-based authentication is implemented in the API. To access you are required to acquire a JSON Web Token (JWT) and include it in the Authorization header.

<details>
  <summary>Registration</summary>

* Endpoint ```/api/auth/register```
* HTTP Request: POST
* Request Body:
  ``` 
  {
    "email": "test.user@example.com",
    "password": "12345678",
    "repeatPassword": "12345678",
    "firstName": "Test",
    "lastName": "User",
    "shippingAddress": "123 Main St, City, Country"
  }
  ```
* Response: Status Code 200
* Response Body:
  ``` 
  {
    "id": 1,
    "email": "test.user@example.com",
    "firstName": "Test",
    "lastName": "User",
    "shippingAddress": "123 Main St, City, Country"
  }
  ```

</details>

<details>
  <summary>Login</summary>

* Endpoint ```/api/auth/login```
* HTTP Request: POST
* Request Body:
  ``` 
  {
      "email": "test.user@example.com",
      "password": "12345678"
  }
  ```
* Response: Status Code 200
* Response Body:
  ``` 
  {
      "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
  }
  ```

</details>

### Books and Categories

<details>
  <summary>Available books</summary>

* Endpoint ```/api/books```
* HTTP Request: GET
  * Response: Status Code 200
    * Response Body:
      ```
      [
          {
              "id": 1,
              "title": "Animal Farm",
              "author": "George Orwell",
              "isbn": "24546555677676776",
              "price": 30.00,
              "description": "Animal Farm is a beast fable, 
                in the form of a satirical allegorical novella, 
                by George Orwell, 
                first published in England on 17 August 1945",
              "coverImage": "https://www.example.com/images/book1.jpg",
              "categories": [2]
          },
          {
              "id": 2,
              "title": "Women",
              "author": "Charles Bukowski",
              "isbn": "735934362372328632",
              "price": 27.00,
              "description": "Women is a 1978 novel written by Charles Bukowski, 
                starring his semi-autobiographical character Henry Chinaski.",
              "coverImage": "https://www.example.com/images/book2.jpg",
              "categories": [1]
          }
      ]
      ```

</details>

<details>
  <summary>Certain book</summary>

* Endpoint ```/api/books/1```
* HTTP Request: GET
* Response: Status Code 200
* Response Body:
  ```
  {
      "id": 1,
      "title": "Animal Farm",
      "author": "George Orwell",
      "isbn": "24546555677676776",
      "price": 30.00,
      "description": "Animal Farm is a beast fable, 
        in the form of a satirical allegorical novella, 
        by George Orwell,
        first published in England on 17 August 1945",
      "coverImage": "https://www.example.com/images/book3.jpg",
      "categories": [2]
  }
  ```

</details>

<details>
  <summary>Available categories</summary>

* Endpoint ```/api/categories```
* HTTP Request: GET
* Response: Status Code 200
* Response Body:
  ```
  [
      {
          "id": 1,
          "name": "Autobiographical novel",
          "description": "Autobiographical novel books"
      },
      {
          "id": 2,
          "name": "Political satire",
          "description": "Political satire books"
      },
  ]
  ```

</details>

<details>
  <summary>Certain category</summary>

* Endpoint ```/api/categories/1```
* HTTP Request: GET
* Response: Status Code 200
* Response Body:
  ```
  {
      "id": 1,
      "name": "Autobiographical novel",
      "description": "Autobiographical novel books"
  }
  ```

</details>

<details>
  <summary>Available books by category</summary>

* Endpoint ```/api/categories/1/books```
* HTTP Request: GET
* Response: Status Code 200
* Response Body:
  ```
  {
      "id": 1,
      "title": "Animal Farm",
      "author": "George Orwell",
      "isbn": "24546555677676776",
      "price": 30.00,
      "description": "Animal Farm is a beast fable, 
        in the form of a satirical allegorical novella, 
        by George Orwell,
        first published in England on 17 August 1945",
      "coverImage": "https://www.example.com/images/book3.jpg",
      "categories": [2]
  }
  ```

</details>

### Shopping Cart

<details>
  <summary>Add book to shopping cart</summary>

* Endpoint ```/api/cart```
* HTTP Request: POST
* Request Body:
  ```
  {
    "bookId": "1",
    "quantity": "3"
  }
  ```
* Response Body:
  ```
  {
      "id": 1,
      "bookId": 1,
      "bookTitle": "Animal Farm",
      "quantity": 3
  }
  ```

</details>

<details>
  <summary>User's shopping cart</summary>

* Endpoint ```/api/cart```
* HTTP Request: GET
* Response: Status Code 200
* Response Body:
  ```
  [
      {
          "id": 1,
          "bookId": 1,
          "bookTitle": "Animal Farm",
          "quantity": 3
      }
  ]
  ```

</details>

<details>
  <summary>Update item in shopping cart</summary>

* Endpoint ```/api/cart/cart-items/1```
* HTTP Request: PUT
* Request Body:
  ```
  {
    "quantity": "2"
  }
  ```
* Response Body:
  ```
  {
      "id": 1,
      "bookId": 1,
      "bookTitle": "Animal Farm",
      "quantity": 2
  }
  ```

</details>

<details>
  <summary>Delete item from shopping cart</summary>

* Endpoint ```/api/cart/cart-items/1```
* HTTP Request: DELETE

</details>

### Orders

<details>
  <summary>Place an order</summary>

* Endpoint ```/api/orders```
* HTTP Request: POST
* Request Body:
  ```
  {
      "shippingAddress": "123 Main St, City, Country"
  }
  ```
* Response Body:
  ```
  {
      "id": 1,
      "userId": 1,
      "orderItems": [
          {
              "id": 1,
              "bookId": 1,
              "quantity": 3
          }
      ],
      "orderDate": "2024-04-02T14:10:23.316180965",
      "total": 90.00,
      "status": "PENDING"
  }
  ```

</details>

<details>
  <summary>Orders history</summary>

* Endpoint ```/api/orders```
* HTTP Request: GET
* Response Body:
  ```
  [
      {
          "id": 1,
          "userId": 1,
          "orderItems": [
              {
                  "id": 1,
                  "bookId": 1,
                  "quantity": 3
              }
          ],
          "orderDate": "2024-04-02T14:20:30.316180965",
          "total": 90.00,
          "status": "PENDING"
      }
  ]
  ```

</details>

<details>
  <summary>Certain order (returns all items from order)</summary>

* Endpoint ```/api/orders/1/items```
* HTTP Request: GET
* Response Body:
  ```
  [
        {
            "id": 1,
            "bookId": 1,
            "quantity": 3
        }
  ]
  ```

</details>

<details>
  <summary>Certain item from certain order</summary>

* Endpoint ```/api/orders/1/items/1```
* HTTP Request: GET
* Response Body:
  ```
  {
      "id": 1,
      "bookId": 1,
      "quantity": 3
  }
  ```

</details>

### For administrators

### Books and categories

<details>
  <summary>Create new book</summary>

* Endpoint ```/api/books```
* HTTP Request: POST
* Request Body:
  ```
  {
        "title": "Three Comrades",
        "author": "Erich Maria Remarque",
        "isbn": "97831614s4100",
        "price": 25.50,
        "description": "Three Comrades is a 1936 
          novel by the German author Erich Maria Remarque",
        "coverImage": "https://www.example.com/images/book3.jpg",
        "categories": [1]
  }
  ```
* Response Body:
  ```
  {
      "id": 1,
      "title": "Three Comrades",
      "author": "Erich Maria Remarque",
      "isbn": "9783161484100",
      "price": 17.50,
      "description": "Three Comrades is a 1936 
          novel by the German author Erich Maria Remarque",
      "coverImage": "https://www.example.com/images/book3.jpg",
      "categories": [1]
  }
  ```

</details>

<details>
  <summary>Update existing book</summary>

* Endpoint ```/api/books/1```
* HTTP Request: PUT
* Request Body:
  ```
  {
        "title": "Three Comrades",
        "author": "Erich Maria Remarque",
        "isbn": "97831614s4100",
        "price": 29.50,
        "description": "Three Comrades (German: Drei Kameraden) is a 1936 
          novel by the German author Erich Maria Remarque. ",
        "coverImage": "https://www.example.com/images/book3.jpg",
        "categories": [1]
  }
  ```
* Response Body:
  ```
  {
        "title": "Three Comrades",
        "author": "Erich Maria Remarque",
        "isbn": "97831614s4100",
        "price": 29.50,
        "description": "Three Comrades (German: Drei Kameraden) is a 1936 
          novel by the German author Erich Maria Remarque. ",
        "coverImage": "https://www.example.com/images/book3.jpg",
        "categories": [1]
  }
  ```
</details>

<details>
  <summary>Delete existing book</summary>

* Endpoint ```/api/books/1```
* HTTP Request: DELETE

</details>

<details>
  <summary>Create category</summary>

* Endpoint ```/api/categories```
* HTTP Request: POST
* Request Body:
  ```
  {
      "name": "Horror",
      "description": "Horror books"
  }
  ```
* Response Body:
  ```
  {
      "id": 3,
      "name": "Horror",
      "description": "Horror books"
  }
  ```

</details>

<details>
  <summary>Update existing category</summary>

* Endpoint ```/api/categories/1```
* HTTP Request: PUT
* Request Body:
  ```
  {
      "name": "Horror",
      "description": "Horror and not only books"
  }
  ```
* Response Body:
  ```
  {
      "id": 3,
      "name": "Horror",
      "description": "Horror and not only books"
  }
  ```

</details>


<details>
  <summary>Delete existing category</summary>

* Endpoint ```/api/categories/1```
* HTTP Request: DELETE

</details>

### Orders

<details>
  <summary>Update existing order</summary>


* Endpoint ```/api/orders/1```
* HTTP Request: PATCH
* Request Body:
  ```
  {
      "status": "DELIVERED"
  }
  ```
* Response Body:
  ```
  [
      {
          "id": 1,
          "userId": 1,
          "orderItems": [
              {
                  "id": 1,
                  "bookId": 1,
                  "quantity": 3
              }
          ],
          "orderDate": "2024-04-02T14:10:23.316180965",
          "total": 90.00,
          "status": "DELIVERED"
      }
  ]
  ```

</details>

###  Challenges
#### Global Exception Handling:
Developed a centralized global exception handler that intercepts 
and processes exceptions thrown by controllers, services, and other components. 
I used the @ControllerAdvice annotation in Spring MVC to define a global exception handling 
class that contains methods annotated with @ExceptionHandler to handle specific exception types. 
Also, I implemented error response DTOs (Data Transfer Objects) to format and 
standardize error responses returned to clients, including HTTP status codes, 
error messages, and additional metadata.

###  How to run Book Store API

1) Install Docker
2) Clone current project repository
3) Add your ".env" file (see .env.example)
3) Configure a ".env" file with necessary environment variables
4) Run the command mvn clean package
5) Use docker-compose up to run Docker container
6) Access the locally running application at http://localhost:{SPRING_LOCAL_PORT}/api
