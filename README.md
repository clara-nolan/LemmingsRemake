# Lemmings Recreation in Java

A Java-based recreation of the classic game Lemmings, focusing on implementing core programming concepts and providing a user-friendly interface for level creation and gameplay.

## Core Concepts

### File I/O
- **Usage:** Construction and editing of levels through reading and writing files. 
- **Implementation:** Levels are constructed by reading from files, and users can edit levels, which triggers file writing. This allows for the dynamic setup of the game state based on text file content.

### 2D Arrays
- **Usage:** Management of background images through a `BufferedImage`, representing the image as a 2D array for efficient color and position management.
- **Implementation:** Utilizes `getRGB()` for reading and `setRGB()` for editing pixels, aiding in collision detection and environment manipulation without redundancy.

### Collections
- **Usage:** Dynamic tracking of lemmings using an `ArrayList<BaseLemmings>`.
- **Implementation:** Lemmings are added and removed in real-time. Iterators facilitate safe removal of entities, ensuring encapsulation and order maintenance.

### Inheritance and Subtyping
- **Usage:** Diverse lemming behavior and abilities through a structured hierarchy.
- **Implementation:** An interface `Lemming` and abstract class `BaseLemming` lay the groundwork for specialized lemmings (`WalkerLemming`, `BomberLemming`, `BlockerLemming`), showcasing polymorphism and dynamic dispatch.

## Implementation Details

### Overview

- **Lemming:** An interface defining generic lemming behavior.
  - `BaseLemming`: Common functionality for all lemmings.
    - `WalkerLemming`: Default movement and falling.
    - `BlockerLemming`: Blocking capabilities.
    - `BomberLemming`: Explosion and termination.

- **Level:** Manages level state, including load/save functionality and movement checks.
- **AnimationCycle:** Manages sequences of sprites.
- **CsvReader:** Facilitates level data reading.
- **DrawContext:** Centralizes drawing state.
- **GameCourt:** Manages game state, including lemming interactions and UI elements.
- **RunLemmings:** Main game UI for level selection.
- **LevelEditor:** UI for level property editing.
- **Direction & LemmingType:** ENUMs for movement and lemming categorization.

### Challenges

- **Movement Detection:** Initial pixel-by-pixel checks were replaced by simpler, more reliable methods.
- **Color Handling:** Discovery and adaptation to ARGB format for accurate color representation.
- **Explosion Visualization:** Adjustments to alpha channel settings for visible craters.
- **Animation Padding:** Custom padding to correct sprite spacing and animation alignment.

### Design Evaluation

- **Functionality Separation:** Good, with the potential for improvement in `BlockerLemming`'s external list access.
- **Encapsulation:** Mostly adhered to, with minor exceptions.
- **Refactoring Opportunities:** `WalkerLemming.canMove()` for unified falling behavior.

## External Resources

- **Sprite Sheets:** [Lemmings Forums](https://www.lemmingsforums.net/index.php?topic=1336.0)
- **Tutorials:**
  - [Java Swing Dialogs](https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html)
  - [RGBA and ARGB Formats](https://www.educative.io/answers/what-is-the-difference-between-rgba-and-argb)
