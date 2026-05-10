# Aplikasi Login & Data Pasien (Tugas Akhir)

Aplikasi Android sederhana berbasis Kotlin yang mendemonstrasikan integrasi API menggunakan Retrofit untuk proses autentikasi (Login) dan pengambilan data (GET) yang dilindungi oleh Bearer Token.

## 🚀 Fitur Utama

- **Autentikasi API**: Login menggunakan email dan password melalui endpoint POST.
- **Manajemen Token**: Menyimpan Bearer Token secara lokal menggunakan `SharedPreferences`.
- **Daftar Pasien**: Mengambil data pasien dari server dan menampilkannya dalam daftar.
- **UI Kustom**: Desain antarmuka menggunakan komponen rounded (sesuai file `bg_button_rounded.xml` dan `bg_edittext_rounded.xml`).
- **RecyclerView**: Menampilkan data pasien secara efisien dengan list yang rapi.

## 🛠️ Tech Stack

- **Bahasa**: [Kotlin](https://kotlinlang.org/)
- **HTTP Client**: [Retrofit 2](https://square.github.io/retrofit/) & OkHttp (Logging Interceptor)
- **Concurrency**: Kotlin Coroutines (lifecycleScope)
- **UI Components**: ConstraintLayout, RecyclerView, CardView, Material Design.
- **JSON Parsing**: GSON Converter.

## 📋 Endpoint yang Digunakan

| Fitur | Method | Endpoint |
| :--- | :--- | :--- |
| **Login** | `POST` | `https://api.pahrul.my.id/api/login` |
| **Data Pasien** | `GET` | `https://api.pahrul.my.id/api/pasien` |

## 🔄 Alur Aplikasi

1. **Halaman Login**: User memasukkan email dan password. Aplikasi mengirim request ke API.
2. **Success Login**: Token yang diterima disimpan ke `SharedPreferences`, lalu user diarahkan ke halaman utama.
3. **Halaman Utama (Home)**: Aplikasi mengambil Nama User dari data login dan memanggil API daftar pasien dengan menyertakan header `Authorization: Bearer {token}`.
4. **Tampilan List**: Data pasien (Nama, Tgl Lahir, Jenis Kelamin, Alamat, dan Telepon) ditampilkan ke dalam `RecyclerView`.
5. **Logout**: Menghapus token dari penyimpanan dan kembali ke layar login.

## 📂 Struktur Folder Penting

- `model/`: Data Class untuk response API (Login & Pasien).
- `network/`: Konfigurasi Retrofit dan definisi interface API.
- `res/layout/`: File XML untuk tampilan (Login, Home, dan Item List).
- `PasienAdapter.kt`: Penghubung data pasien dengan tampilan list.

---
*Dibuat untuk memenuhi Tugas Akhir Mata Kuliah Pemrograman Mobile.*
