# IT45-Projet-SESSAD

## Explication :

Projet réalisé en IT45 à l’UTBM. L’objectif de ce dernier est d’utiliser les méthodes enseignées en cours dans un problème concret de recherche opérationnelle. 
Ce projet vise à mettre en place un système d’affectation de missions à plusieurs centres SESSAD en utilisant les algorithmes d’optimisation vu en cours. Les centres SESSAD sont des centres fournissant des Service d’accompagnement d'Éducation Spécialisée et de Soin À Domicile pour des personnes vulnérables. Chaque centre possède sa propre localisation et ses propres employés parlant, soit le langage des signes LSF et/ou le codage LPC et possédant 0-n spécialités (électricité, informatique, cuisine…). Les missions doivent être affectées aux centres ayant les employés capables de respecter les compétences et spécialités nécessaires, tout en minimisant la distance entre les missions et les centres. Même si l’affectation selon les spécialités est optionnelle.  
L’objectif de ce projet est donc d’optimiser ce système d'affectation en prenant en compte les compétences et spécialités des employés, la localisation des missions, centres et employés, mais également les horaires des missions. 

Ce projet à été codé en java en utilisant la suite JetBrains et plus précisément IntelliJ IDEA. il se build et se compile ici dirrectement en cliquant sur "run"
Un makefile à tout de meme été créé pour lancer le programme sous linux, cependant il se build mais ne peut pas se run car le chemin des fichiers d'instances n'est pas trouvé et nous n'avons pas réussi à comprendre pourquoi. Pour cette raison nous vous conseillons d'utiliser IntelliJ IDEA dirrectement

## Commandes utiles :

#### 1. Positionnement dans le fichier src
    cd src
    
### Pour lancer le make file

#### 1. Execution du Makefile
    make

#### 2. Execution du programme
    java -cp bin Main
    
    
#### 3. Nettoyage des fichiers générés
     rm -f Main.class Centre.class Employé.class EmployéEdt.class Mission.class CSVReader.class CoupleEmployéMission.class Kmeans.class AlgoTabou.class


### Autre possibilité pour le lancer avec les commandes depuis un terminal
#### 1. Build le projet
    javac -g Main.java Centre.java Employé.java EmployéEdt.java Mission.java CSVReader.java CoupleEmployéMission.java Kmeans.java AlgoTabou.java

#### 2. Execution du programme
    java Main
    
 #### 3. Nettoyage des fichiers générés
     rm -f Main.class Centre.class Employé.class EmployéEdt.class Mission.class CSVReader.class CoupleEmployéMission.class Kmeans.class AlgoTabou.class


N.B. Dans les 2 cas, le programme ici ne se run pas car le chemin des fichiers d'instances n'est pas trouvé et nous n'avons pas réussi à comprendre pourquoi. Pour cette raison nous vous conseillons d'utiliser IntelliJ IDEA dirrectement
