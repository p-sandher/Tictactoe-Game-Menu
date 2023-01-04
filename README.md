<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
<a name="readme-top"></a>
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->



<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/p-sandher/Tictactoe-Game-Menu/">
    <img src="puneet-sandher-logo.png" alt="Logo" width="200" height="200">
  </a>

<h3 align="center">Tictactoe Game Hub</h3>

  <p align="center">
    <br />
    <a href="https://github.com/p-sandher/Tictactoe-Game-Menu/"><strong>Explore the Repo »</strong></a>
    <br />
    <br />

  </p>
</div>


## About The Project

A game hub with tictactoe and numerical tictactoe with a GUI and terminal version. This is an academic project and the code may not be published publically.

### Built With
* [![Js][Js]][Js-url]
* [![Junit][Junit]][Junit-url]

### Description

This project has the following classes that are extended:
1. BoardGame --> Methods involving the running the game
2. Grid --> Methods with maintaining the gameboard grid 

Interface:
The Saveable class is an interface that is used for loading and saving game files.

Class for file handling
1. FileHandler does the saving and loading of the files

Classes used for the GUI:
1. GameUI --> has the main menu and features that cause the in execution of tictactoe and numerical tictactoe
2. TictactoeView --> GUI of tictactoe, handles all user communication, using TictactoeGame for running the game
3. NumericalTictactoeView --> GUI of numerical tictactoe, handles all user communication, using NumericalTictactoeGame for running the game

Class for Player handling
1.Player ->handles the saving of player profile, loading a player profile, and creating the string of the player score board 
Player Profile Format 
<Name>,<Games Won>
e.g. a textfile would look like 
Kathy,3
Classes used in tictactoe
1. TictactoeGame--> Methods involving the running the game (player turn, checking is game is over, creating messages to print, error checking user input, handles game play)
2. TictactoeGrid --> Methods with maintaining the gameboard grid (placing game piece, updating grid, checking for wins, handles anything related to the grid)
3. TextUI --> Handles all the printing to the terminal and getting user input

Classes used in Numerical tictactoe
1. NumericalTictactoeGame--> Methods involving the running the game (player turn, checking is game is over, creating messages to print, error checking user input, handles game play)
2. NumericalTictactoeGrid --> Methods with maintaining the gameboard grid (placing game piece, updating grid, checking for wins, handles anything related to the grid)
3. NumericalTextUI --> Handles all the printing to the terminal and getting user input


All classes use exceptions, encapsulation,inheritance,interfaces, constructors, accessors, mutators and other object-oriented concepts.


### Dependencies

Java Libraries Used: 
    Java Swing libraries<br />
    import java.io.File;<br />
    import java.util.Iterator;<br />
    import java.lang.String;<br />
    import java.util.Collections;<br />
    import java.util.ArrayList;<br />
    import java.util.Scanner;<br />
    import java.io.IOException;<br />
    import java.nio.file.FileSystems;<br />
    import java.nio.file.Files;<br />
    import java.nio.file.Path;<br />

The program uses Gradle to build and run the program.


### Executing program

* How to run the GUI
* Step-by-step bullets
Step 1
```
gradle clean build
```
Step 2
```
gradle run
```
Step 3 
```
java -jar build/libs/a3.jar game.GameUI
```
Note step 3 can not be ran scioer shell. 
* How to run the Numerical tictactoe
* Step-by-step bullets
Step 1
```
gradle clean build
```
Step 2
```
gradle run
```
Step 3 
```
java -cp build/classes/java/main numericaltictactoe.NumericalTictactoeTextUI
```
* How to run the Tictactoe
* Step-by-step bullets
Step 1
```
gradle clean build
```
Step 2
```
gradle run
```
Step 3 
```
java -cp build/classes/java/main tictactoe.TictactoeTextUI
```

## Limitations

The user can't specifiy which folder to save a game file, it is automatically stored in assets.

<!-- CONTACT -->
## Contact


[![LinkedIn][linkedin-shield]][linkedin-url]
<br />
Puneet Sandher -  puneetsandher@gmail.com

Project Link: [https://github.com/p-sandher/Tictactoe-Game-Menu](https://github.com/p-sandher/Tictactoe-Game-Menu)


<p align="right">(<a href="#readme-top">back to top</a>)</p>

Keep a log of what things you accomplish when.  You can use git's tagging feature to tag the versions or you can reference commits.
* 0.6
    * Added JUnit Testing and JavaDocs
    * See [release history](https://github.com/p-sandher/Tictactoe-Game-Menu)
* 0.5
    * created exceptions for user game position
    * See [release history](https://github.com/p-sandher/Tictactoe-Game-Menu)
* 0.4
    * connected all methods to create a complete game and fixed bugs with constructors in ConnectFour
    * See [release history](https://github.com/p-sandher/Tictactoe-Game-Menu)
* 0.3
    * fixed bugs and added file writer
    * See [release history](https://github.com/p-sandher/Tictactoe-Game-Menu)
* 0.2
    * Created file parser, win conditions, player turn methods and textUI messages
    * See [release history](https://github.com/p-sandher/Tictactoe-Game-Menu)
* 0.1
    * Initial Release



[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/puneet-sandher/
[Js]: https://img.shields.io/badge/Java-323330?style=for-the-badge&logo=javascript&logoColor=F7DF1E
[Js-url]: https://docs.oracle.com/en/java/
[Junit]: https://img.shields.io/badge/Junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white
[Junit-url]: https://junit.org/junit5/docs/current/user-guide/


