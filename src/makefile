JAVAC = javac
JFLAGS = -g
SOURCES = Main.java Centre.java Employé.java EmployéEdt.java Mission.java CSVReader.java CoupleEmployéMission.java Kmeans.java AlgoTabou.java
CLASSES = $(SOURCES:.java=.class)

.PHONY: default compile clean

default: compile

compile: $(CLASSES)

%.class: %.java
	$(JAVAC) $(JFLAGS) $<

clean:
	rm -f $(CLASSES)
