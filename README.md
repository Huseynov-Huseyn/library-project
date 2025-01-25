# Library Project

## Overview
This Java-based application is designed to manage a library system efficiently. It provides users with the ability to add, update, and remove books, as well as search and modify book and user information. The application ensures seamless management of library data through its intuitive and user-friendly features.

## Features
- **Book Management:**
  - Add, update, and delete books from the library.
  - Search for books by various criteria (e.g., title, author, genre).
- **User Management:**
  - Add, update, and manage user information.
- **Data Validation:**
  - Ensures the integrity and accuracy of user input.
- **Exception Handling:**
  - Global exception handling for better error responses.
- **Role-Based Access Control:**
  - Secure access to features based on user roles.
- **Testing:**
  - Unit and integration testing for reliable functionality.

## Technologies Used
- **Programming Language:** Java
- **Frameworks:** Spring Boot, Spring Data JPA, Spring Security
- **Database:** H2 Database (embedded)
- **Build Tool:** Maven
- **Testing:** JUnit
- **Version Control:** Git & GitHub

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.8+

### Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/Huseynov-Huseyn/library-project.git
   cd library-project
   ```
2. Build the project using Maven:
   ```bash
   mvn clean install
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

### Accessing the Application
- The application runs on `http://localhost:8080` by default.
- Use API clients (e.g., Postman) or a browser to interact with the endpoints.

## Endpoints
- **Books**
  - `GET /books`: Retrieve all books.
  - `POST /books`: Add a new book.
  - `PUT /books/{id}`: Update book information.
  - `DELETE /books/{id}`: Delete a book.
- **Users**
  - `GET /users`: Retrieve all users.
  - `POST /users`: Add a new user.
  - `PUT /users/{id}`: Update user information.

## Testing
Run tests using Maven:
```bash
mvn test
```

## License
This project is open-source and available under the [MIT License](LICENSE).

## Contact
Developed by **Huseyn Huseynov**  
[GitHub Profile](https://github.com/Huseynov-Huseyn)  
Email: huseynhuseyn343@gmail.com
