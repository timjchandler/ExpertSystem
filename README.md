# ExpertSystem

#### An expert system for calculating sentence duration for robbery under Swiss law.

More information about the system may be found [here](https://timjchandler.github.io/ExpertSystem/)

#### Running from IntelliJ (or another IDE)

1. Open ```SentencingRobbery/pom.xml``` in IntelliJ

2. Run ```mvn compile``` (requires maven)

3. Now the system should be runnable from either ```Main.java``` or ```ExpertSystemMain.java```

#### Notes:

This has only been tested with IntelliJ and running through the compiled ```.jar``` file. Whilst it will no doubt work in other IDEs such as eclipse, I am not familiar with them.  

There are two main files due to a quirk with compiling JavaFX to a single ```.jar``` file. Both will run the System as one simply calls the other.
