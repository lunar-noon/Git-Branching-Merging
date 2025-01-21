# Projekt Pitch:

Eine minimalistische Web-App, wo der Nutzer schnell und unkompliziert Artikel erstellen, bearbeiten und veröffentlichen können, die zusätzlich als Notiz App genutzt werden kann.

## Beschreibung von die Idee:

Ein Benutzer hat ein Konto, von dem aus Artikel erstellt werden. Theoretisch werden dafür nur zwei Tabellen benötigt: eine für Benutzerkonten und eine für Artikel. Zusätzliche Tabellen können hinzugefügt werden. Die Artikel werden so angezeigt, sodass es ersichtlich ist, welcher Benutzer sie publiziert hat.


### Projektbeschreibung: Minimalistische Web-App für Artikelverwaltung

## **User Stories**

### **User Story 1**
**„Als angemeldeter Benutzer möchte ich neue Artikel erstellen und veröffentlichen können, um meine Gedanken oder Notizen schnell zu speichern.“**

#### **Akzeptanzkriterien**
1. Der Benutzer muss eingeloggt sein, um einen Artikel erstellen zu können.
2. Auf der Startseite oder in der Naavbar gibt es einen Button, der den Benutzer zu einem Post Page weiterleitet.
3. Die Artikel enthalten folgende Felder:
   - **Titel**
   - **Inhalt**
4. Der Benutzer kann einen Artikel veröffentlichen und anschliessend bearbeiten oder löschen.
5. Der Benutzer kann optional eine Kategorie auswählen.
6. Nach dem Veröffentlichen wird der Artikel auf der öffentlichen Seite angezeigt, inklusive:
   - Titel des Artikels
   - Name des Benutzers, der den Artikel veröffentlicht hat
   - Veröffentlichungsdatum
7. Besucher können auf einen Artikel klicken, um die vollständigen Details anzuzeigen.

---

### **User Story 2**
**„Als Besucher möchte ich eine intuitive UI haben, damit ich mich einfach anmelden kann.“**

#### **Akzeptanzkriterien**
1. Die Startseite oder Navbar enthält einen Login Button.
2. Das Anmeldeformular enthält folgende Funktionen:
   - **E-Mail-Verifizierung:** Die eingegebene E-Mail-Adresse wird validiert.
   - **Passwort-Eingabe:** Ein Passwort ist erforderlich, um sich einzuloggen.
   - **Kein Benutzername erforderlich:** Besucher können sich nur mit ihrer E-Mail-Adresse anmelden.



# Arbeitsschritte und Zeitschätzung

## Tabelle der Arbeitsschritte

| **Schritt**                         | **Beschreibung**                                                                                  | **Zeitschätzung** |
|-------------------------------------|--------------------------------------------------------------------------------------------------|--------------------|
| Projekt-Setup & Idee                    | Initiales Setup der Web-App mit grundlegenden Tools und Frameworks                              | 20 min           |
| User Stories und Akzeptanzkriterien     | Realistische Stories mit erreichbaren Zielen                                                      | 30 min            |
| Datenbankstruktur und Logik            | Tabellen für Benutzerkonten und Artikel erstellen                                               | 20 min            |
| Web-App inkl. Testing               | Gesamte Web-App mit benutzerfreundlichem UI                                                       | 5 h               |
| Dokumentation                        | Intuitive Navigation und grundlegendes Styling                                                  | 1 h               |
| Deployment                          | Bereitstellung der App auf einem Server oder Hosting-Dienst                                     | 20 min            |

**Gesamtdauer:** 7,4 h


# Projektübersicht

| **Schritt**                         | **Beschreibung**                                                                                  | **Zeitschätzung** |
|-------------------------------------|--------------------------------------------------------------------------------------------------|--------------------|
| **Projekt-Setup & Idee**            | Die Idee und das allgemeine Ziel konnten relativ schnell definiert werden.                        | Die Suche nach der Idee hat bei mir etwas länger gedauert als erwartet. |
| **User Stories und Akzeptanzkriterien** | Ich habe nur grundlegende Aspekte der App mit User Stories beschrieben.                            | Nicht alle User Stories konnten umgesetzt werden, z. B. Punkte 5 und 7 der ersten User Story. Der Grund dafür war meine falsche Einschätzung des Zeitplans. |
| **Datenbankstruktur und Logik**     | Gegen Ende des Projekts musste ich die Datenbankstruktur leicht ändern, da ich die Architektur von Supabase missverstanden hatte.        |                    |
| **Web-App inkl. Testing**           | Ich habe hier relativ viel Zeit verloren, weil ich die App anders gestalten wollte. Aufgrund einiger Pakete musste ich das Projekt jedoch komplett neu beginnen. |                    |
| **Dokumentation**                   | Die Dokumentation konnte ich nicht rechtzeitig abschliessen und musste sie daher am Ende nachholen. |                    |
| **Deployment**                      | Alle Features, wie Login, Erstellung, Löschung und Aktualisierung von Artikeln, funktionieren und unterscheiden sich je nach Nutzer. | Die Grundziele wurden erreicht. |

![alt text](<Screenshot 2025-01-21 174703.png>)

## Die Architektur des Projekts

Das Ziel war, eine serverlose Fullstack-App zu entwickeln, die einfach nachvollziehbar ist. Das Frontend wurde nicht mit einem kompletten React-Framework, sondern mit einer minimalen und reinen Implementierung in React JS erstellt. Als Datenbank wurde SQL verwendet.  

## Deployment

Um die App zu starten, müssen folgende Schritte durchgeführt werden:  
1. Die benötigten Pakete installieren.  
2. Die Tabellen in Supabase erstellen.  
3. Die RLS (Row Level Security) für die `articles`-Tabelle deaktivieren.  
4. Eine Policy für Anfragen konfigurieren, die wie folgt definiert ist:  
   ```sql
   (user_id = auth.uid())
   ```
5. Vergessen Sie nicht, Ihren Key und die URL von Supabase einzugeben.


## kritischer Review

Das Projekt konnte ich zwar abschliessen, allerdings erst in letzter Minute. Daher denke ich, dass meine Zeitplanung fehlerhaft war und beim nächsten Mal mehr Beachtung finden sollte. Einige Features konnte ich aufgrund von Zeitmangel nicht umsetzen, und für zusätzliche Ideen blieb ebenfalls keine Zeit. Dennoch funktioniert das Projekt vollständig. Einige Aspekte könnten jedoch bei einer zukünftigen Planung verbessert werden.

