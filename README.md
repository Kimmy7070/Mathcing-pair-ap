# Matching Pair Game

A simple desktop “memory” matching game built with Java Swing. Flip two cards at a time to find matching pairs, track your moves, and restart with a fresh shuffle.

---

## 📥 Download & Run (No Java Required)

We bundle a minimal Java Runtime so you don’t need to install Java yourself:

1. Go to the [Releases](https://github.com/Kimmy7070/Mathcing-pair-ap/releases) page.  
2. Download the ZIP for your OS:
   - **Windows**: `MatchingPairGame-windows.zip`
   - **macOS**: `MatchingPairGame-macos.zip`
   - **Linux**: `MatchingPairGame-linux.zip`
3. Unzip the archive.
4. Run the executable:
   - **Windows**: Double-click `MatchingPairGame.exe`
   - **macOS/Linux**:
     ```sh
     cd MatchingPairGame
     ./MatchingPairGame
     ```
5. Enjoy!

---

## 🛠 Build & Run from Source (Java SE 8+ Required)

If you prefer to compile:

```sh
git clone https://github.com/Kimmy7070/Mathcing-pair-ap.git
cd Mathcing-pair-ap/MatchingPairGame

# 1) Compile
mkdir -p out
javac -d out src/main/java/matchingpairsgame/*.java

# 2) Package
jar cfe MatchingPairGame.jar matchingpairsgame.Board -C out .

# 3) Run
java -jar MatchingPairGame.jar
