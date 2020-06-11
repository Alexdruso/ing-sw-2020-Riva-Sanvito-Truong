# Software Engineering Project A.A. 2019-2020
**Progetto di Ingegneria del Software A.A. 2019-2020**

**Professor:** Prof. Gianpaolo Cugola

**Group:** GC02

**Students**:
- Andrea Riva (immatriculation number: 887449)
- Alessandro Sanvito (immatriculation number: 891196)
- Kien Tuong Truong (immatriculation number: 887907)

## Quick start guide

### Windows

To run the server:

1. In a terminal window, run:
```bash
Santorini-Server.bat
```

To run the client:

You need to enable the support to UTF-8 characters in the terminal:

1. Open the `Area geografica` control panel (Run > `intl.cpl`)
2. In the `Opzioni di amministrazione` tab, choose `Cambia impostazioni locali del sistema` and check `Utilizzare UTF-8 per il supporto della lingua a livello mondiale`.
3. In a terminal window, run:

**For the GUI:**
```bash
Santorini-Client.bat
```

**For the CLI:**
```bash
Santorini-Client.bat cli
```

### Linux / MacOS

To run the server:

1. In a terminal window, run:
```bash
./Santorini-Server.sh
```

To run the client:

1. In a terminal window, run:

**For the GUI:**
```bash
./Santorini-Client.sh
```

**For the CLI:**
```bash
./Santorini-Client.sh cli
```

### Environment variables

| Name | Description | Allowed values |
| -------------- | ----------- | ----------------- |
| `CLI_INPUT_FILE` | Uses the specified file as source for the input to the CLI | A relative or absolute file path. Default: *empty (stdin is used for input)* |
| `CLI_LOG_INPUTS_FOLDER` | The folder in which to log all the inputs from the CLI | A relative or absolute folder path, including the trailing `/`. Default: *empty (do not log inputs)* |
| `LANGUAGE` | Sets the desired language for the user interface | `en`, `it`. Default: *the system default language* |
| `LOG_LEVEL` | Sets the log messages verbosity | All the values specified in [java.util.logging.Level](https://docs.oracle.com/en/java/javase/14/docs/api/java.logging/java/util/logging/Level.html). Default: `INFO` |

## Development

The software has been written using [Java SE 14](https://docs.oracle.com/en/java/javase/14/).

The IDE used for the development is [IntelliJ Idea](https://www.jetbrains.com/idea/) 2020.1.

### How to correctly view CLI colors in IntelliJ

To correctly see the colors in the CLI version of the client in the `Run` window of IntelliJ, add this `VM option` in the RunConfiguration of ClientApp:

```
-Djansi.passthrough=true
```

Please, be aware that some CLI features (such as the screen blanking or the positioning of some parts of the user interface) can't be rendered correctly in the `Run` window of IntelliJ. 
The `Run` window of IntelliJ is meant just for debugging and testing purposes. 
Run the compiled JAR in a terminal to benefit from the full experience.
