# Loan Payment System API

A robust, single-application Loan Payment System built with **Spring Boot** and **H2 Database**. This project manages loan creation and retrieval (Loan Domain) and handles payments towards those loans (Payment Domain) with integrated business logic and persistence.

## 🚀 Tech Stack

- **Java**: 1.8
- **Framework**: Spring Boot 2.7.18
- **Database**: H2 (In-Memory)
- **Documentation**: SpringDoc OpenAPI (Swagger UI)
- **Build Tool**: Maven
- **Lombok**: For boilerplate-free entities and models

## ✨ Features

- **Loan Domain**: 
    - Create a new loan with a specified amount and term.
    - Retrieve comprehensive loan details (balance, status, term).
- **Payment Domain**: 
    - Record payments against active loans.
    - Automatic balance reduction and state management.
- **Business Logic**: 
    - **Overpayment Prevention**: Rejects payments that exceed the remaining balance.
    - **Status Management**: Automatically transitions loans from `ACTIVE` to `SETTLED` upon full repayment.
- **Persistence**: Data is stored in an H2 in-memory database for rapid testing and evaluation.
- **Documentation**: Fully interactive Swagger UI and Postman Collection.

## 🛠️ Prerequisites

- **JDK 1.8**
- **Maven 3.6+** (or use the included `mvnw` wrapper)

## 🏃‍♂️ Getting Started

### 1. Build the Application
```bash
./mvnw clean package
```

### 2. Run the Application
```bash
./mvnw spring-boot:run
```
The application will start on port **8080**.

## 📚 API Documentation

### Swagger UI (Interactive)
Explore and test the API directly in your browser: 👉 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

- **OpenAPI JSON Spec**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

### Postman Collection
An optimized Postman collection is included in the project root: 📄 `Loan_Payment_System_API.postman_collection.json`

**How to use:**
1. Open Postman -> **Import** -> Select the file.
2. The collection uses a `base_url` variable (default: `http://localhost:8080`).
3. You can test **Create Loan**, **Get Loan Details**, and **Record Payment** endpoints.

## 🗄️ Database Access (H2 Console)
You can inspect the in-memory database while the application is running.

- **URL**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- **JDBC URL**: `jdbc:h2:mem:loandb`
- **Username**: `sa`
- **Password**: (leave blank)

## 🧪 Testing
Run the unit tests to ensure business logic correctness:
```bash
./mvnw test
```

## 📂 Project Structure
`src/main/java/com/example/LoanPaymentApplication/`
├── **controller/** &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; # REST Endpoints (LoanController, PaymentController)
├── **entity/** &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; # JPA Entities (Loan, Payment)
├── **exception/** &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; # Global Exception Handling
├── **repository/** &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; # Data Access Layer (LoanRepository, PaymentRepository)
└── **service/** &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; # Business Logic (LoanService, PaymentService)

## 📝 Design Decisions

- **Domain Separation**: The system is logically split into Loan and Payment domains within the same application to maintain a clear separation of concerns as per the assessment requirements.
- **State Management**: Implemented an automated status transition from `ACTIVE` to `SETTLED` when the loan's remaining balance reaches zero.
- **Error Handling**: Custom exceptions like `OverpaymentException` and `LoanNotFoundException` are used to provide clear, actionable error messages via a `GlobalExceptionHandler`.
- **API Contract**: The API strictly follows the specified endpoints and request/response formats for both loan and payment management.
