# BWI_Challenge_2021
Hallo liebes BWI-Team,

zuerst einmal vielen Dank für die Challenge!
Es hat Spaß gemacht sich mal wieder mit einer guten Portion Mate vor den Rechner zu setzen.
Ich hatte leider erst am 06.01. von der Challenge erfahren und bin daher noch nicht ganz fertig geworden.
Trotzdem wollte ich meinen jetzigen Stand einreichen, und hoffe auf ein kleines Feedback, ob ich überhaupt auf dem Richtigen weg bin :)
Der Code errechnet für kleinere Probleme die richtige Lösung, läuft jedoch bei dem gestellten Problem noch mehrere Stunden.
Mein Nächster Ansatz wäre das Problem in subprobleme aufzuteilen und eine Kombination aus dem aktuellen Algorithmus, 
sowie Dynamischem Programmieren anzuwenden. 
Folgend finden Sie alle weiteren Informationen. 

Mit freundlichen Grüßen,
Christian Staudigl


Ausführung:

	Windows (empfohlen):
		- Java muss installiert sein (getestet mit Version 1.8.0_212 64bit)
		- JAVA_HOME muss gesetzt sein
		- Im Ordner "Ausführung" die "start.bat" ausführen
		- Den Anwisungen des Programs folgen
				 
	Linux:
		- Java (getestet mit openjdk-8-jdk) installieren
		- Terminal starten (falls nicht eh schon geschehen)
		- "cd pfad/zum/Ordner/Challenge/Ausführung/"
		- "java challenge.jar"
		- Den Anwisungen des Programs folgen
		
	Hinweis: Die Datei Items.csv enthält die Werte für die Challenge als csv.
			 Diese werden im Program eingelesen, wenn keine andere Datei angegeben wird.


Algorithmus:

	An sich ist das ganze ein 0/1-Knapsack Problem. 
	Daher viel meine erste Wahl auf Dynamisches Programmieren mit einem 3D-Tensor / Würfel.
	Das funktioniert allerdings nur mit ganzzahligen Werten, also mussten die Gewichte in Kilo auf Gramm erweitert werden.
	Dadurch wurde diese Methode allerdings viel zu Speicherintensiv (O(N*C1*C2), N = Anzahl der Items, CX = Kapazität der LKWs in Gramm).
	
	Daher habe ich jetzt einen "LeastCount Branch and Bound" - Algorythmus als Vorlage genommen.
	Dieser Algorythmus benötigt zwar nur recht wenig Speicher, hat aber im schlimmsten Fall eine exponentielle Laufzeit.
	Durch die Struktur der Aufgabe, wie etwa, dass Items in der gleichen Kategorie die gleichen Werte haben, lässt sich die Laufzeit zwar minimal optimieren,
	ist aber bei weitem noch nicht O(N) oder Ähnliches.
	
	Ich bin mir sicher, dass ich noch nicht den besten Weg gefunden habe, habe aber leider keine Zeit mehr um andere Sachen auszuprobieren.
	Nichtsdestotrotz wollte ich gerne machen, und wie der Zufall so will läuft der Algorithmus mit der Beispieldaten erstaunlich schnell durch :P
	
	TL;DR:
	Es wird eine abgewandelte Art des "LeastCount Branch and Bound" - Algorythmus verwendet.
	

Ergebnis:

	Auch, wenn zeitlich bedingt, nur minimal getestet, hier die Ergebnisse von der Version vom 09.01.2021:
	
	LkW "Lkw 1":
		<> "Mobiltelefon Outdoor" - 3 Stk.
		<> "Mobiltelefon Heavy Duty" - 217 Stk.
		<> "Mobiltelefon BÃ¼ro" - 54 Stk.
		<> "Tablet outdoor groÃ?" - 361 Stk.
		<> "Tablet BÃ¼ro klein" - 1 Stk.
		<> "Tablet BÃ¼ro groÃ?" - 2 Stk.
		<> "Tablet outdoor klein" - 2 Stk.

	LkW "Lkw 2":
		<> "Mobiltelefon Outdoor" - 154 Stk.
		<> "Mobiltelefon Heavy Duty" - 3 Stk.
		<> "Mobiltelefon BÃ¼ro" - 6 Stk.
		<> "Tablet outdoor groÃ?" - 9 Stk.
		<> "Tablet BÃ¼ro klein" - 592 Stk.
		<> "Tablet BÃ¼ro groÃ?" - 2 Stk.
		<> "Tablet outdoor klein" - 1 Stk.

		Maximaler Wert Lkw 1: 		40663
		Maximaler Wert Lkw 2: 		34032
		Gesamtwert: 				74695
		Gewicht ohne Fahrer Lkw 1: 	1027,207 Kg
		Gewicht ohne Fahrer Lkw 2: 	1018,659 Kg
		Gewicht mit Fahrer Lkw 1: 	1099,607 Kg
		Gewicht mit Fahrer Lkw 2: 	1099,994 Kg
		Ausführungszeit: 			27299 Sekunden
		Anzahl an Items: 			3292
		Anzahl an Boxen: 			10

	
	
	
