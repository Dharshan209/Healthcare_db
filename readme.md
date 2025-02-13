# Janitri Health Monitor

## 📌 Project Overview
This project is a **full-stack health monitoring system** built using **Spring Boot, MySQL, and a frontend with HTML, CSS, and JavaScript**. The system allows users to **register, log in, add patients, and record heart rate data**.

---

## 🛠️ Tech Stack
- **Backend:** Spring Boot (Java), Spring Data JPA, MySQL
- **Frontend:** HTML, CSS, JavaScript
- **API Testing:** Postman
- **Build Tool:** Maven

---

## 🚀 Setup & Run Instructions

### **1️⃣ Clone the Repository**
```sh
git clone https://github.com/your-repo/janitri-health-monitor.git
cd janitri-health-monitor
```

### **2️⃣ Backend Setup**
#### **📌 Prerequisites**
- Install **Java 17**
- Install **MySQL**
- Install **Maven**

#### **📌 Database Configuration**
1. Open MySQL and create a database:
   ```sql
   CREATE DATABASE janitri_db;
   ```
2. Update `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/janitri_db
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
   ```

#### **📌 Run the Backend**
```sh
mvn clean install
mvn spring-boot:run
```

The backend will start at `http://localhost:8080`.

---

### **3️⃣ Frontend Setup**
#### **📌 Prerequisites**
- A web browser (Chrome, Firefox, Edge, etc.)

#### **📌 Run the Frontend**
1. Open `frontend/index.html` in a browser.
2. **Ensure the backend is running** before using the app.

---

## 🔑 Authentication & User Flow
1. **User Registers** → `POST /api/users/register`
2. **User Logs In** → `POST /api/users/login`
3. **After Login** → User can:
   - **Add Patients** (`POST /api/patients`)
   - **View Patients** (`GET /api/patients/user/{userId}`)
   - **Add Heart Rate Records** (`POST /api/heartrate`)
   - **View Heart Rate Records** (`GET /api/heartrate/patient/{patientId}`)

---

## 📖 API Documentation

### **User APIs**
- **Register User:** `POST /api/users/register`
  ```json
  {
      "email": "test@example.com",
      "password": "password123",
      "name": "John Doe"
  }
  ```
- **Login User:** `POST /api/users/login`
  ```json
  {
      "email": "test@example.com",
      "password": "password123"
  }
  ```

### **Patient APIs**
- **Add Patient:** `POST /api/patients`
  ```json
  {
      "name": "Alice",
      "age": 30,
      "user": { "id": 1 }
  }
  ```
- **View Patients:** `GET /api/patients/user/{userId}`

### **Heart Rate APIs**
- **Add Heart Rate Record:** `POST /api/heartrate`
  ```json
  {
      "bpm": 72,
      "timestamp": "2025-02-13T10:00:00",
      "patient": { "id": 1 }
  }
  ```
- **View Heart Rate Records:** `GET /api/heartrate/patient/{patientId}`

---

## ⚡ Assumptions & Decisions
1. **User authentication** is simple (email & password stored in DB, no JWT yet).
2. **Patients must be linked** to an existing user (user ID required when adding patients).
3. **Database auto-creates tables** using `spring.jpa.hibernate.ddl-auto=update`.

---
