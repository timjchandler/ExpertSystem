## Expert System

#### Sentencing calculated for Robbery under Swiss Law

This calculator reads in rules from xml files and implements them via a gui


### Structure

```bash
.
├── README.md
└── SentencingRobbery
    ├── knowledgebase
    │   ├── questions.xml
    │   └── rules.xml
    └── src
        ├── ExpertSystem
        │   ├── Main.java
        │   ├── controller
        │   │   ├── Controller.java
        │   │   └── QuestionController.java
        │   ├── model
        │   │   ├── AbstractKnowledge.java
        │   │   ├── Fact.java
        │   │   ├── Model.java
        │   │   ├── Question.java
        │   │   ├── Rule.java
        │   │   └── State.java
        │   ├── parser
        │   │   ├── Parser.java
        │   │   ├── QuestionParser.java
        │   │   └── RuleParser.java
        │   └── view
        │       ├── css
        │       │   └── style.css
        │       └── fxml
        │           ├── main.fxml
        │           ├── questionArea.fxml
        │           └── trace.fxml
        └── tests
            ├── KnowledgeTest.java
            ├── ModelTest.java
            ├── ParserTest.java
            └── StateTest.java
            
```
