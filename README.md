# Matching Pair Game

A simple desktop memory matching game built in Java Swing. Players flip over two cards at a time, trying to find matching pairs. The game tracks moves and shuffles the cards after each restart.

---

## 📥 Download & Run (No Java Setup Required)

We've provided platform-specific, self-contained packages that bundle a minimal Java Runtime Environment (JRE). No separate Java installation is needed.

1. Visit the [Releases page](https://github.com/Kimmy7070/Mathcing-pair-ap/releases).
2. Download the ZIP for your operating system:
   - **Windows**: `MatchingPairGame-windows.zip`
   - **macOS**: `MatchingPairGame-macos.zip`
   - **Linux**: `MatchingPairGame-linux.zip`
3. Unzip the archive.
4. **Run the executable** in the extracted folder:
   - **Windows**: Double-click `MatchingPairGame.exe`
   - **macOS/Linux**: Open a terminal, `cd` into the folder, then:
     ```sh
     ./MatchingPairGame
     ```
5. Enjoy the game!

---

## 🛠 Build & Run from Source (Java Required)

If you prefer to compile the game yourself, you’ll need **Java SE 8** or higher on your system.

1. **Clone the repository**:

   ```sh
   git clone https://github.com/Kimmy7070/Mathcing-pair-ap.git
   cd Mathcing-pair-ap/MatchingPairGame
   ```

2. **Compile** all `.java` files:

   ```sh
   mkdir -p out
   javac -d out src/main/java/matchingpairsgame/*.java
   ```

3. **Package** into an executable JAR:

   ```sh
   jar cfe MatchingPairGame.jar matchingpairsgame.Board -C out .
   ```

4. **Run** the JAR:

   ```sh
   java -jar MatchingPairGame.jar
   ```

> Tip: If you don’t have Java installed, see the [Download & Run](#download--run-no-java-setup-required) section above for a bundled package.

---

## 🧩 How It Works

- **Board**: Builds the UI grid and control buttons.
- **Controller**: Tracks flips and matches, enforces game rules.
- **Card**: Represents each tile, handles face-up/down/excluded states.
- **Counter**: Displays the number of moves (face-up flips).
- **ShuffleEvent / MatchedEvent**: Custom events to broadcast new layouts and match results.

Dive into `src/main/java/matchingpairsgame/` to explore each class and see how listeners coordinate game flow.

---

## 🤝 Contributing

1. Fork the repo.
2. Create a feature branch: `git checkout -b my-feature`.
3. Commit your changes: `git commit -m "Add awesome feature"`.
4. Push to your branch and open a Pull Request.

---

## 📄 License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.

---

## 🐛 Known Issues & Bugs

- **Counting duplicate flips**: Clicking the same card twice in a row still increments the move counter, even though it doesn’t affect matching logic.
- **No victory notification**: When all pairs are matched, the game ends silently without a dialog or visual indication of completion.
- **Fixed board size**: The game is hardcoded to 4 pairs (8 cards); adjusting for more pairs requires code changes.

## 🚀 Future Improvements

- Suppress move increments when the same card is clicked twice.
- Add a pop-up dialog or animated screen when the player wins.
- Parameterize board size (e.g., allow 4×4, 6×6) via config or UI.
- Replace numeric card faces with images for a richer UX.
- Add sound effects on flips and matches.
- Include a timer and high-score tracking.

---

*Generated on April 27, 2025*

---

## 📚 README in Italiano

Di seguito trovi la versione italiana del file README, da salvare come `README.it.md` nella root del repository.

```markdown
# Gioco del Memory (Matching Pair Game)

Un semplice gioco desktop di memoria realizzato in Java Swing. Il giocatore scopre due carte alla volta cercando di trovare coppie uguali. Il gioco conta le mosse e mescola le carte ad ogni riavvio.

---

## 📥 Download e Avvio (Java non richiesto)

Abbiamo fornito pacchetti auto-contenuti con una JRE minima inclusa. Non serve installare Java separatamente.

1. Vai alla [pagina dei Rilasci](https://github.com/Kimmy7070/Mathcing-pair-ap/releases).
2. Scarica lo ZIP per il tuo sistema operativo:
   - **Windows**: `MatchingPairGame-windows.zip`
   - **macOS**: `MatchingPairGame-macos.zip`
   - **Linux**: `MatchingPairGame-linux.zip`
3. Estrai l’archivio.
4. **Avvia l’eseguibile** nella cartella estratta:
   - **Windows**: doppio clic su `MatchingPairGame.exe`
   - **macOS/Linux**: apri un terminale, spostati nella cartella e digita:
     ```sh
     ./MatchingPairGame
     ```
5. Buon divertimento!

---

## 🛠 Compilazione e Avvio da Sorgente (Java richiesto)

Se preferisci compilare il gioco da te, serve Java SE 8 o superiore.

1. **Clona il repository**:
   ```sh
   git clone https://github.com/Kimmy7070/Mathcing-pair-ap.git
   cd Mathcing-pair-ap/MatchingPairGame
   ```
2. **Compila** tutti i file `.java`:
   ```sh
   mkdir -p out
   javac -d out src/main/java/matchingpairsgame/*.java
   ```
3. **Crea** il JAR eseguibile:
   ```sh
   jar cfe MatchingPairGame.jar matchingpairsgame.Board -C out .
   ```
4. **Avvia** il JAR:
   ```sh
   java -jar MatchingPairGame.jar
   ```

> Suggerimento: se non hai Java installato, segui la sezione Download e Avvio nelle versioni precompilate.

---

## ➕ Aggiungere un README in un'altra lingua

Per supportare più lingue nel tuo repository GitHub, segui questi passi:

1. **Crea un nuovo file** nella root del progetto, ad esempio `README.it.md` per l’italiano o `README.es.md` per lo spagnolo.
2. **Traduci** il contenuto del `README.md` nel nuovo file, mantenendo la stessa struttura e i link relativi.
3. (Opzionale) **Aggiungi un link** nel README principale per rimandare alle versioni tradotte:
   ```markdown
   ---
   📖 Available in: [English](README.md) · [Italiano](README.it.md)
   ```
4. **Commit** e **push** i nuovi file:
   ```sh
   git add README.it.md README.md
   git commit -m "Add Italian README"
   git push origin main
   ```

GitHub rileva automaticamente `README.*.md` nella root e li mostra nel pannello dei file. Puoi inoltre configurare il branch predefinito e le lingue supportate.
```

