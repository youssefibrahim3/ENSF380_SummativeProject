# Disaster Relief Management System

A command-line Java application for managing disaster relief operations, built as part of ENSF 380 at the University of Calgary. The system allows relief workers to manage victims, supplies, locations, medical records, family relationships, and inquiries through a persistent PostgreSQL database.

## Features

- **Victim Management** — Add, modify, soft-delete, and hard-delete disaster victim records with full audit trail
- **Skill Registry** — Track and search victim skills across three categories: medical, language, and trade
- **Cultural Requirements** — Load and assign culturally-sensitive accommodation options from a configurable file
- **Supply Tracking** — Manage perishable and non-perishable supplies with expiry date enforcement
- **Medical Records** — Log and view treatment records per victim
- **Family Relationships** — Record and query relationships between victims
- **Inquiry Logging** — Track inquiries made by individuals searching for missing persons
- **Action Logging** — Every data change is logged to `action_log.txt` with a timestamp
- **Error Logging** — Unrecoverable errors are logged to `errorlog.txt` for diagnostics

## Tech Stack

- **Language:** Java 21
- **Database:** PostgreSQL (via JDBC)
- **Testing:** JUnit 4, Hamcrest
- **Design Patterns:** Singleton (ActionLogger, DatabaseManager), DAO pattern, MVC separation

## Setup

### Database Setup
Run the provided SQL file to create the database and seed it with sample data:
```bash
psql -U postgres -f src/main/java/edu/ucalgary/oop/project.sql
```

### Build and Run
```bash
# Create output directory
mkdir out

# Compile
javac -cp "lib/*" -d out src/main/java/edu/ucalgary/oop/*.java

# Run
java -cp "out;lib/*" edu.ucalgary.oop.Main
```
On Mac/Linux use `:` instead of `;` in the classpath.

### Run Tests
```bash
java -cp "out;lib/*" org.junit.runner.JUnitCore edu.ucalgary.oop.DisasterVictimTest edu.ucalgary.oop.SupplyTest edu.ucalgary.oop.LocationTest edu.ucalgary.oop.MedicalRecordTest edu.ucalgary.oop.FamilyRelationTest edu.ucalgary.oop.ReliefServiceTest edu.ucalgary.oop.InquirerTest edu.ucalgary.oop.MedicalSkillTest edu.ucalgary.oop.LanguageSkillTest edu.ucalgary.oop.TradeSkillTest edu.ucalgary.oop.CulturalOptionsTest
```

## Design Highlights

- **DAO Pattern** — Database access is abstracted behind a `GenericDAO<T, ID>` interface, with concrete implementations for each entity. This decouples business logic from SQL and makes the codebase easier to test and maintain.
- **Singleton Logger** — `ActionLogger` uses the Singleton pattern to ensure all user-driven changes are written to a single log file in the correct order.
- **Soft Delete** — Victim records support soft deletion, keeping data intact in the database while hiding it from the UI — a common pattern in production systems where data retention matters.
- **Perishable Supply Enforcement** — The system prevents expired supplies from being allocated to victims and warns workers of expired inventory on entry to the supply menu.
- **Configurable Cultural Options** — Accommodation types are loaded at startup from a serialized file, making the system adaptable to different deployment contexts without recompilation.

## License
GPL v3 — base project structure provided by Ann Barcomb and Khawla Shnaikat, University of Calgary, 2024–2025. Individual project extensions by Youssef Ibrahim, 2026.
