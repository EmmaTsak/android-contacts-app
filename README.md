Contacts App (Android)

A modern Android contacts management application built using Kotlin, Jetpack Compose, and MVVM architecture, designed for efficient data handling and a clean user experience.

Features

Add, edit, and delete contacts
Display contacts in a dynamic list
MVVM architecture with ViewModel & Repository pattern
Local data persistence using Room Database
Reactive UI updates with state management
Navigation between screens using Compose Navigation

Tech Stack

Language: Kotlin
UI: Jetpack Compose
Architecture: MVVM
Database: Room
State Management: ViewModel
Navigation: Compose Navigation

Architecture Overview

The app follows clean architecture principles:

UI Layer: Compose screens (ContactListScreen, ContactFormScreen)
ViewModel: Handles UI state and business logic
Repository: Abstracts data access
Data Layer: Room Database (DAO + Entity)

Project Structure:
data/           → Room entities, DAO, database
repository/     → Data abstraction layer
viewmodel/      → UI state & logic
ui/             → Compose screens & components
navigation/     → App navigation graph
