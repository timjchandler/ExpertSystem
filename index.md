# Expert System

### Sentencing calculator for Robbery under Swiss Law

This calculator reads in rules from xml files and implements them via a gui

An executable .jar of the current version _PROTOTYPE_ can be downloaded [here](https://github.com/timjchandler/ExpertSystem/blob/main/SentencingRobbery-PROTOTYPE.jar)

## Documentation

The JavaDoc for this system may be found [here](https://timjchandler.github.io/ExpertSystem/JavaDoc/overview-summary.html)

## Knowledge Base

The xml for the knowledge base may be found here:

+ The [rules](https://timjchandler.github.io/ExpertSystem/SentencingRobbery/knowledgebase/rules.xml)

+ The [questions](https://timjchandler.github.io/ExpertSystem/SentencingRobbery/knowledgebase/questions.xml)


## Structure

```bash
.
├── SentencingRobbery.iml
├── knowledgebase
│   ├── questions.xml
│   └── rules.xml
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   ├── ExpertSystem
    │   │   │   ├── Main.java
    │   │   │   ├── controller
    │   │   │   │   ├── Controller.java
    │   │   │   │   └── HomeController.java
    │   │   │   ├── model
    │   │   │   │   ├── AbstractKnowledge.java
    │   │   │   │   ├── Fact.java
    │   │   │   │   ├── Model.java
    │   │   │   │   ├── Question.java
    │   │   │   │   ├── Rule.java
    │   │   │   │   └── State.java
    │   │   │   ├── parser
    │   │   │   │   ├── Parser.java
    │   │   │   │   ├── QuestionParser.java
    │   │   │   │   └── RuleParser.java
    │   │   │   └── view
    │   │   │       ├── css
    │   │   │       │   ├── exit.css
    │   │   │       │   ├── question.css
    │   │   │       │   └── style.css
    │   │   │       └── fxml
    │   │   │           ├── exitScreen.fxml
    │   │   │           ├── home.fxml
    │   │   │           ├── main.fxml
    │   │   │           ├── questionArea.fxml
    │   │   │           └── trace.fxml
    │   │   ├── META-INF
    │   │   │   └── MANIFEST.MF
    │   │   └── tests
    │   │       ├── KnowledgeTest.java
    │   │       ├── ModelTest.java
    │   │       ├── ParserTest.java
    │   │       └── StateTest.java
    │   └── resources
    └── test
        └── java
            
```
