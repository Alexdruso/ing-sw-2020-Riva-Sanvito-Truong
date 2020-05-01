# Progetto di Ingegneria del Software A.A. 2019-2020

**Docente:** Prof. Gianpaolo Cugola

**Gruppo:** GC02

**Studenti**:
- Andrea Riva (matricola: 887449)
- Alessandro Sanvito (matricola: 891196)
- Kien Tuong Truong (matricola: 887907)

## Quick start guide

Per eseguire il server (Windows):
```bash
java -jar target\Santorini-Server.jar
```

Per eseguire il client (Windows):
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
