# javamicroserviceexam
This is the applicants submission for the Technical Exam for Rising Tide Digital. 

## Tech Stack

- Java 17
- Spring Boot 3.2
- Spring Data JPA
- H2 In-Memory Database
- Lombok

## Project Structure

```
src/main/java/com/TechnicalTest/TechnicalExam/
├── controller/
│   └── CustomerController.java      
├── exception/
│   └── AccountExceptionHandler.java 
├── model/
│   ├── AccountModel.java             
│   └── CustomerModel.java           
├── repository/
│   └── CustomerRepository.java      
├── service/
│   └── CustomerService.java         
└── TechnicalExamApplication.java     

src/main/resources/
├── application.properties           
└── data.sql                         
```
### Run the application

```bash
./mvnw spring-boot:run
```

The app will start at `http://localhost:8080`

## API Endpoints

### 1. Create Customer Account

**POST** `/api/v1/account`

**Request Body:**

| Field          | Type      | Required | Validated |
|----------------|-----------|----------|-----------|
| customerName   | Char(50)  | Yes      | No        |
| customerMobile | Char(20)  | Yes      | No        |
| customerEmail  | Char(50)  | Yes      | Yes       |
| address1       | Char(100) | Yes      | No        |
| address2       | Char(100) | No       | No        |
| accountType    | Enum      | Yes      | Yes — `S` (Savings) or `C` (Checking) |

**Example Request:**
```json
{
  "customerName":"Test",
  "customerMobile":"09081234567",
  "customerEmail": "test12345@gmail.com",
  "address1":"test",
  "address2":"test",
  "accountType": "S"
}
```

**Success Response – 201 CREATED:**
```json
{
  "customerNumber": 1000006,
  "transactionStatusCode": 201,
  "transactionStatusDescription": "Customer account created"
}
```

**Failed Response – 400 BAD REQUEST:**
```json
{
  "transactionStatusCode": 400,
  "transactionStatusDescription": "customerEmail is a required field"
}
```

---

### 2. Customer Inquiry

**GET** `/api/v1/account/{customerNumber}`

**Example Request:**
```
GET http://localhost:8080/api/v1/account/10000001
```

**Success Response – 302 FOUND:**
```json
{
    "transactionStatusDescription": "Customer Account found",
    "checking": [
        {
            "availableBalance": 8000.00,
            "accountType": "Checking",
            "accountNumber": 10002
        }
    ],
    "address2": "Brgy. 001",
    "address1": "123 Rizal St, Manila",
    "customerEmail": "john.doe@gmail.com",
    "transactionStatusCode": 302,
    "savings": [
        {
            "availableBalance": 15000.00,
            "accountType": "Savings",
            "accountNumber": 10001
        }
    ],
    "customerNumber": "10000001",
    "customerMobile": "09171234567",
    "customerName": "John Doe"
}
```

**Failed Response – 401 NOT FOUND:**
```json
{
  "transactionStatusCode": 401,
  "transactionStatusDescription": "Customer not found"
}
```

## Sample Data within H2 Database


| Customer Number | Name              | Accounts              |
|-----------------|-------------------|-----------------------|
| 10000001        | John Doe          | Savings + Checking    |
| 10000002        | Maria Clara       | Checking              |
| 10000003        | Keyle Ochoa       | Savings               |
| 10000004        | Truett Marks      | Savings               |
| 10000005        | Maryam Lu         | Checking              |


