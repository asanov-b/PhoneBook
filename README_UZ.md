> 🇬🇧 English version: [README.md](README.md)

# 📞 PhoneBook — Java Backend Portfolio (Production-Ready)

> Toza arxitektura, xavfsizlik, testlash hamda CI/CD va Docker orqali real ish muhitiga yaqin yetkazib berish jarayonlarini namoyish etuvchi Java backend loyihasi.

## TL;DR

Quyidagi yo‘nalishlarga qaratilgan Java backend portfolio loyihasi:

- Toza va qatlamli backend arxitektura (Controller → Service → Repository)
- Spring Boot asosida ishlab chiqilgan REST API dizayni
- Spring Security va JWT yordamida xavfsiz autentifikatsiya va avtorizatsiya
- Backendga yo‘naltirilgan testlash va kod sifati amaliyotlari
- Docker Compose orqali barqaror va bir xil ish muhiti
- CI/CD orqali avtomatlashtirilgan build va deploy jarayoni
- Serverda shaffof tarzda amalga oshiriladigan avtomatik yangilanishlar

Biznes mantiq ataylab sodda qilingan — asosiy e’tibor **backend dizayni, xavfsizlik va qo‘llab-quvvatlash qulayligi**ga qaratilgan, maqsadim **backend development ko‘nikmalarim**ni ko‘rsatish.

## 🔗 Quick Access

- 🌍 **Live Demo:** http://44.200.65.144
  - **Demo foydalanuvchilar:**
    - Admin: `admin / admin`
    - User: `user / user`

- 📘 **API hujjatlari (Swagger):** http://44.200.65.144:8080/swagger-ui/index.html
- 🐳 **Docker Image:** docker.io/asanovbekzod/phonebook:latest
- 🔄 **CI/CD Pipeline:** GitHub Actions — `main` branch’ga har bir push’da test, build, Docker image chiqarish va serverga avtomatik deploy

## 🎯 Project Purpose

Ushbu loyiha **Java backend portfolio loyihasi** sifatida yaratilgan bo‘lib, asosiy e’tibor:

- backend arxitektura
- xavfsizlik
- testlash
- kod sifati

ga qaratilgan.

Biznes mantiq ataylab sodda saqlangan, bu orqali **real Java backend xizmati qanday loyihalanishi va qo‘llab-quvvatlanishi** aniq ko‘rsatib beriladi.

Ushbu loyiha orqali quyidagi imkoniyatlarim namoyish etilgan:
- toza va qatlamli Java backend arxitektura yaratish
- Spring Boot, Spring Security va Spring Data JPA bilan ishonchli ishlash
- JWT yordamida autentifikatsiya va avtorizatsiya joriy etish
- DTO, validation va markazlashtirilgan exception handling’dan foydalanish
- avtomatlashtirilgan testlar yozish va ishga tushirish
- CI/CD va Docker Compose’ni ishlab chiqish jarayoniga integratsiya qilish

CI/CD avtomatlashtirish va Docker Compose zamonaviy Java backend servislar qanday ishlab chiqilishi, testlanishi va ishga tushirilishini aks ettirish uchun qo‘llanilgan.

## 🚀 Tech Stack

### Backend Core
- **Java 17** — asosiy dasturlash tili
- **Spring Boot** — ilova framework’i
- **Spring Web** — REST API ishlab chiqish
- **Spring Data JPA (Hibernate)** — ma’lumotlar bilan ishlash qatlami
- **PostgreSQL** — relyatsion ma’lumotlar bazasi

### Security
- **Spring Security** — autentifikatsiya va avtorizatsiya
- **JWT (JSON Web Token)** — stateless xavfsizlik mexanizmi
- **BCrypt** — parollarni xeshlash

### Testing & Quality
- **JUnit** — unit testlar
- **Validation API** — so‘rov va ma’lumotlarni tekshirish
- **Global Exception Handling** — yagona va barqaror xatolik javoblari

### Build & Configuration
- **Maven** — dependency va build boshqaruvi
- **Environment Variables** — tashqi konfiguratsiya

### Containerization & Delivery
- **Docker** — ilovani container’lash
- **Docker Compose** — ko‘p containerli muhit (backend + database + frontend)
- **GitHub Actions** — avtomatlashtirilgan test, build va deploy

## 🧠 Architecture

Ilova real Spring Boot loyihalarida keng qo‘llaniladigan **qatlamli Java backend arxitektura** asosida qurilgan.  
Har bir qatlam aniq mas’uliyatga ega bo‘lib, bu **qo‘llab-quvvatlash, testlash va kengaytirishni** osonlashtiradi.

### Architectural Layers

**Controller Layer**
- REST API endpointlarini taqdim etadi
- request va response’larni boshqaradi
- kiruvchi ma’lumotlarni tekshiradi
- biznes mantiqni service qatlamiga uzatadi

**Service Layer**
- asosiy biznes mantiqni o‘z ichiga oladi
- ilova jarayonlarini muvofiqlashtiradi
- controller va repository o‘rtasida asosiy bog‘lovchi hisoblanadi

**Repository Layer**
- Spring Data JPA orqali ma’lumotlar bazasi bilan ishlaydi
- DB access logikasini abstraksiyalaydi
- toza va deklarativ ma’lumotlar olish usullarini ta’minlaydi

### Request Flow

Client Request → Controller → Service → Repository → Database

### Security Architecture

Xavfsizlik **Spring Security** va **JWT** asosida amalga oshirilgan:

1. Foydalanuvchi login orqali autentifikatsiyadan o‘tadi  
2. JWT token yaratiladi va qaytariladi  
3. Keyingi so‘rovlar `Authorization` header orqali yuboriladi  
4. Maxsus security filter tokenni tekshiradi  
5. Faqat ruxsat berilgan endpointlarga kirishga imkon beriladi  

Bu yondashuv **stateless REST API**lar uchun juda mos.

### Supporting Components

- **DTOs** — API va persistence modellarini ajratish  
- **Validation** — ma’lumotlar to‘g‘riligini tekshirish  
- **Global Exception Handling** — barqaror va tushunarli xatolik javoblari  
- **Configuration Classes** — xavfsizlik va ilova sozlamalarini markazlashtirish  

Ushbu arxitektura **real Java backend ilovalari** qanday tuzilishini aks ettiradi.

## 🔄 CI/CD Pipeline

Loyihada backend o‘zgarishlarini sifatli va uzluksiz yetkazib berish uchun **to‘liq avtomatlashtirilgan CI/CD pipeline** mavjud.

Pipeline **GitHub Actions** yordamida amalga oshirilgan.

### Pipeline Trigger

Pipeline quyidagi holatda ishga tushadi:
- `main` branch’ga har bir push’da

Har bir o‘zgarish avtomatik tekshiriladi va tarqatiladi.

### Continuous Integration (CI)

CI bosqichi kod barqarorligini ta’minlaydi:

- source code yuklab olinadi
- Java muhiti sozlanadi
- testlar ishga tushiriladi (`mvn test`)
- ilova build qilinadi (`mvn package`)

Agar biror bosqichda xatolik yuz bersa, pipeline to‘xtaydi.

### Continuous Delivery (CD)

CI muvaffaqiyatli yakunlangach:

- ilovadan Docker image yaratiladi
- image container registry’ga joylanadi

Server tomonda **Watchtower** yangi image’ni aniqlab, container’ni avtomatik yangilaydi.

Bu ilovani **qo‘lda deploy qilmasdan** yangilash imkonini beradi.

### Deployment Flow

1. Kod `main` branch’ga push qilinadi  
2. CI test va build jarayonlarini bajaradi  
3. Yangi Docker image chiqariladi  
4. Watchtower yangi image’ni aniqlaydi  
5. Container yangi versiya bilan qayta ishga tushiriladi  

### Purpose of Automation

CI/CD avtomatlashtirish:
- har bir o‘zgarish testdan o‘tishini
- ishlayotgan ilovaning kod bilan doimiy mos bo‘lishini
- qo‘lda deploy jarayonlarini kamaytirishni

ta’minlaydi va backend arxitektura hamda kod sifatiga e’tiborni saqlab qoladi.

## 🐳 Docker & Containerization

Ilova local development va production muhitlarda **bir xil va barqaror ishlashini** ta’minlash uchun to‘liq container’langan.

Docker ilovani paketlash va ishga tushirish uchun ishlatiladi, Docker Compose esa ilovaning ishlashi uchun zarur bo‘lgan asosiy xizmatlarni boshqaradi.

## Container Setup

Loyiha Docker Compose orqali quyidagi xizmatlarni boshqaradi:

### Backend Application
- Docker container ichida ishlovchi Spring Boot ilova  
- REST API’larni taqdim etadi  
- Ma’lumotlar bazasiga ichki Docker tarmog‘i orqali ulanadi  

### Frontend Application
- Alohida container’da ishlaydi  
- Backend API’lar bilan ishlovchi client vazifasini bajaradi  
- Backend bilan ichki Docker tarmog‘i orqali muloqot qiladi  

### PostgreSQL Database
- Alohida izolyatsiya qilingan container’da ishlaydi  
- Docker volume’lar orqali doimiy ma’lumot saqlashni ta’minlaydi  
- Faqat backend xizmati tomonidan foydalaniladi  

### Avtomatik Yangilanishlar (Server Darajasida)

Container’larni avtomatik yangilash **Docker Compose’dan tashqarida**, server darajasida sozlangan.

Serverda alohida o‘rnatilgan Watchtower xizmati:
- ishlayotgan container’larni kuzatadi  
- yangi Docker image’lar chiqishini aniqlaydi  
- container’larni avtomatik ravishda yangi versiya bilan qayta ishga tushiradi  

Bu yondashuv Docker Compose konfiguratsiyasini soddaroq saqlashga yordam beradi, yangilanish jarayoni esa infratuzilma darajasida mustaqil boshqariladi.

Umuman olganda, ushbu tuzilma **real backendga yo‘naltirilgan ishlab chiqarish muhiti**ni aks ettiradi:  
backend asosiy komponent bo‘lib qoladi, deploy va yangilanish jarayonlari esa ilova tuzilmasini murakkablashtirmagan holda shaffof tarzda amalga oshiriladi.

### Local Development

Docker Compose yordamida butun stack’ni bitta buyruq bilan ishga tushirish mumkin:

```bash
docker compose up --build
```

Bu yondashuv quyidagi afzalliklarni beradi:
- local sozlash jarayoni sodda va tez bo‘ladi
- turli mashinalarda bir xil ish muhiti ta’minlanadi
- qo‘shimcha qo‘lda konfiguratsiya talab qilinmaydi
- production muhitiga yaqin sharoitda ishlash imkonini beradi

### Production Runtime

Production muhitda barcha xizmatlar Docker Compose orqali boshqariladi.  
Ilova doimiy ishlayotgan holatda serverda joylashtirilgan va foydalanuvchi so‘rovlarini qabul qiladi.

CI/CD pipeline orqali yangi backend versiyasi chiqarilganda:
- yangi Docker image yaratiladi
- image registry’ga joylanadi
- serverdagi ishchi container avtomatik yangilanadi

Bu jarayon qo‘lda aralashuvsiz amalga oshiriladi va ishlayotgan xizmat uzluksiz yangilanib boradi.

---

## ⚙️ Configuration

Ilova konfiguratsiyasi **koddan tashqarida** saqlanadi. Bu yondashuv xavfsizlikni oshiradi va turli muhitlarda ishlashni osonlashtiradi.

Asosiy sozlamalar:
- application.properties faylida joylashgan

Runtime vaqtida konfiguratsiya quyidagilar orqali uzatiladi:
- ma’lumotlar bazasi ulanish sozlamalari
- JWT uchun maxfiy kalit
- muhitga bog‘liq port va boshqa parametrlar

CI/CD jarayonida ishlatiladigan barcha maxfiy qiymatlar GitHub Actions’dagi Secrets orqali boshqariladi va repozitoriyga kiritilmaydi.

Loglash markazlashtirilgan holda sozlangan bo‘lib, ilova loglari serverda saqlanadi va monitoring uchun qulay holatda bo‘ladi.

---

## 🧪 Testing & Quality

Loyihada backend barqarorligini ta’minlash uchun avtomatlashtirilgan testlar mavjud.

Testlash jarayoni:
- CI pipeline ichida avtomatik ishga tushadi
- har bir o‘zgarish testdan o‘tkaziladi
- test muvaffaqiyatsiz bo‘lsa, build va deploy to‘xtatiladi

Kod sifati quyidagi tamoyillar asosida saqlanadi:
- controller, service va repository qatlamlarining aniq ajratilishi
- API va ma’lumotlar modeli o‘rtasida DTO’lardan foydalanish
- kiruvchi ma’lumotlar uchun validation
- markazlashtirilgan exception handling

Bu yondashuv kodni o‘qish, testlash va kengaytirishni osonlashtiradi.

---

## 🌍 Live Deployment

Ilova real muhitda ishga tushirilgan va doimiy ravishda ishlamoqda.

Live deploy quyidagilarni namoyish etadi:
- haqiqiy backend xizmati qanday ishlashini
- real foydalanuvchi so‘rovlarini qayta ishlashni
- CI/CD orqali avtomatik yangilanish jarayonini

Frontend backend bilan ichki tarmoq orqali muloqot qiladi, ma’lumotlar esa doimiy saqlash mexanizmi orqali himoyalangan.

---

## 🗺️ Roadmap

Kelajakdagi yaxshilanishlar rejalashtirilgan:

- autentifikatsiya jarayonini kuchaytirish uchun refresh token qo‘llab-quvvatlash
- service va security qatlamlari uchun test qamrovini oshirish
- role-based authorization’ni yanada aniq va moslashuvchan qilish

---

## 🧾 Xulosa

Ushbu loyiha mening **Java backend dasturchi sifatidagi yondashuvimni** ifodalaydi.

Asosiy e’tibor:
- toza arxitektura
- xavfsizlik
- testlash
- real ish muhiti

ga qaratilgan.

Murakkab biznes funksiyalarni ko‘rsatish emas, balki **ishonchli, qo‘llab-quvvatlanadigan va professional backend xizmat** yaratish asosiy maqsad bo‘lgan.

---

## 👤 Author

**Bekzod Asanov**  
Java Backend Developer

GitHub: https://github.com/asanov-b  
LinkedIn: https://www.linkedin.com/in/bekzod-asanov
