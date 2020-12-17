# ExpertSystem

#### An expert system for calculating sentence duration for robbery under Swiss law.

### TODO:

+ Refactoring:
    - Break up controller class
+ Rules:
    - Add more functionality
    - Decision made more by the rules, not the system
    - Fewer 1 to 1 rules
    - Add more rules
    - Add more questions

### Relevant Structure

```Bash
.
├── README.md
├── SentencingRobbery
│   ├── pom.xml
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   │   └── tjc
│   │   │   │       └── rug
│   │   │   │           ├── ExpertSystem
│   │   │   │           │   ├── FatJarMain.java
│   │   │   │           │   ├── Main.java
│   │   │   │           │   ├── controller
│   │   │   │           │   │   ├── AnimateElement.java
│   │   │   │           │   │   ├── Controller.java
│   │   │   │           │   │   ├── ExitController.java
│   │   │   │           │   │   ├── HomeController.java
│   │   │   │           │   │   ├── QuestionController.java
│   │   │   │           │   │   └── TraceController.java
│   │   │   │           │   ├── model
│   │   │   │           │   │   ├── AbstractKnowledge.java
│   │   │   │           │   │   ├── Fact.java
│   │   │   │           │   │   ├── Model.java
│   │   │   │           │   │   ├── Question.java
│   │   │   │           │   │   ├── Response.java
│   │   │   │           │   │   ├── Rule.java
│   │   │   │           │   │   ├── Sentence.java
│   │   │   │           │   │   └── State.java
│   │   │   │           │   └── parser
│   │   │   │           │       ├── Parser.java
│   │   │   │           │       ├── QuestionParser.java
│   │   │   │           │       └── RuleParser.java
│   │   │   │           └── META-INF
│   │   │   │               └── MANIFEST.MF
│   │   │   └── resources
│   │   │       └── resources
│   │   │           ├── css
│   │   │           │   ├── exit.css
│   │   │           │   ├── home.css
│   │   │           │   ├── primary.css
│   │   │           │   ├── question.css
│   │   │           │   └── trace.css
│   │   │           ├── fxml
│   │   │           │   ├── exit.fxml
│   │   │           │   ├── home.fxml
│   │   │           │   ├── main.fxml
│   │   │           │   ├── questionArea.fxml
│   │   │           │   └── trace.fxml
│   │   │           ├── knowledgebase
│   │   │           │   ├── questions.xml
│   │   │           │   └── rules.xml
│   │   │           └── media
│   │   │               ├── home.png
│   │   │               ├── icon.png
│   │   │               ├── icon2.png
│   │   │               ├── icon3.png
│   │   │               └── scales.png
│   │   └── test
│   └── target
├── SentencingRobbery-PROTOTYPE-V2.jar
└── SentencingRobbery-PROTOTYPE.jar
```
