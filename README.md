# LaMetric KVB Abfahrtsmonitor

## Konfiguration

### Parameter

| Parameter            | Pflicht | Beschreibung                      |
| -------------------- |---------|---------------------------------- |
| stationId            | x       | ID der KVB Station                |
| minTime              |         | Minimale Abfahrtszeit in Minuten  |
| maxTime              |         | Maximale Abfahrtszeit in Minuten  |
| limit                |         | Anzahl maximaler Abfahrtszeiten   |
| filteredDestinations |         | Ignorierte Fahrtziele             |
| frames               | x       | Gewünschte Ansichten              |

### KVB Station
Über das Parameter *stationId* muss eine KVB Haltestelle gewählt werden. Die ID der gewünschten Haltestelle kann 
am einfachsten direkt über die KVB Webseite http://www.kvb-koeln.de/qr/haltestellen ermitteln. Wenn man dort über
den Anfagsbuchstaben die gewünschte Haltestelle angeklickt hat, wird in der Browserzeile die ID angezeigt.

| Haltestelle   | ID  | 
| ------------- | --- |
| Dom/Hbf.      | 8   | 
| Neumarkt      | 2   | 
| Heumarkt      | 1   | 
| ...           | ... | 


### Ansichten
Es können ein oder mehrere Ansichten (s.g. Frames) ausgewählt werden. Sollten mehrere Ansichten aktiv sein,
wechselt LaMetric diese regelmäßig. Auf die Scroll- und Wechselgeschwindigkeit kann leider von der App kein 
Einfluss genommen werden.

* **disruption**: Zeigt Betriebsstörungen ("Fahrplanunregelmäßigkeiten") zu Haltestelle als Lauftext an. 
  Sollten mehrere Störungen vorliegen, wird je Störung ein eigener Lauftext dargestellt. Falls aktuell mal 
  Störung vorliegt, wird diese Ansicht ignoriert.
* **singleDeparture**: Zeigt die Abfahrtszeit der nächsten Bahn in Minuten. Es empfiehlt sich über das Parameter 
  *filteredDestinations* irrelevante Bahnen (z.B. Gegenrichtung oder andere Linie) herauszufiltern. Mit dem Parameter 
  *minTime* können zusätzlich Bahnen gefiltert werden, die man aufgrund der Wegzeit nicht mehr erreicht. Diese Ansicht
  ist sehr minimalistisch, verzichtet auf Scrolleffekte und lenkt kaum ab. 
* **allDepartures**: Zeigt die Abfahrtszeiten der nächsten Bahnen in Minuten. Über das Parameter *limit* kann die
  Anzahl der darzustellenden Bahnen limitiert werden. Die Abfahrtszeiten scrollen durch das Bild.