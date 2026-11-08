# Assignment 06: Visitor Pattern — Advanced File System

**Author:** Anuar Yelansar  
**Group:** SE-2522  
**Language/Build System:** Java 17 | Maven  

---

## 📌 Description
This project implements an in-memory hierarchical file system using the **Visitor Behavioral Pattern**. It supports tree structures composed of directories (`DirectoryNode`) and files (`FileNode`), providing operations through polymorphic double dispatch without mutating element classes or using `instanceof` runtime type checks.

### Supported Operations (Visitors):
1. **TotalSizeVisitor:** Calculates total byte size across all nested files.
2. **SearchVisitor:** Filters files by name substring and minimum byte size threshold, returning sorted root-relative paths.
3. **StatisticsVisitor:** Counts total files, directories, and computes the maximum tree depth.

---

## 🚀 How to Run

### 1. Run the Main Application
To run the main program demonstration:
- **In IntelliJ IDEA:** Open `src/main/java/org/example/visitor/Main.java` and click **Run** (Green play button / `Shift + F10`).
- **Via Terminal (Maven Wrapper):**
  ```powershell
  .\mvnw.cmd compile exec:java -Dexec.mainClass="org.example.visitor.Main"