# Pet Med Tracking Android App

A professional Android application designed for veterinarians to track pet medications and generate client handouts.

## 🏗 Architecture & Design

### App Architecture
The project follows **Clean Architecture** principles and the **MVVM (Model-View-ViewModel)** pattern, organized into three main layers:

*   **Presentation Layer**: Built with **Jetpack Compose**. ViewModels interact with Use Cases and maintain state using `StateFlow`.
*   **Domain Layer**: Contains the core business logic (Use Cases) and platform-independent Models.
*   **Data Layer**: Responsible for data persistence and retrieval. It combines a **Room** database for local storage and a localized **PetMedApiService** that simulates backend interactions.

### Engineering Decisions & Tradeoffs
1.  **Simplified Data Stack**: Intentionally removed **Retrofit** to simplify the project's infrastructure. This decision was made to focus development on core features like PDF generation and medication logic without the overhead of managing a complex networking stack in a localized demo environment.
2.  **In-Memory API Simulation**: The `PetMedApiService` provides hardcoded initial data and maintains an in-memory list for new records. This allows for a functional "create" flow during an app session without requiring a live backend.
3.  **PDF Generation**: Used a custom `PdfGenerator` to create professional handouts. This was prioritized over simple text sharing to ensure information is clearly presented to pet owners.
4.  **Hardcoded Strings**: Decided to move forward with hardcoded strings inside Compose Kotlin code to gain time.
5.  **Separated ViewModels and UiStates**: Created a single View Model and a single UiState to gain time and have a faster delivery.

### Design Considerations
*   **User Experience**: The UI is designed to be intuitive for veterinarians, with clear forms for adding medications and an easy-to-use "Share" feature for generating handouts.
*   **Data Validation**: Mandatory fields (Start Date, Duration) are enforced in the UI to ensure accurate medical records.

## 🛠 Engineering Journey & Challenges

### Problems Encountered
*   **Mock Server Requests**: The initial idea was to mock server-side responses using Mock Web Server library from OkHttp3. During development, I changed the approach to gain time as it would be much more complex to implement it rather than using simple hardcoded kotlin code.
*   **Field Validation**: At beginning, I forgot to add some fields into `AddMedicationDialog`, which were silently causing problems to add medications into Room DB, as those fields are required.
*   **Missing Hilt Annotation**: There was a missing `@Provides` annotation that was causing persistent compile errors.

### Technical Debt & Tech Improvements
*   **Networking Layer**: While currently simplified, re-introducing a robust networking stack (Retrofit/GraphQL) would be a logical next step if the app scales to use a real backend, including HTTP error/success handling.
*   **Testing Coverage**: Strong unit test coverage for the Repository and Use Cases. Future improvements include adding **Instrumented Tests** for UI verification.

## 🤖 AI usage

*   **For Planning**: ChatGPT 5.3 was used to plan the development considering the needs versus delivery date, and point possible risks.
*   **For Development**: AntiGravity was used to create the boilerplate code in order to save development time (Room and Retrofit, basically). Also, the Share feature (PDF Generator class) and some part of UI Compose screens.  
*   **For Delivery**: README skeleton creation with the necessary sections and proposals of items to be added, also to gain delivery time.

## 🚀 Getting Started

### Prerequisites
*   **JDK**: Version 21
*   **Android Studio**: Ladybug or later
*   **Android SDK**: API 36 (Ladybug)

### Reproducible Build Instructions
To build the project and run tests from the command line:

1.  **Clone the repository**.
2.  **Build the Debug APK**:
    ```bash
    ./gradlew assembleDebug
    ```
3.  **Run Unit Tests**:
    ```bash
    ./gradlew test
    ```

## 📈 Next Steps

### Product Features
- [ ] **Camera/Voice Input**: Implement Voice dictation and/or Photo recognition for physical prescriptions and digitalization of already prescribed medications.
- [ ] **Medication Reminders**: Push notifications to alert pet owners when to administer doses.
- [ ] **Comprehensive Pet Portfolio**: Add support medical history and photos.
- [ ] **Multi-Vet Sync**: Allow multiple veterinarians to access shared pet records using a login system.

### Engineering Tasks
- [ ] **Offline-First Sync**: Implement a repository pattern that syncs local Room data with a remote API using a RemoteMediator.
- [ ] **Tech Improvements**: Continue doing tech debts and tech improvements as a long-term commitment to always keep the app up and running without production errors.
- [ ] **CI/CD Integration**: Set up an automated building pipeline and run unit testing on every pull request.
