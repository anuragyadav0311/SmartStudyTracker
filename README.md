# 📚 Smart Study Tracker v2.0

> *Track · Analyze · Achieve*

A feature-rich Java command-line application that helps students track their study sessions, analyze habits, set goals, and stay motivated.

---

## ✨ Features

### Core Features
- **➕ Add Study Sessions** — Log subject, duration, and date (auto-fills today's date)
- **📖 View All Sessions** — Color-coded table with session details and totals
- **🗑️ Delete Sessions** — Remove sessions interactively by number
- **🔍 Search & Filter** — Find sessions by subject (fuzzy match) or filter by date

### Analytics & Goals
- **📊 Study Statistics Dashboard** — Full analytics with:
  - Total study time and session count
  - Per-subject breakdown with visual progress bars
  - Study streak tracking (current + longest)
  - Top study days leaderboard
- **🎯 Daily Study Goals** — Set targets, track progress with visual bars, earn celebrations

### Experience
- **💡 Motivational Quotes** — 20 curated quotes displayed on startup and on-demand
- **🎨 Rich Terminal UI** — ANSI color-coded menus, tables, and output
- **📅 Smart Date Input** — Press Enter to auto-fill today's date
- **✅ Input Validation** — Robust validation for all user inputs

---

## 🛠️ Technologies Used

- **Java** (JDK 11+)
- **File I/O** — CSV-based persistent storage
- **Java Streams API** — For analytics and filtering
- **ANSI Escape Codes** — For colorful terminal output
- **OOP Principles** — Clean class design with separation of concerns

---

## 📁 Project Structure

```
SmartStudyTracker/
├── src/
│   ├── Main.java              # Entry point — CLI menu & feature integration
│   ├── StudySession.java      # Data model for study sessions
│   ├── TrackerService.java    # CRUD operations with file persistence
│   ├── StudyStatistics.java   # Analytics dashboard & data visualization
│   ├── StudyGoals.java        # Daily goal tracking & progress display
│   ├── MotivationEngine.java  # Random motivational quotes
│   └── ConsoleColors.java     # ANSI color utility constants
├── data/
│   └── sessions.txt           # Study session data (CSV format)
├── report/
│   └── Smart_Study_Tracker_Report.pdf
├── .gitignore
├── LICENSE
└── README.md
```

---

## 🚀 How to Run

### Prerequisites
- Java JDK 11 or higher

### Steps

1. **Clone the repository:**
   ```bash
   git clone https://github.com/anuragyadav0311/SmartStudyTracker.git
   ```

2. **Navigate to source folder:**
   ```bash
   cd SmartStudyTracker/src
   ```

3. **Compile all source files:**
   ```bash
   javac *.java
   ```

4. **Run the application:**
   ```bash
   java Main
   ```

---

## 📸 Screenshots

### Main Menu
```
   ╔═══════════════════════════════════════════════════════╗
   ║         📚 Smart Study Tracker v2.0 📚                ║
   ║      ─── Track · Analyze · Achieve ───                ║
   ╚═══════════════════════════════════════════════════════╝

   ┌───────────────────────────────────────┐
   │       📋 MAIN MENU                     │
   ├───────────────────────────────────────┤
   │  1. ➕ Add Study Session                │
   │  2. 📖 View All Sessions                │
   │  3. 🗑️  Delete a Session                │
   │  4. 🔍 Search & Filter Sessions         │
   │  5. 📊 Study Statistics                 │
   │  6. 🎯 Study Goals                      │
   │  7. 💡 Get Motivated                    │
   │  8. 🚪 Exit                             │
   └───────────────────────────────────────┘
```

---

## 📖 Example Usage

1. **Add a session:** Select option 1, enter subject "Mathematics", duration "45", press Enter for today's date
2. **View stats:** Select option 5 to see your analytics dashboard with progress bars and streaks
3. **Set a goal:** Select option 6 → option 2, enter "120" for a 2-hour daily target
4. **Search:** Select option 4 → option 1, type "Math" to find all math-related sessions

---

## 🎓 Learning Outcomes

- Implemented file handling with CSV parsing in Java
- Applied OOP principles with clean separation of concerns
- Built a rich CLI interface with ANSI escape codes
- Used Java Streams API for data processing and analytics
- Implemented streak tracking with date boundary handling
- Created a modular architecture for easy feature extension

---

## 🔮 Future Improvements

- [ ] Export sessions to PDF/HTML reports
- [ ] Weekly/monthly analytics views
- [ ] Pomodoro timer integration
- [ ] Multi-user support with profiles
- [ ] GUI version with JavaFX

---

## 📄 License

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.
