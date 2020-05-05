# Progetto di Ingegneria del Software A.A. 2019-2020

**Docente:** Prof. Gianpaolo Cugola

**Gruppo:** GC02

**Studenti**:
- Andrea Riva (matricola: 887449)
- Alessandro Sanvito (matricola: 891196)
- Kien Tuong Truong (matricola: 887907)

## Quick start guide

Per eseguire il server (Windows):

1. Da terminale, eseguire:
```bash
java -jar target\Santorini-Server.jar
```

Per eseguire il client (Windows):

E' necessario attivare il supporto ai caratteri UTF-8 per il terminale:

1. Aprire il pannello di controllo `Area geografica` (Esegui > `intl.cpl`)
2. Nella tab `Opzioni di amministrazione`, scegliere `Cambia impostazioni locali del sistema` e abilitare la spunta `Utilizzare UTF-8 per il supporto della lingua a livello mondiale`.
3. Da terminale, eseguire:
```bash
java -Dfile.encoding=UTF8 -jar target\Santorini-Client.jar
```

### Variabili di ambiente

| Nome variabile | Descrizione | Valori consentiti |
| -------------- | ----------- | ----------------- |
| `LOG_LEVEL` | Imposta la verbosità dei messaggi di log | Tutti i valori consentiti da [java.util.logging.Level](https://docs.oracle.com/en/java/javase/14/docs/api/java.logging/java/util/logging/Level.html). Default: `INFO`. |

## Sviluppo

Il software è stato sviluppato con [Java SE 14](https://docs.oracle.com/en/java/javase/14/).

Lo strumento utilizzato per lo sviluppo è [IntelliJ Idea](https://www.jetbrains.com/idea/) 2020.1.

### Corretta visualizzazione dei colori della CLI

Per vedere in modo corretto i colori usati dalla CLI all'interno della finestra di `Run` di IntelliJ, aggiungere come `VM option` RunConfiguration della ClientApp:

```
-Djansi.passthrough=true
```
