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
* **Wichtig:** Ein Stromzähler kann auch mal ausfallen und muss ersetzt werden.
* Ein Zählerwechsel hat nichts mit dem Stromliefervertrag zu tun.
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

## Problem: Kaputten Zähler ersetzen

Ich habe bei meinem ersten Entwurf nicht berücksichtigt, dass Zähler ausgetauscht werden müssen.

In Rücksprache mit einem Facharbeiter im Ruhestand, der in einem Prüflabor gearbeitet hatte, habe ich in Erfahrung gebracht, wie im Regelfall bei einem Defekt des Zählers die Rechnung angepasst wird.
Dabei kommt es auf den diagnostizierten Defekt an.

In der Regel wird der Verbrauch zu gunsten des Abnehmers geschätzt.

### 1. Zähler läuft zu schnell, oder läuft ohne Last

In diesem Fall wird durch ein Prüflabor ermittelt, um wie viel der Zähler zu viel erfasst hat.
Da bei der Analyse nicht erkannt werden kann, seit wann der Fehler existiert, wird der Verbrauch des vorigen Verrechnungszeitraumes geschätzt.
Die daraus entstehenden Kosten werden gegen die bereits bezahlten Kosten gegengerechnet. Die Differenz wird im laufenden Verrechnungszeitraum gutgeschrieben.

### 2. Zähler läuft zu langsam oder steht sogar unter Last

Der Verbrauch im letzten Verrechnungszeitraum wird geschätzt und im laufenden Verrechnungszeitraum als Nachzahlung in Rechnung gestellt.

### Lösungsansätze

Damit in der Anwendung auch der Austausch von Zählern abgebildet werden kann, muss das Berechnungsmodell überarbeitet werden.
In dem aktuellen Entwurf werden für die Kostenberechnung die Differenzen der Zählerstände genutzt.

Dies ist bei einem Austausch nicht mehr möglich, ohne für die erste Rechnung nach dem Tausch einen falschen, gegebenenfalls negativen Zählerstand zu verwenden.

Das bedeutet für die Datenbankstruktur, dass Ablesungen wie Log-Einträge konstruiert werden sollten:
* Zeitstempel
* Platznummer
  * Mit der Zählernummer ist die Platznummer nicht mehr erforderlich, wenn Zähler den Plätzen zugeordnet werden.
  * Wäre es sinnvoll auch Zähler zu erfassen, die keinen Plätzen zugeordnet sind, sondern bestimmte Funktionen erfüllen?
* Zählernummer
  * Es bietet sich an hierfür eine Kennzahl zu verwenden, die fest in den Zählern hinterlegt ist und von außen lesbar ist.
* Zählerstand
* Kommentar
  * Könnte sinnvoll sein. Aktuell sehe ich dafür aber keine Notwendigkeit.

Damit können nach wie vor aus Differenzen von Zählerständen der erfasste Verbrauch ermittelt werden.
Mit der Zählernummer wird sichergestellt, dass die Zählerstände verschiedener Zähler für den gleichen Platz nicht vermischt werden.

Damit kann ein zum Beispiel ein Austausch abgebildet werden:

<table>
<thead>
<tr>
<th>Zeitstempel</th><th>Platznummer</th><th>Zählernummer</th><th>Zählerstand</th>
</tr>
</thead>
<tbody>
<tr>
<td>12.01.19</td><td>A 01</td><td>333333</td><td>8754,335</td>
</tr>
<tr>
<td>22.05.19</td><td>A 01</td><td>333333</td><td>8774,335</td>
</tr>
<tr>
<td>22.05.19</td><td>A 01</td><td>444444</td><td>0,000</td>
</tr>
<tr>
<td>12.01.20</td><td>A 01</td><td>444444</td><td>90,000</td>
</tr>
</tbody>
<tbody>
<tr>
<th colspan="3">Summe:</th><td>110,000</td>
</tr>
</tbody>
</table>

Es ist so auch möglich für einen Platz mehrere Zähler parallel abzurechnen.
Dies ist allerdings nicht vorgesehen.

### Eigene Tabelle für Zähler?

Mit einer eigenen Tabelle für Zähler können weitere Informationen abgelegt werden und Formulare mit Vorgabewerten gefüllt werden.

* Zählernummer
* Einbauzeitpunkt
* Kommentar
* Ausbauzeitpunkt

Damit können mehrere Kontrollglieder eingesetzt werden:

**Validierung des Ablesezeitpunktes:**
Es kann so geprüft werden, ob der Zähler zum Zeitpunkt der Ablesung überhaupt in Betrieb war.

**Abgleich der Zählernummern:**
Es kann so geprüft werden, ob der zurzeit installierte Zähler auch registriert wurde und dessen Zählstand verwertet werden kann.

## Problem: Benutzeroberfläche

In Hauptfenster-Mitglieder.png gibt es einen Entwurf der Benutzeroberfläche vom Hauptfenster.
Dabei wird auf Tabs als primäres Element zur Organisation nach Arbeitsbereichen zurückgegriffen.

Ich kann mich irgendwie damit nicht anfreunden.

Ich denke, ich sollte zuerst die UI-Elemente für die einzelnen Arbeitseinheiten fertigstellen.
Sie sollten in so oder so Unterfenstern dargestellt werden.
Dann kann ich mir immer noch Gedanken über das Hauptfenster machen.

### Idee: Übersicht über Berechnungszeitraum als Hauptfenster

Mit einem Dialog kann ein neuer Berechnungszeitraum angelegt oder ein vorhandener ausgewählt werden.
Es gibt in der Übersicht je eine Zeile für je einen Stromliefervertrag.

Die Daten in den jeweiligen Zeilen stammen aus Rechnungen, die im Verrechnungszeitraum gestellt wurden.
Die Daten der Rechnungen werden aus Ablesungen ermittelt und können manuell angepasst werden.

Ich weiß nur nicht, wie ich die Erfassung der Zählwerksstände in diesem Arbeitsfluss unterbringen kann, ohne dass es sich zu weit Weg vom Arbeitsfluss anfühlt.

### Lösungsansatz: Idealen Arbeitsablauf festhalten (User-Stories) 

*TODO*

**Anwendungsfälle**

* Zählerstände erfassen
  * Jahresabschluss
  * Austausch eines Zählers
* Rechnungen erstellen
  * Jahresabschluss
  * Vertragsauflösung
* Jahresabschluss (Berechnungszeitraum)
* Mitgliederdaten verwalten
  * Vertragsabschluss
  * Umzug
  * Vertragsauflösung
  
**Arbeitsablauf**

* Neuer Berechnungszeitraum anlegen
  * Start-Datum: Heute
  * End-Datum: Heute + ein Jahr
* Zählerstände erfassen
  * Liste aus Verträgen
    * Name des Abnehmers
    * Name des Platzes
    * Zählernummer
  * Datum: Heute
  * Zählerstand: Eintragen
  * Validierung:
    * Zähler muss dem Platz zugeordnet sein
    * Zähler muss zum Zeitpunkt der Ablesung in Betrieb gewesen sein
* Rechnung erstellen
  * Start-Datum und End-Datum aus aktiven Berechnungszeitraum
  * Für alle Zähler, die am Platz im Berechnungszeitraum im Betrieb waren:
    * Anfangsstand:
      * Zählerstand einer Ablesung, die nicht mehr von 7 Tagen vom Start-Datum abweicht
      * Falls nicht verfügbar, lineare Interpolation aus der letzten Ablesung vor Start-Datum und ersten Ablesung nach Start-Datum und als *geschätzt* markieren
      * Falls nicht verfügbar, keinen Wert vorgeben und als *geschätzt* markieren
    * Endstand: wie Anfangsstand, nur in Bezug zum Enddatum anstelle des Startdatums
    * Verbrauch: Aus Differenz von Anfangs- und Endstand berechnen.
  * Positionen mit Vorgabewerten füllen
    * Wenn Start-Datum des Liefervertrags im Berechnungszeitraum liegt, eine Anschlussgebühr eintragen
    * Grundgebühr: Grundrate * Anzahl (angebrochene) Monate, in denen der Vertrag läuft.
    * Alter Abschlag: Falls vorhanden, Abschlag aus der Rechnung zu diesem Vertrag aus dem vorigen Berechnungszeitraum, sonst leer.
  * Zwischenergebnisse und Endsumme berechnen
    * *TODO* Berechnung aus Excel-Tabellen übernehmen
  * Nach Sichtung und manueller Korrektur, können die Rechnungen versandbereit gemacht werden:
    * Email, als PDF-Datei im Anhang und im Body als ASCI-Art, sowie HTML
    * ePost-Brief, als HTML falls möglich
    * Ausdruck einseitig auf DIN A4
  * Nach Druck oder Versand kann eine Rechnung nicht mehr verändert werden.
  * Rechnungen können mehrfach gedruckt werden
  * Vorschau-Druck sollte nicht erforderlich sein, da die UI ein ähnliches Layout haben soll, wie die Rechnungen
* Jahresabschluss erstellen
  * Übersicht über alle Verträge an Kassenwart per Email schicken
    * Platz, Abnehmer, Verbrauch, alter Abschlag, neuer Abschlag, Anschlussgebühr, Bankeinzug, Rechnung
    * Summen in Abschlusszeile
  * Übersichten können mehrfach gedruckt werden
  * Vorschau sollte nicht erforderlich sein, da die UI ein ähnliches Layout haben soll
  * Die übersicht kann alle Rechnungen (auch nicht gesendete) enthalten
  * Drucken der Übersicht hat keinen Einfluss auf den Berechnungszeitraum
* Ein Berechnungszeitraum gilt als *offen*, solange es in diesem Zeitraum nicht gedruckte Rechnungen gibt, oder noch keine Rechnungen vorliegen
