# COMP3021 PA1: Pipes ASCII Game

## Background

Pipes is a puzzle game, originally developed in 1989 as Pipe Mania. The objective of the game is to place pipes in a 
grid to form a path from the source tile to the sink tile.

We will be making a text version of this game.

## Unicode representation

We use Unicode geometric and box drawing characters to represent the various elements of a map. A sample map when the program loads looks like:
```
████████
█......█
◁......█
█......█
█△.....█
█......█
█......█
████████
```

Since it will be inconvenient for players to construct a map using these characters, an ASCII representation is also 
provided. Therefore, the above map can also be represented using ASCII with the following text:
```
WWWWWWWW
W......W
<......W
W......W
W^.....W
W......W
W......W
WWWWWWWW
```

A detailed explanation of each character can be found in the [Map File Format](#map-file-format) section.

## Game Mechanics

### Basic Controls

- `<col><row>`: Places current pipe to the specific cell
- `exit`: To quit the game
- `undo`: To undo a step

### Map Specifications

- The map should only contain one source tile in any non-edge cell.
- The map should only contain one sink tile in any edge cell.
- The source tile must not point into a wall.
- The sink tile must point outside the map.

### Water Flow <a id="water-flow"></a>

Unlike the original game where the pipes fill based on the time elapsed, this game fills pipes based on the rounds 
elapsed. In other words, when the round ends (i.e. when the player places a pipe), pipes already filled with water will 
let their water flow to adjacent connected pipes.  The following output demonstrates an example of
this in action. A pipe filled with water is displayed as `|`, and the ones without water filled as `║` and `=`.
```
Round 1:
┃═
║.

Round 2:
┃═
┃.
```

The bottom-left pipe `║` in round 1 is filled in round 2 because it is connected to the top-left pipe `|`, whereas the top-right pipe `=` is not filled it is not connected to a filled pipe.

<!-- The exception to this rule is the first 10 rounds, where no pipes will be filled to provide an advantage to the player. -->

### Undo

The game allows players to undo a step from the game. Undoing a step will remove the last placed
pipe, and increment the number of rounds by one. However, it will **NOT** allow water to flow to adjacent pipes. The game allows players to undo multiple steps. When there are no pipes in the map, undo has no effect.

### Winning and Losing Conditions

The game is won when the player must place pipes on the map such that a path is formed from the source tile to the sink 
tile. One of the approaches to check this is to use 
[Breadth First Search](https://en.wikipedia.org/wiki/Breadth-first_search) to search for the sink tile along the pipes. 
You may also use other algorithms or create your own, provided it achieves the same goal.

The game is lost when no additional pipes are filled in each round after the `N`th round. The value of `N' can be configured in the loaded map. In the example maps, `N` is set to 10.

## Loading the Inputs

When launching the game with no command-line arguments, the game by default will generate a map from scratch. In order 
to allow the player to customize the game parameters, the following arguments can be provided:

- `java -jar PA1.jar <map_file>`
    - Loads a map from the specified path
- `java -jar PA1.jar <rows> <cols>`
    - Generates a map with the given number of rows and columns

In IntelliJ IDEA, insert these arguments into the `Program arguments` field in `Run > Edit configurations`. You may also
want to set the `Working directory` to the root directory of your project.

![](img/intellij-run.png)

## Map File Format <a id="map-file-format"></a>

When loading a map from a file, a specific format is used to indicate the various different parameters of the game.

The file should start with the number of rows, a newline, the number of columns, another newline, and finally the number
of rounds before the water starts flowing. After this, the file should contain the ASCII representation of the map.

The ASCII character set is as follows:

- `W`: Wall
- `.`: Cell
- `^`: Source/Sink pipe pointing upward
- `v`: Source/Sink pipe pointing downward
- `<`: Source/Sink pipe pointing leftward
- `>`: Source/Sink pipe pointing rightward

The file can also optionally contain a list of pipes to queue at the beginning of the game, delimited by commas.

The pipe representation set is as follows:

- `TL`: Top-Left (`╝`)
- `TR`: Top-Right (`╚`)
- `BL`: Bottom-Left (`╗`)
- `BR`: Bottom-Right (`╔`)
- `HZ`: Horizontal (`═`)
- `VT`: Vertical (`║`)
- `CR`: Cross (`╬`)

For example, if you would like the first 2 pipes to be a cross followed by a top-left, the last line of the file should
be `CR, TL`.

Comments for the file are available in the form of `# your comment here`.

See the `maps` directory for example maps.

## Java Concepts

### `java.util.Optional` Class

The `Optional` class is a generic container object which represents a value that may or may not exist. In this project, 
we will use this class to represent the fact that a cell may or may not contain a pipe.

The API documentation can be found [here](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/Optional.html).

### Java Collections Classes

This project uses a few library classes in Java Collections. Java Collections provides a set of useful library classes in the `java.util` package for data structures to manage objects.

In particular, we will be using the following library classes:

- `java.util.ArrayList<E>` ([Javadoc](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/ArrayList.html)): A resizable contiguous container; Equivalent to `std::vector<T>` in C++
- `java.util.LinkedList<E>` ([Javadoc](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/LinkedList.html)): Linked list; Equivalent to `std::list<T>` in C++
- `java.util.Stack<E>` ([Javadoc](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/Stack.html)): Stack; Equivalent to `std::stack<T>` in C++

### `final` and Immutability

Like `const` in C++, `final` in Java declares an immutable object whose value cannot be altered once defined. You are recommended to set variables as `final` to enforce immutability and avoid accidentally modifying references.

### Jetbrains Annotations

A common bug in Java applications are `NullPointerException`, where a programmer tries to use a value which happens to 
be `null` during runtime. To reduce the risk of these errors, the Jetbrains Annotations library is bundled with the 
project.

Jetbrains Annotations is a library written by Jetbrains, the company behind IntelliJ IDEA. The purpose of this library 
is to mark variables, fields and methods as either `@Nullable` or `@NotNull` to allow IntelliJ to catch nullability 
violations. An example can be shown below:

![](img/intellij-annotations.png)

The use of these annotations are optional although you are recommended to add these annotations to reduce the risks of `NullPointerException` bugs.

## Tasks

Complete all the TODOs in the entire project. A detailed description of each task is provided in the Javadoc above each 
method. In IntelliJ IDEA, go to `View > Tool Windows > TODO` to jump to each TODO in the project. You may replace the 
placeholder implementation in the methods marked as TODO.

| TODO | Practiced Concepts |
| ----------- | ------------------ |
| game.CellStack::* | Basic Java |
| game.DelayBar::* | Basic Java |
| game.Game::* | Basic Java |
| game.PipeQueue::* | Basic Java |
| game.map.Map::tryPlacePipe | Class, inheritance, Optional |
| game.map.Map::display | Basic Java (or Streams?) |
| game.map.Map::undo | Basic Java |
| game.map.Map::fillTiles | Streams |
| game.map.Map::checkPath | Streams, Optional |
| game.map.Map::hasLost | Basic Java |
| game.map.cells.Cell | Class, inheritance |
| game.map.cells.FillableCell | Class, inheritance, Overriding |
| game.map.cells.TerminationCell | Class, inheritance, Overriding | 
| game.map.cells.Wall | Class, inheritance, Overriding |
| game.pipes.Pipe::* | Basic Java, Overriding |

Some unit tests are provided to test for the implementation of your code.

## Bonus

In real-world developments, testing is important to validating the correctness of software.
The Sample JUnit tests provided by us only cover a small set of scenarios and are not enough
 for validating your implementation comprehensively. 
You are encouraged to write more JUnit tests yourself, which can not only help you debug
more easily, but can also earn you bonus.

When you submit your homework, you can include the JUnit tests written by yourself in the test folder. 
You can either add test methods to existing JUnit files or create new JUnit files.The more test coverage you can achieve over the provided tests, the more bonus you will receive.
Please see the grading scheme for more details.

## Completed Demo

A runnable PA1_obfuscated.jar is provided in the `PA1 Obfuscated` folder. 
Please check the markdown file under that folder about how to run it.
For more information about input formats, please check the above `Loading the Inputs` section. 

## Other Remarks

If the map appears to be distorted, try to switch to other monospace fonts in IntelliJ. You will may set your Console Font to an appropriate monospace font like `Source Code Pro`, `Fira Code` or `MS Gothic` under `Settings > Appearance & Behavior > Editor > Color Scheme > Console Font` for the map to be displayed properly. The font that works may vary across Windows, MacOS and Linux.
