# First Java Ticket Reservation System

A throwback to my very first Java project! This is a **ticket reservation system** built without a database, using **text files** to store and retrieve reservation data.

## Project Overview

* Users can **reserve tickets**, and the system stores the data in text files.
* Employees can **view all reserved tickets** by reading directly from the files.
* **Seat & couch tracking** automatically moves to the next couch when all seats are reserved.
* **Reset functionality** clears all reservations by resetting the file.
* **Restore data functionality** can automatically restore reservations when the app restarts or manually restore data from another file if needed.

This project demonstrates **basic Java programming, file handling, and system logic** without relying on a database.

---

## Installation & Usage

1. **Clone the repository**

```bash
git clone https://github.com/moehabx/Train-Ticket-Reservation-System
```

2. **Open in Eclipse**

   * File → Import → Existing Java Project → Select the project folder.

3. **Run the project**

   * Open `Main.java` → Right-click → Run As → Java Application.

4. **Reservation workflow**

   * Reserve tickets, view reserved tickets, reset, or restore data as needed.

---

## Notes

* You may have to change your java version I remember having an issue with it.
* All reservation data is stored in text files located in the project directory.
* Designed as a learning project to practice **Java logic, file I/O, and problem-solving**.

--

