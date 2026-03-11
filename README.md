# 🏦 Banking Application - RESTful API

A fully functional banking application with a custom-built HTTP server and RESTful API, developed as a hands-on Java learning project.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Installation & Setup](#installation--setup)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Database Schema](#database-schema)
- [Testing](#testing)
- [What I Learned](#what-i-learned)
- [Future Enhancements](#future-enhancements)
- [Contributing](#contributing)
- [License](#license)

---

## 🌟 Overview

This is a backend banking application built entirely from scratch as part of a 14-day intensive Java learning project. The project demonstrates understanding of:

- Building HTTP servers from the ground up
- Implementing RESTful API architecture
- Database design and JDBC operations
- Multi-layered application architecture
- Real-world banking operations

**Key Highlight:** No frameworks used - everything built from scratch to understand core concepts!

---

## ✨ Features

### User Management
- ✅ User registration with automatic account creation
- ✅ Secure login authentication
- ✅ Unique username and email validation
- ✅ Auto-generated account numbers (ACC00001, ACC00002, etc.)

### Banking Operations
- 💰 **Deposit**: Add money to accounts
- 💸 **Withdraw**: Remove money from accounts (with balance validation)
- 🔄 **Transfer**: Send money between accounts
- 📊 **Balance Inquiry**: Check current account balance
- 📜 **Transaction History**: View all account transactions

### Technical Features
- 🔧 Custom HTTP server built from scratch
- 🗄️ MySQL database integration
- 📡 RESTful API design
- 🛡️ Input validation and error handling
- 💾 Transaction recording for audit trail

---

## 🛠️ Technology Stack

- **Language:** Java (JDK 8+)
- **Database:** MySQL 8.0
- **Architecture:** Layered (DAO → Service → Handler → Router)
- **Protocol:** HTTP/1.1
- **Data Format:** JSON

**No external frameworks** - Built using core Java libraries:
- `java.net.ServerSocket` for networking
- `java.sql` for database connectivity
- `java.io` for I/O operations

---

## 📁 Project Structure
```
Bank_Application/
├── src/
│   ├── Server.java                 # Main HTTP server
│   ├── ClientHandler.java          # Handles client connections
│   ├── HTTPRequest.java            # Parses HTTP requests
│   ├── HTTPResponse.java           # Builds HTTP responses
│   ├── Router.java                 # Routes requests to handlers
│   ├── RequestHandler.java         # Handles API endpoints
│   │
│   ├── AuthService.java            # Authentication logic
│   ├── BankingService.java         # Banking operations logic
│   │
│   ├── UserDAO.java                # User database operations
│   ├── AccountsDAO.java            # Account database operations
│   ├── TransactionsDAO.java        # Transaction database operations
│   ├── DB_Connection.java          # Database connection manager
│   │
│   ├── Users.java                  # User model
│   ├── Accounts.java               # Account model
│   └── Transactions.java           # Transaction model
│
├── lib/
│   └── mysql-connector-j-9.2.jar   # MySQL JDBC driver
│
└── README.md
```

---

## 📥 Installation & Setup

### Prerequisites

- Java JDK 8 or higher
- MySQL 8.0 or higher
- Git (optional)

### Step 1: Clone the Repository
```bash
git clone https://github.com/yourusername/banking-application.git
cd banking-application
```

### Step 2: Set Up Database

1. Open MySQL Workbench or command line
2. Run the following SQL script:
```sql
CREATE DATABASE IF NOT EXISTS Bank_Application;
USE Bank_Application;

-- Users Table
CREATE TABLE Users(
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Username VARCHAR(100) NOT NULL UNIQUE,
    Email VARCHAR(100) NOT NULL UNIQUE,
    Password VARCHAR(255) NOT NULL,
    Phone_Number VARCHAR(20) NOT NULL,
    Created_At TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Accounts Table
CREATE TABLE Accounts(
    ID INT AUTO_INCREMENT PRIMARY KEY,
    User_ID INT NOT NULL,
    Account_Number VARCHAR(20) UNIQUE NOT NULL,
    Balance DECIMAL(15, 2) DEFAULT 0.00 NOT NULL,
    Account_Type VARCHAR(20) DEFAULT 'SAVINGS' NOT NULL,
    Status VARCHAR(20) DEFAULT 'ACTIVE' NOT NULL,
    Created_At TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (User_ID) REFERENCES Users(ID)
);

-- Transactions Table
CREATE TABLE Transactions(
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Account_ID INT NOT NULL,
    Transaction_Type VARCHAR(20) NOT NULL,
    Amount DECIMAL(15, 2) NOT NULL,
    Balance_After DECIMAL(15, 2) NOT NULL,
    Recipient_Account_ID INT NULL,
    Description VARCHAR(255) NULL,
    Transaction_Date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Status VARCHAR(20) DEFAULT 'SUCCESS' NOT NULL,
    FOREIGN KEY (Account_ID) REFERENCES Accounts(ID),
    FOREIGN KEY (Recipient_Account_ID) REFERENCES Accounts(ID)
);
```

### Step 3: Configure Database Connection

Open `DB_Connection.java` and update with your MySQL credentials:
```java
private static final String URL = "jdbc:mysql://localhost:3306/Bank_Application";
private static final String USER = "your_mysql_username";
private static final String PASSWORD = "your_mysql_password";
```

### Step 4: Add MySQL JDBC Driver

1. Download MySQL Connector/J from [MySQL website](https://dev.mysql.com/downloads/connector/j/)
2. Place the `.jar` file in the `lib/` folder
3. Add to your IDE's classpath

---

## 🚀 Running the Application

### Option 1: Run from IDE (IntelliJ IDEA / Eclipse)

1. Open the project in your IDE
2. Make sure MySQL JDBC driver is in the classpath
3. Run `Server.java`
4. You should see: `Server started. Listening on port 8080`

### Option 2: Run from Command Line
```bash
# Compile all Java files
javac -cp ".:lib/mysql-connector-j-9.2.jar" src/*.java

# Run the server
java -cp ".:lib/mysql-connector-j-9.2.jar:src" Server
```

**Server will start on:** `http://localhost:8080`

---

## 📖 API Documentation

### Base URL
```
http://localhost:8080
```

### Endpoints

#### 1. Register User
```http
POST /api/register
Content-Type: application/json

{
    "name": "John Doe",
    "username": "johndoe",
    "email": "john@example.com",
    "password": "password123",
    "phoneNumber": "08012345678"
}
```

**Response:**
```json
{
    "message": "Registration successful! Your account number is: ACC00001"
}
```

---

#### 2. Login
```http
POST /api/login
Content-Type: application/json

{
    "username": "johndoe",
    "password": "password123"
}
```

**Response:**
```json
{
    "message": "Login successful!"
}
```

---

#### 3. Deposit Money
```http
POST /api/deposit
Content-Type: application/json

{
    "accountId": 1,
    "amount": 5000
}
```

**Response:**
```json
{
    "message": "Deposit successful! New balance: 5000.00"
}
```

---

#### 4. Withdraw Money
```http
POST /api/withdraw
Content-Type: application/json

{
    "accountId": 1,
    "amount": 2000
}
```

**Response:**
```json
{
    "message": "Withdrawal successful! New balance: 3000.00"
}
```

---

#### 5. Transfer Money
```http
POST /api/transfer
Content-Type: application/json

{
    "senderId": 1,
    "recipientAccountNumber": "ACC00002",
    "amount": 1500
}
```

**Response:**
```json
{
    "message": "Transfer successful! New balance: 1500.00"
}
```

---

#### 6. Check Balance
```http
POST /api/balance
Content-Type: application/json

{
    "accountId": 1
}
```

**Response:**
```json
{
    "balance": 5000.00
}
```

---

#### 7. Get Transaction History
```http
POST /api/transactions
Content-Type: application/json

{
    "accountId": 1
}
```

**Response:**
```json
[
    {
        "id": 1,
        "accountId": 1,
        "transactionType": "DEPOSIT",
        "amount": 5000.00,
        "balanceAfter": 5000.00,
        "transactionDate": "2025-03-10T14:30:00",
        "status": "SUCCESS"
    }
]
```

**📄 For complete API documentation, see [API_DOCUMENTATION.md](API_DOCUMENTATION.md)**

---

## 🗄️ Database Schema

### Users
| Column | Type | Constraints |
|--------|------|-------------|
| ID | INT | PRIMARY KEY, AUTO_INCREMENT |
| Name | VARCHAR(100) | NOT NULL |
| Username | VARCHAR(100) | UNIQUE, NOT NULL |
| Email | VARCHAR(100) | UNIQUE, NOT NULL |
| Password | VARCHAR(255) | NOT NULL |
| Phone_Number | VARCHAR(20) | NOT NULL |
| Created_At | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP |

### Accounts
| Column | Type | Constraints |
|--------|------|-------------|
| ID | INT | PRIMARY KEY, AUTO_INCREMENT |
| User_ID | INT | FOREIGN KEY → Users.ID |
| Account_Number | VARCHAR(20) | UNIQUE, NOT NULL |
| Balance | DECIMAL(15,2) | DEFAULT 0.00 |
| Account_Type | VARCHAR(20) | DEFAULT 'SAVINGS' |
| Status | VARCHAR(20) | DEFAULT 'ACTIVE' |
| Created_At | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP |

### Transactions
| Column | Type | Constraints |
|--------|------|-------------|
| ID | INT | PRIMARY KEY, AUTO_INCREMENT |
| Account_ID | INT | FOREIGN KEY → Accounts.ID |
| Transaction_Type | VARCHAR(20) | NOT NULL |
| Amount | DECIMAL(15,2) | NOT NULL |
| Balance_After | DECIMAL(15,2) | NOT NULL |
| Recipient_Account_ID | INT | NULLABLE, FOREIGN KEY → Accounts.ID |
| Description | VARCHAR(255) | NULLABLE |
| Transaction_Date | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP |
| Status | VARCHAR(20) | DEFAULT 'SUCCESS' |

---

## 🧪 Testing

### Using Postman

1. Import the Postman collection (if provided)
2. Start the server
3. Test endpoints in this order:
   - Register a user
   - Login with credentials
   - Deposit money
   - Check balance
   - Withdraw money
   - Transfer to another account
   - View transaction history

### Manual Testing
```bash
# Test with curl
curl -X POST http://localhost:8080/api/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Test User","username":"testuser","email":"test@example.com","password":"test123","phoneNumber":"08012345678"}'
```

---

## 🎓 What I Learned

This project was an intensive learning experience where I built everything from scratch:

### Core Concepts
- ✅ How HTTP protocol works at a low level
- ✅ Request/Response cycle
- ✅ TCP/IP networking with ServerSocket
- ✅ Multithreading for handling multiple clients
- ✅ JSON parsing without libraries

### Software Architecture
- ✅ Layered architecture (separation of concerns)
- ✅ DAO pattern for database operations
- ✅ Service layer for business logic
- ✅ RESTful API design principles

### Database Operations
- ✅ JDBC for database connectivity
- ✅ SQL queries and transactions
- ✅ Foreign key relationships
- ✅ Database normalization

### Problem-Solving
- ✅ Debugging NullPointerExceptions
- ✅ String parsing and manipulation
- ✅ Error handling and validation
- ✅ Testing with real data

---

## 🚀 Future Enhancements

- [ ] Add JWT authentication for security
- [ ] Implement password hashing (BCrypt)
- [ ] Add account statements (PDF generation)
- [ ] Build a web frontend (HTML/CSS/JavaScript)
- [ ] Add email notifications for transactions
- [ ] Implement transaction limits per day
- [ ] Add multi-currency support
- [ ] Create admin dashboard
- [ ] Add logging framework
- [ ] Containerize with Docker

---

## 🤝 Contributing

Contributions are welcome! If you'd like to improve this project:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📝 License

This project is open source and available under the [MIT License](LICENSE).

---

## 👩‍💻 Author

**Rodiyah Musa**

- GitHub: (https://github.com/rodeeyah06)
- LinkedIn: www.linkedin.com/in/rodiyah-musa
- Email: rodiyahmusa@gmail.com

---

## 🙏 Acknowledgments

- Built as part of a 14-day intensive Java learning project
- Special thanks to the Java and MySQL communities
- Inspired by real-world banking systems

---

## 📊 Project Stats
- **Lines of Code:** ~2000+
- **Files:** 16 Java classes
- **Database Tables:** 3
- **API Endpoints:** 7
- **No external frameworks used!**

---

**⭐ If you found this project helpful, please consider giving it a star!**

---

*Last Updated: March 10, 2025*
