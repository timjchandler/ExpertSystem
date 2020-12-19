<p align="center">
 <img src="https://raw.githubusercontent.com/timjchandler/ExpertSystem/main/SentencingRobbery/src/main/resources/resources/media/icon.svg" alt="tjc logo"      width="100" height="100">
</p>

This system provides a recommended sentence for cases in which the convicted has committed robbery.

* TOC
{:toc} 

# Download   
_<span style="color:grey; font-size:10px">File updated: 19/12/20</span>_     
The latest version, _PROTOTYPE-V2_ can be downloaded [here](https://github.com/timjchandler/ExpertSystem/blob/main/SentencingRobbery-PROTOTYPE-V2.jar)    

To run the system, Java is required. 

The system does not require installation, it should run simply by double clicking on it.  
Alternatively run the following command from the directory that it is stored in:  
```java -jar SentencingRobbery-PROTOTYPE.jar```

Note that you may need to provide admin/super user permissions to run the system.


# How to use the system

### Calculating a sentence

Upon running the system click Start on the left menu bar and answer all of the questions. Questions with circular boxes are single choice, square boxes are multiple choice. You must always select at least one answer to progress. 

If you wish to restart, the start button (now restart) may be clicked to take you back to the beginning and clear answers.

### Viewing progress

By clicking on the Trace button you may view all of the questions you have answered up to that point. You can also switch to the facts tab to view the given and inferred facts. Please note that the facts are primarily for debugging, and as such are in a less human-readable form.

Pressing the Trace button (now Hide Trace) again will take you back to the questions.

### Viewing results

Once you have answered all of the questions you will be presented with a short form. Here you may input your own name, the case number and the filename to save the output under. The first two fields are optional, if provided they will simply add the given information to the output.

The filename will only accept letters and numbers. Any other characters will be stripped out. The file will be saved in the directory that the system was run from.

# JavaDoc

The JavaDoc for this system may be found [here](https://timjchandler.github.io/ExpertSystem/JavaDoc/overview-summary.html)
_<span style="color:grey; font-size:10px">Last updated: 18/12/20</span>_   

# Knowledge Base

The xml for the knowledge base may be found here:

+ The [rules](https://github.com/timjchandler/ExpertSystem/blob/main/SentencingRobbery/src/main/resources/resources/knowledgebase/rules.xml)

+ The [questions](https://github.com/timjchandler/ExpertSystem/blob/main/SentencingRobbery/src/main/resources/resources/knowledgebase/questions.xml)  

+ The [outputs](https://github.com/timjchandler/ExpertSystem/blob/main/SentencingRobbery/src/main/resources/resources/knowledgebase/outputs.xml)  


# Structure

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
