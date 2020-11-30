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


```Bash
.
├── README.md
└── SentencingRobbery
    ├── SentencingRobbery.iml
    ├── pom.xml
    └── src
        ├── main
        │   ├── java
        │   │   └── tjc
        │   │       └── rug
        │   │           ├── ExpertSystem
        │   │           │   ├── Main.java
        │   │           │   ├── controller
        │   │           │   │   ├── Controller.java
        │   │           │   │   └── HomeController.java
        │   │           │   ├── model
        │   │           │   │   ├── AbstractKnowledge.java
        │   │           │   │   ├── Fact.java
        │   │           │   │   ├── Model.java
        │   │           │   │   ├── Question.java
        │   │           │   │   ├── Rule.java
        │   │           │   │   └── State.java
        │   │           │   └── parser
        │   │           │       ├── Parser.java
        │   │           │       ├── QuestionParser.java
        │   │           │       └── RuleParser.java
        │   │           └── META-INF
        │   │               └── MANIFEST.MF
        │   └── resources
        │       └── resources
        │           ├── css
        │           │   ├── exit.css
        │           │   ├── question.css
        │           │   └── style.css
        │           ├── fxml
        │           │   ├── exitScreen.fxml
        │           │   ├── home.fxml
        │           │   ├── main.fxml
        │           │   ├── questionArea.fxml
        │           │   └── trace.fxml
        │           └── knowledgebase
        │               ├── questions.xml
        │               └── rules.xml
        └── test
            ├── KnowledgeTest.java
            ├── ModelTest.java
            ├── ParserTest.java
            ├── StateTest.java
            └── java
            
```
