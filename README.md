# PhoneBook — Tech-Focused Portfolio Project  
(Spring Boot · JWT · Docker · CI/CD)

> Bu loyiha murakkab biznes ilova (masalan, Ecommerce) emas.  
> Asosiy maqsad — **zamonaviy backend va DevOps texnologiyalarini amalda qo‘llay olishimni** ko‘rsatish.

---

## 🎯 Loyiha maqsadi (Portfolio nuqtai nazaridan)

Ushbu loyiha orqali men quyidagi texnik ko‘nikmalarni real kod orqali namoyish qilaman:

- Spring Boot yordamida **REST API** yaratish
- **Layered architecture**: Controller → Service → Repository
- **Spring Security + JWT** asosida stateless autentifikatsiya
- **Role-based authorization** (USER / ADMIN)
- **DTO + validation** (`jakarta.validation`)
- **Global exception handling**
- **PostgreSQL** bilan ishlash (runtime)
- **H2 in-memory database** (test environment)
- **Swagger / OpenAPI** orqali API hujjatlashtirish
- **JUnit 5 + Mockito** bilan unit testlar
- **Docker multi-stage build**
- **docker-compose** orqali servislarni birga ishga tushirish
- **GitHub Actions CI/CD**: test → build → Docker Hub push
- **Professional logging** (console + rolling file logs)

---

## 🧰 Texnologiyalar

### Backend
- Java 17
- Spring Boot
- Spring Web MVC
- Spring Data JPA (Hibernate)
- Spring Validation
- Spring Security

### Authentication
- JWT (JSON Web Token)

### Database
- PostgreSQL (production / runtime)
- H2 (testing)

### Documentation
- springdoc-openapi (Swagger UI + Bearer Auth)

### Testing
- JUnit 5
- Mockito

### DevOps
- Docker (multi-stage build)
- docker-compose
- GitHub Actions
- Docker Hub

---

## 🗂️ Loyiha strukturasi

Asosiy package: `com.bekzod.phonebook`

config/ → Security, CORS va OpenAPI sozlamalari
controller/ → REST endpointlar
service/ → Biznes logika
repository/ → JPA repositorylar
security/ → JWT, filter va UserDetails
entity/ → JPA entitylar + BaseEntity
mapper/ → DTO ↔ Entity mapping
exceptions/ → Global exception handling
utils/ → Konstantalar va security helperlar

yaml
Copy code

---

## 🔐 Xavfsizlik (JWT)

- Autentifikatsiya `Authorization: Bearer <token>` orqali
- Ochiq endpointlar:
  - `/api/v1/auth/**`
  - `/swagger-ui/**`
  - `/v3/api-docs/**`
- Qolgan barcha endpointlar token talab qiladi
- Role asosida ruxsatlar (`USER`, `ADMIN`)

---

## 📚 API hujjatlari (Swagger)

Ilova ishga tushgach:

http://localhost:8080/swagger-ui/index.html

yaml
Copy code

Swagger ichida JWT token bilan authorize qilib, protected endpointlarni test qilish mumkin.

---

## ⚙️ Konfiguratsiya (Environment variables)

Ilova konfiguratsiyasi environment orqali boshqariladi:

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `JWT_SECRET`

Misol:

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/phonebook_db
export SPRING_DATASOURCE_USERNAME=postgres
export SPRING_DATASOURCE_PASSWORD=0000
export JWT_SECRET=very_strong_secret_key_min_32_chars
🧪 Testlar
Loyihada service layer uchun unit testlar yozilgan:

JUnit 5

Mockito

H2 in-memory database

Testlarni ishga tushirish:

bash
Copy code
mvn test
🐳 Docker
Ilova multi-stage Docker build yordamida image qiladi:

bash
Copy code
docker build -t phonebook:local .
docker run -p 8080:8080 phonebook:local
🧩 docker-compose
docker-compose.yml quyidagilarni ishga tushiradi:

PostgreSQL

Backend application

(Frontend container — portfolio demo uchun)

Ishga tushirish:

bash
Copy code
docker compose up -d
🔁 CI/CD (GitHub Actions)
CI/CD pipeline quyidagicha ishlaydi:

main branch’ga push

Unit testlar (mvn test)

Docker image build

Docker Hub’ga push

Kerakli GitHub Secrets:

DOCKER_HUB_USERNAME

DOCKER_HUB_ACCESS_TOKEN

🪵 Logging
Loglar quyidagicha sozlangan:

Console logging

Rolling file logging

Har kuni yangi log fayl

14 kunlik tarix saqlanadi

🏁 Xulosa
Bu loyiha murakkab biznes funksionallikni emas, balki:
toza arxitektura
xavfsizlik
testlash
avtomatlashtirilgan CI/CD
containerization
kabi real production standartlarini tushunish va amalda qo‘llay olishimni ko‘rsatadi.
