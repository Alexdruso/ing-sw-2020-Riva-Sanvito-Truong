# Progetto di Ingegneria del Software A.A. 2019-2020

**Docente:** Prof. Gianpaolo Cugola

**Gruppo:** GC02

**Studenti**:
- Andrea Riva (matricola: 887449)
- Alessandro Sanvito (matricola: 891196)
- Kien Tuong Truong (matricola: 887907)

## Sviluppo

Lo strumento utilizzato per lo sviluppo del software è IntelliJ Idea 2019.3.

### Corretta visualizzazione dei colori della CLI

Per vedere in modo corretto i colori usati dalla CLI all'interno della finestra di `Run` di IntelliJ, aggiungere come `VM option` RunConfiguration della ClientApp:

```
-Djansi.passthrough=true
```
