projektMPM
==========

harmonogramowanie projektów metodą MPM (tzw. metoda francuska)

## OPIS PLIKÓW ##

  `projektMPM.java`  - implementacja algorytmu z interfejsem tekstowym

  `pliki.in`  -pliki z takim rozszerzeniem to przykładowe pliki wejściowe o nastepującej strukturze
  
- w piewszym wierszu znajduje sie liczba etapów danego projektu
- w kolejnych parach znajdują sie dane poszczegolnych etapów (numer id etapu, czas trwania, numery id etapów poprzedzających) wraz z nazwą etapu

## KOMPILACJA ##

	$ javac projektMPM.java
  
  
## URUCHOMIENIE ##

	$ java projektMPM plik.in