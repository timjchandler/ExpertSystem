# Expert System

### Sentencing calculator for Robbery under Swiss Law

This calculator reads in rules from xml files and implements them via a gui


#### Contents:
[How to use the system](### How to use the system)
[Download](### Download)
[JavaDoc](### Documentation)
[Knowledge Base](### Knowledge Base)
[Structure](### Structure)


### How to use the system

### Download   
_<span style="color:grey; font-size:10px">File updated: 18/12/20</span>_     
The latest version, _PROTOTYPE_ can be downloaded [here](https://github.com/timjchandler/ExpertSystem/blob/main/SentencingRobbery-PROTOTYPE.jar)    

It should run simply by double clicking on it. Alternatively run the following command from the directory that it is stored in:  
```java -jar SentencingRobbery-PROTOTYPE.jar```

### JavaDoc

The JavaDoc for this system may be found [here](https://timjchandler.github.io/ExpertSystem/JavaDoc/overview-summary.html)

### Knowledge Base

The xml for the knowledge base may be found here:

+ The [rules](https://github.com/timjchandler/ExpertSystem/blob/main/SentencingRobbery/src/main/resources/resources/knowledgebase/rules.xml)

+ The [questions](https://github.com/timjchandler/ExpertSystem/blob/main/SentencingRobbery/src/main/resources/resources/knowledgebase/questions.xml)

+ The [outputs](https://github.com/timjchandler/ExpertSystem/blob/main/SentencingRobbery/src/main/resources/resources/knowledgebase/outputs.xml)


## Structure

Below is a tree of the relevant structure. Test and target directories are not expanded.
_<span style="color:grey; font-size:10px">Last updated: 18/12/20</span>_   

```bash
.
├── pom.xml
├── src
│   ├── main
│   │   ├── appengine
│   │   │   └── app.yaml
│   │   ├── java
│   │   │   └── tjc
│   │   │       └── rug
│   │   │           ├── ExpertSystem
│   │   │           │   ├── ExpertSystemMain.java
│   │   │           │   ├── Main.java
│   │   │           │   ├── controller
│   │   │           │   │   ├── AnimateElement.java
│   │   │           │   │   ├── Controller.java
│   │   │           │   │   ├── ExitController.java
│   │   │           │   │   ├── HomeController.java
│   │   │           │   │   ├── QuestionController.java
│   │   │           │   │   └── TraceController.java
│   │   │           │   ├── model
│   │   │           │   │   ├── AbstractKnowledge.java
│   │   │           │   │   ├── Fact.java
│   │   │           │   │   ├── Model.java
│   │   │           │   │   ├── Output.java
│   │   │           │   │   ├── Question.java
│   │   │           │   │   ├── Response.java
│   │   │           │   │   ├── Rule.java
│   │   │           │   │   ├── Sentence.java
│   │   │           │   │   └── State.java
│   │   │           │   └── parser
│   │   │           │       ├── OutputParser.java
│   │   │           │       ├── Parser.java
│   │   │           │       ├── QuestionParser.java
│   │   │           │       └── RuleParser.java
│   │   │           └── META-INF
│   │   │               └── MANIFEST.MF
│   │   └── resources
│   │       └── resources
│   │           ├── css
│   │           │   ├── exit.css
│   │           │   ├── home.css
│   │           │   ├── primary.css
│   │           │   ├── question.css
│   │           │   └── trace.css
│   │           ├── fxml
│   │           │   ├── exit.fxml
│   │           │   ├── home.fxml
│   │           │   ├── main.fxml
│   │           │   ├── questionArea.fxml
│   │           │   └── trace.fxml
│   │           ├── knowledgebase
│   │           │   ├── outputs.xml
│   │           │   ├── questions.xml
│   │           │   └── rules.xml
│   │           └── media
│   │               ├── home.png
│   │               ├── icon.jpg
│   │               ├── icon.png
│   │               ├── icon.svg
│   │               └── scales.png
│   └── test
├── summary.pdf
└── target      
```
