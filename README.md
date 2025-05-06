# Kotlin Quiz App

A modern Android quiz application built with Kotlin and Android Jetpack components, designed to provide an interactive learning experience through customizable quizzes.

## Features

- Create and manage custom quizzes
- Take quizzes and track progress
- View detailed statistics and performance metrics
- Customizable quiz settings
- Dark mode support
- Offline functionality

## Architecture & Technical Stack

### Core Technologies
- **Language:** Kotlin
- **Minimum SDK:** API 21 (Android 5.0)
- **Target SDK:** API 34

### Architecture Components
- MVVM (Model-View-ViewModel) architecture
- Android Jetpack Components
- Hilt for dependency injection
- Room Database for local storage
- StateFlow for reactive state management
- Navigation Component for app navigation

### Libraries & Frameworks
- AndroidX Libraries
- Material Design Components
- ConstraintLayout
- Room Persistence Library
- Hilt
- JUnit & Espresso for testing

## Project Setup

### Prerequisites
- Android Studio Arctic Fox or newer
- JDK 17
- Android SDK with minimum API 21

### Building the Project
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/kotlin-quiz.git
   ```

2. Open the project in Android Studio

3. Sync project with Gradle files

4. Build the project:
   ```bash
   ./gradlew build
   ```

### Running Tests
- Run unit tests:
  ```bash
  ./gradlew test
  ```
- Run instrumented tests:
  ```bash
  ./gradlew connectedAndroidTest
  ```

## Project Structure

### Key Components

- `app/` - Main application module
  - `src/main/`
    - `java/com/example/kotlinquiz/`
      - `data/` - Data layer (Room DB, Models, Repositories)
      - `di/` - Dependency injection modules
      - `ui/` - UI components and ViewModels
      - `utils/` - Utility classes

### Database Schema

The app uses Room database with the following main entities:
- Quiz
- Question
- Answer
- Category
- QuizAttempt

## Continuous Integration

The project uses GitHub Actions for CI/CD, which automatically:
- Builds the project
- Runs unit tests
- Runs instrumentation tests
- Generates debug APK
- Performs static code analysis

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.
# Kotlin Quiz App

A modern Android quiz application built with Kotlin and Android Jetpack components, designed to provide an interactive learning experience through customizable quizzes.

## Features

- Create and manage custom quizzes
- Take quizzes and track progress
- View detailed statistics and performance metrics
- Customizable quiz settings
- Dark mode support
- Offline functionality

## Architecture & Technical Stack

### Core Technologies
- **Language:** Kotlin
- **Minimum SDK:** API 21 (Android 5.0)
- **Target SDK:** API 34

### Architecture Components
- MVVM (Model-View-ViewModel) architecture
- Android Jetpack Components
- Hilt for dependency injection
- Room Database for local storage
- StateFlow for reactive state management
- Navigation Component for app navigation

### Libraries & Frameworks
- AndroidX Libraries
- Material Design Components
- ConstraintLayout
- Room Persistence Library
- Hilt
- JUnit & Espresso for testing

## Project Setup

### Prerequisites
- Android Studio Arctic Fox or newer
- JDK 17
- Android SDK with minimum API 21

### Building the Project
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/kotlin-quiz.git
   ```

2. Open the project in Android Studio

3. Sync project with Gradle files

4. Build the project:
   ```bash
   ./gradlew build
   ```

### Running Tests
- Run unit tests:
  ```bash
  ./gradlew test
  ```
- Run instrumented tests:
  ```bash
  ./gradlew connectedAndroidTest
  ```

## Project Structure

### Key Components

- `app/` - Main application module
  - `src/main/`
    - `java/com/example/kotlinquiz/`
      - `data/` - Data layer (Room DB, Models, Repositories)
      - `di/` - Dependency injection modules
      - `ui/` - UI components and ViewModels
      - `utils/` - Utility classes

### Database Schema

The app uses Room database with the following main entities:
- Quiz
- Question
- Answer
- Category
- QuizAttempt

## Continuous Integration

The project uses GitHub Actions for CI/CD, which automatically:
- Builds the project
- Runs unit tests
- Runs instrumentation tests
- Generates debug APK
- Performs static code analysis

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.
