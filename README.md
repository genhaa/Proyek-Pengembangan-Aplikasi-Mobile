# 📱 ARCANE
Asisten perpustakaan digital berbasis KMP yang mengintegrasikan Google Books API dan AI Gemini untuk manajemen literatur cerdas serta analisis riset otomatis.

![Build Status](https://github.com/genhaa/Proyek-Pengembangan-Aplikasi-Mobile.git)
![Kotlin Version](https://img.shields.io/badge/Kotlin-2.0.0-blue?logo=kotlin)
![Compose Version](https://img.shields.io/badge/Compose-Multiplatform-orange)

---

## 👥 Informasi Tim
Proyek ini dikembangkan oleh **B Square** sebagai bagian dari tugas mata kuliah Pengembangan Aplikasi Mobile.

| Nama | NIM | GitHub | Role |
| :--- | :--- | :--- | :--- |
| [Memory Simanjuntak] | [123140095] | https://github.com/13-095-memory | Lead Developer / UI Design |
| [Grace Exauditha Nababan] | [123140115] | https://github.com/genhaa | Backend & Data |


---

## 🚀 Fitur Aplikasi (Sprint 1 Planning & Status)

### Fitur Utama (Minimum Requirements):
- [x] **Modern UI/UX:** Lebih dari 5 layar (Home, Bookshelf, Book Detail, Explore, Settings, AIAssistant) menggunakan Material Design 3 bertema ungu/indigo premium.
- [x] **Data Management:** Integrasi REST API (Google Books API) menggunakan Ktor dan Database Lokal SQLDelight.
- [x] **State Management:** Implementasi StateFlow untuk reaktivitas UI di seluruh halaman.
- [x] **Navigation:** Navigasi antar layar yang aman dengan argument menggunakan Compose Navigation.

### Fitur Tambahan (Bonus):
- [x] Support iOS Platform (Kotlin Multiplatform).
- [x] Dark Mode & Light Mode Support.
- [x] **Integrasi AI:** Google Gemini AI untuk analisis riset, literatur ilmiah, sintesis otomatis, dan asisten riset cerdas.
- [x] **Penyimpanan Gambar Lokal:** Menyimpan foto profil secara lokal pada sandbox OS menggunakan Okio FileSystem untuk efisiensi memori.

---

## 🏗️ Arsitektur & Tech Stack
Aplikasi ini dibangun menggunakan **Clean Architecture** dan pola **MVVM (Model-View-ViewModel)** untuk memastikan kode yang mudah diuji dan dipelihara.

- **Multiplatform:** Kotlin Multiplatform (KMP)
- **UI Framework:** Compose Multiplatform
- **Dependency Injection:** Koin (Target Bonus +10%)
- **Networking:** Ktor
- **Local DB:** SQLDelight (jika pakai DB)
- **Testing:** Kotlin Test & Mockative

---

## 🛠️ Project Setup, CI/CD, & Testing

Proyek ini sudah dilengkapi dengan **GitHub Actions** untuk Continuous Integration (CI). Setiap push atau pull request otomatis menjalankan kompilasi build dan seluruh unit test.

### 🧪 Petunjuk Pengujian (Test Instructions)

1. **Menjalankan Unit Test (Local JVM):**
   Untuk menjalankan seluruh test untuk ViewModel dan business logic secara lokal:
   ```bash
   ./gradlew testDebugUnitTest
   ```
2. **Menjalankan UI Test (Instrumented Test):**
   Untuk menjalankan UI test di emulator Android atau perangkat fisik:
   ```bash
   ./gradlew connectedAndroidTest
   ```
3. **Menjalankan Laporan Code Coverage (Kover):**
   Gunakan plugin Kover untuk melacak cakupan kode pengujian:
   ```bash
   ./gradlew koverHtmlReport
   ```
   Laporan cakupan kode dalam format HTML akan tersedia di folder `composeApp/build/reports/kover/html/index.html`.

---

## 📈 Rencana Pengerjaan (Project Plan)
- **Sprint 1 (Selesai):** Project Setup, Clean Architecture, & UI Mockup.
- **Sprint 2 (Selesai):** Core Features, Local Storage SQLDelight, & Google Books REST API Integration.
- **Sprint 3 (Selesai):** Gemini AI Integration (Research Assistant), Settings Page, & Dark Mode.
- **Sprint 4 (Selesai):** Testing (Unit Testing & UI Testing 50%+ coverage), Bug Fixing (Local storage path optimization), & UI Polishing.
- **Sprint 5 (Selesai):** Release APK, Final Demo Preparation, & Documentation.
