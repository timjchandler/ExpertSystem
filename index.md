# Expert System

### Sentencing calculator for Robbery under Swiss Law

This calculator reads in rules from xml files and implements them via a gui

### Download
_<span style="color:grey; font-size:10px">File updated: 30/11/20</span>_  
An executable .jar of the current version _PROTOTYPE_ can be downloaded [here](https://github.com/timjchandler/ExpertSystem/blob/main/SentencingRobbery-PROTOTYPE.jar)   

It should run simply by double clicking on it. Alternatively run the following command from the directory that it is stored in:  
```java -jar SentencingRobbery-PROTOTYPE.jar```

## Documentation

The JavaDoc for this system may be found [here](https://timjchandler.github.io/ExpertSystem/JavaDoc/overview-summary.html)

## Knowledge Base

The xml for the knowledge base may be found here:

+ The [rules](https://timjchandler.github.io/ExpertSystem/SentencingRobbery/knowledgebase/rules.xml)

+ The [questions](https://timjchandler.github.io/ExpertSystem/SentencingRobbery/knowledgebase/questions.xml)


## Structure

```bash
.
├── README.md
└── SentencingRobbery
    ├── SentencingRobbery.iml
    ├── pom.xml
    └── src
        ├── main
        │   ├── java
        │   │   └── tjc
        │   │       └── rug
        │   │           ├── ExpertSystem
        │   │           │   ├── Main.java
        │   │           │   ├── controller
        │   │           │   │   ├── Controller.java
        │   │           │   │   └── HomeController.java
        │   │           │   ├── model
        │   │           │   │   ├── AbstractKnowledge.java
        │   │           │   │   ├── Fact.java
        │   │           │   │   ├── Model.java
        │   │           │   │   ├── Question.java
        │   │           │   │   ├── Rule.java
        │   │           │   │   └── State.java
        │   │           │   └── parser
        │   │           │       ├── Parser.java
        │   │           │       ├── QuestionParser.java
        │   │           │       └── RuleParser.java
        │   │           └── META-INF
        │   │               └── MANIFEST.MF
        │   └── resources
        │       └── resources
        │           ├── css
        │           │   ├── exit.css
        │           │   ├── question.css
        │           │   └── style.css
        │           ├── fxml
        │           │   ├── exitScreen.fxml
        │           │   ├── home.fxml
        │           │   ├── main.fxml
        │           │   ├── questionArea.fxml
        │           │   └── trace.fxml
        │           └── knowledgebase
        │               ├── questions.xml
        │               └── rules.xml
        └── test
            ├── KnowledgeTest.java
            ├── ModelTest.java
            ├── ParserTest.java
            └── StateTest.java
            
            
```
