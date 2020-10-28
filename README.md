# Strom

## Verwendungszweck

Dieses Programm soll anhand eingegebener Zählerstände für mehrere Parteien Rechnungen generieren und diese bevorzugt als
Email an die Parteien senden.
Falls für eine Partei keine Email-Adresse bekannt ist, soll stattdessen ein PDF-Dokument erstellt werden, dass später
ausgedruckt und per Post verschickt werden kann.

## Anforderungen

* Das Programm, sowie die darin geführten Informationen müssen leicht portabel sein
* Es gibt einen festen Berechnungszeitraum, quasi ein festes Geschäftsjahr
* Ein Stromzähler kann innerhalb eines Geschäftsjahres mehrfach den Besitzer wechseln
* Zählerstände werden immer gemeinsam mit einem Ablesedatum eingetragen
* Für alle Parteien gilt der gleiche definierbare Strompreis
* Wenn über einen Zähler im Geschäftsjahr Energieverbrauch gemessen wird, fällt über die Nutzungszeit eine definierbare
Grundgebühr an
* Bei der Ersteinrichtung einer neuen Partei, wird einmalig eine definierbare Anschlussgebühr fällig
* Die Anschlussgebühr wird nicht fällig, wenn die Partei zu einem anderen Zähler umzieht, oder nach Kündigung des
Stromanschlusses erneut einen bucht.
* Vom Programm ermittelte Kosten sollen vor der Erstellung der Rechnungen von Hand geändert werden können
* Rechnungen sollen als PDF-Dokumente ausgegeben werden
* Es soll auch eine Übersicht über alle Parteien als PDF ausgegeben werden
* Es soll eine Schnittstelle geben, über die Daten in einem abstrakten Format importiert und exportiert werden können

## Umsetzung

### Programm

Für eine möglichst einfache Übergabe an andere Arbeitsplätze, möchte ich das Programm in Java 11 verfassen.
Es wird eine eigene Datenbank erstellen, die im gleichen Verzeichnis liegen wird, wie das Programm selbst. So kann man
beide Dateien in einem Archiv zusammenpacken und per Email, USB-Stick oder Fileshareing-Dienste übergeben. 

### PDF-Dateien

Für die Erstellung von PDF-Dateien gibt es mehrere Möglichkeiten. Ich möchte mit der Template-Engine
[Velocity](http://velocity.apache.org/) HTML-Dokumente erstellen, die anschließend mit [WK\<html>TOpdf](https://wkhtmltopdf.org/)
in PDF-Dateien umgewandelt werden. Ich meine, dass es so einfacher ist das Layout von PDF-Dateien zu definieren, ohne
darauf angewiesen zu sein, den Inhalt von Java als Bild zu rendern.

Mir ist sehr wohl bewusst, dass WK\<html>TOpdf ein plattformabhängiges Programm ist, deshalb soll es dieses Programm den
Anwender nach dem Installations-Pfad fragen, wenn es nicht an einem Standard-Ort gefunden wurde. 

### Post- und Email-Versand

Wenn der Arbeits- und Kostenaufwand vertretbar ist, möchte ich auch den Briefversand automatisieren.
Ich habe mit einer kleinen Recherche herausgefunden, dass es für den [e-Post Brief](https://www.deutschepost.de/de/e/epost.html)
auch eine API gibt. Damit können Briefe online gesendet werden. Wenn der Empfänger eine Hausanschrift ist, wird der Brief
in einem Verteilerzentrum von der Deutschen Post automatisch ausgedruckt und in einem Briefumschlag versendet.

### Datenbank

Damit das Programm leicht portabel ist, kann ich nicht auf einen Datenbankserver wie mySQL oder solr zurückgreifen.
Ich meine, dass die Verwendung von [ObjectDB](https://www.objectdb.com/) geeignet ist, weil die Datenbankstruktur direkt
mit den Entity-Klassen festgelegt werden kann.

### Import und Export

Damit die Daten auch von anderen Programmen verarbeitet werden können, muss es eine Möglichkeit für einen Datenaustausch
geben. Ich halte es hier für sinnvoll mit CSV-Dateien zu arbeiten, weil diese zum Beispiel ohne weiteres in Access oder
Excel importiert werden können. Für jeden Datentyp (Zählerstände, Kontaktdaten, Kostenübersicht, ...) soll es jeweils
eine eigene CSV-Datei geben. Damit man die Dateien besser voneinander unterscheiden kann, wird immer eine Kopfzeile mit
den Spaltendefinitionen ausgegeben, bzw. beim Import erwartet.