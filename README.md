# ExpertSystem

#### An expert system for calculating sentence duration for robbery under Swiss law.

```Bash
.
├── README.md
└── SentencingRobbery
    ├── knowledgebase
    │   ├── questions.xml
    │   └── rules.xml
    └── src
        ├── ExpertSystem
        │   ├── Main.java
        │   ├── controller
        │   │   ├── Controller.java
        │   │   └── QuestionController.java
        │   ├── model
        │   │   ├── AbstractKnowledge.java
        │   │   ├── Fact.java
        │   │   ├── Model.java
        │   │   ├── Question.java
        │   │   ├── Rule.java
        │   │   └── State.java
        │   ├── parser
        │   │   ├── Parser.java
        │   │   ├── QuestionParser.java
        │   │   └── RuleParser.java
        │   └── view
        │       ├── css
        │       │   └── style.css
        │       └── fxml
        │           ├── main.fxml
        │           ├── questionArea.fxml
        │           └── trace.fxml
        └── tests
            ├── KnowledgeTest.java
            ├── ModelTest.java
            ├── ParserTest.java
            └── StateTest.java
            
```

