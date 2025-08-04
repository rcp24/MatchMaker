# MatchMate

##### App which helps in finding you your soulmate

## App Previews

<img width="344" height="760" alt="Screenshot 2025-08-05 at 12 40 01 AM" src="https://github.com/user-attachments/assets/8f981be4-7fa5-473b-a000-525916075529" />
<img width="344" height="760" alt="Screenshot 2025-08-05 at 12 42 49 AM" src="https://github.com/user-attachments/assets/4b9673bf-686c-45ed-8c6c-5a1220831ac0" />
<img width="344" height="760" alt="Screenshot 2025-08-05 at 12 40 49 AM" src="https://github.com/user-attachments/assets/b9a337ac-5ef7-4c52-8413-139c5e06b68e" />

## Setup Process

1. **Download the ZIP**  
   - Go to the [GitHub repository](<repository_link_here>).  
   - Click on the **"Code"** button and select **"Download ZIP"**.  
   - Extract the downloaded ZIP file to your desired location.  

2. **Import into Android Studio**  
   - Open **Android Studio**.  
   - Click on **"Open"**.  
   - Navigate to the extracted folder and select it.  
   - Wait for the project to sync and build.  

3. **Run the App**  
   - Connect an emulator or physical device.
   - Run the project from 'main' branch
   - Click on the **Run** button ▶️ to launch the app.  

## Project Architecture

MatchMate follows **Clean Architecture** with the **MVVM** design pattern. The project is structured into three main layers:  

1. **Data Layer**  
   - Handles data operations from **Network (Retrofit)** and **Local Database (Room)**.  

2. **Domain Layer**  
   - Contains repository implementations to abstract data sources.  

3. **UI Layer**  
   - Built with **Jetpack Compose** and consists of **Composables** and **ViewModel** to manage UI state.  

## Tech Stack

- **Networking** → Retrofit  
- **Local Storage** → Room  
- **Dependency Injection** → Hilt  
- **Async Operations** → Kotlin Coroutines & Flow  
- **UI Framework** → Jetpack Compose  
- **Image Loading** → Coil  
- **Lifecycle Management** → ViewModel 
