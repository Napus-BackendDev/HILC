
# 🎓 School-Major API

ระบบจัดการข้อมูลโรงเรียนและสาขาวิชา ด้วย Spring Boot และฐานข้อมูล PostgreSQL

## 🚀 How to Set Up and Run the Project

### 🧱 Requirements

- Java 17+
- Docker & Docker Compose
- Maven

### 🐳 Run with Docker Compose

```bash
docker-compose up --build
```

### 🔧 Manual Setup (Without Docker)

1. **Create PostgreSQL Database**

```sql
CREATE DATABASE school_major;
```

2. **Configure `application.properties`**

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/school_major
spring.datasource.username=User
spring.datasource.password=1234
spring.jpa.hibernate.ddl-auto=update
```

3. **Taget File school-major-api**

```bash
cd School-major-api
```

4. **Run the Spring Boot App**

```bash
./mvnw spring-boot:run
```


## 📡 API Documentation

### 🔍 School Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET`  | `/schools` | Get all schools |
| `GET`  | `/schools/{id}` | Get school by ID |
| `GET` | `/schools/search?name=...` | Find school by name |
| `GET` | `/schools/search?acronym=...` | Find school by acronym |
| `POST` | `/schools` | Create a new school |
| `PUT`  | `/schools/{id}` | Update a school |
| `DELETE` | `/schools/{id}` | Delete a school |

### 🎓 Major Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET`  | `/majors` | Get all majors |
| `GET`  | `/majors/{id}` | Get major by ID |
| `GET` | `/majors/search?name=...` | Find major by name|
| `GET` | `/majors/search?acronym=...` | Find major by acronym |
| `POST` | `/majors` | Create a new major |
| `PUT`  | `/majors/{id}` | Update a major |
| `DELETE` | `/majors/{id}` | Delete a major |

---

## 📂 Entities Overview

- `School`: id, name , details, acronym, createdAt, updatedAt
- `Major`: id, school_id, name, details, acronym, createdAt, updatedAt

## 📦 JSON Example Format

### 🏫 School (POST /schools)

```Json
{
  "name": {
    "thai": "สำนักวิชาเทคโนโลยีดิจิทัลประยุกต์",
    "eng": "School of Applied Digital Technology"
  },
  "details": {
    "description": "A center of excellence in technology education and innovation.",
    "phoneNumber": "0 5391 6741 2",
    "email": "adtschool@mfu.ac.th"
  },
  "acronym": "ADT"
}
```

### 🎓 Major (POST /majors)

```Json

{
  "school": {
    "id": "2795b6b7... (ต้องได้มาจากการสร้าง School ก่อน)"
  },
  "details": {
    "description": "วิชาชีพด้านระบบสมองกลฝังตัว",
    "program": "Bachelor of Engineering",
    "fee": 15000
  },
  "name": {
    "thai": "วิศวกรรมคอมพิวเตอร์",
    "eng": "Computer Engineering"
  },
  "acronym": "CE"
}

```
