# Pedalean - Android Native App

A native Android application built 100% in Kotlin for managing bicycle rentals and urban mobility. This project was engineered to showcase advanced Android development practices, emphasizing modularity, scalability, and maintainability.

## 🚀 Key Features

*   **Secure Authentication:** Implementation of a robust login system utilizing token-based authentication (`TokenResponse`, `LoginRequest`).
*   **Interactive Mapping:** Native map integration for locating available bicycles and tracking rental stations.
*   **Bicycle Renting System:** Complete flow for users to view bicycle details, check availability, and manage their rental history.
*   **Offline Support:** Local caching mechanism ensuring the app remains functional even with poor network conditions.
*   **Modern UI:** Built using reactive UI principles with separated `Screens` and `Activities` (e.g., `BikeDetailScreen`, `ProfileScreen`).

## 🏗️ Architecture & Tech Stack

The application strictly follows **Clean Architecture** principles and the **MVVM (Model-View-ViewModel)** design pattern. The codebase is divided into three highly decoupled layers:

### 1. Presentation Layer (`presentation`)
*   Manages the UI and handles user interactions.
*   State management is handled via ViewModels (`MainViewModel`, `LoginViewModel`, `BikeDetailViewModel`).
*   Organized by feature modules: `login`, `main`, `detail`, `profile`, and `splash`.

### 2. Domain Layer (`domain`)
*   Contains the core business logic and encapsulates the application's behavior.
*   Includes pure Kotlin data models (`Bike`, `Rent`, `User`).
*   Defines repository interfaces (`IBikeRepository`, `IRentRepository`, `IUserRepository`) to apply the Dependency Inversion Principle.

### 3. Data Layer (`data`)
*   Implements the repository interfaces and serves as the single source of truth.
*   **Remote Datasource:** Integrates a REST API using Retrofit (`RetrofitClient`, `APIService`) to fetch real-time data.
*   **Local Datasource:** Utilizes Room Database for data persistence (`Pedalean2AppDatabase`) with specific DAOs (`BikeDao`, `RentDao`, `UserDao`) and Entities.

## 🛠️ Technologies & Libraries

*   **Language:** Kotlin
*   **Architecture:** Clean Architecture + MVVM
*   **Networking:** Retrofit
*   **Local Database:** Room (SQLite)
*   **UI Components:** Reactive components & Custom Themes (`Color.kt`, `Theme.kt`, `Type.kt`)
