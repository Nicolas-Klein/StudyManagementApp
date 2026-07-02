# StudyManagementApp

Eine moderne Android-Anwendung zur Unterstützung des Fokus- und Zeitmanagements im Studium. Die App kombiniert einen intelligenten Pomodoro-Timer mit einem strukturierten Wochen- und Deadline-Planer.

---
## 🚀 Features

### ⏱️ Fokus-Timer (Pomodoro-Technik)
- Automatisch wechselnde Fokus- und Pausenphasen (25 Min. Fokus / 5 Min. Pause).
- Lange Pause (20 Min.) nach vier erfolgreichen Fokusphasen.
- Visuelle und akustische Signalisierung bei Phasenwechseln.

### 📅 Lern- & Wochenplaner
- Strukturierte Wochenübersicht zur Organisation von täglichen Aufgaben.
- **Fokus-Auswahl:** Aufgaben können direkt als aktuelle "Fokus-Aufgabe" für den Pomodoro-Timer ausgewählt und mitgenommen werden.

### 🔔 Deadline-Manager & Hintergrund-Dienst
- Sortierte Übersicht aller anstehenden Deadlines.
- **Zuverlässiges Benachrichtigungssystem:** Ein autonomer `WorkManager`-Hintergrunddienst prüft im 24-Stunden-Intervall anstehende Fristen und warnt den Nutzer rechtzeitig per System-Notification – selbst wenn die App geschlossen ist.