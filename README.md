# Software Engineering Project A.A. 2019-2020
**Progetto di Ingegneria del Software A.A. 2019-2020**

**Professor:** Prof. Gianpaolo Cugola

**Group:** GC02

**Students**:
- Andrea Riva (immatriculation number: 887449)
- Alessandro Sanvito (immatriculation number: 891196)
- Kien Tuong Truong (immatriculation number: 887907)

## Implemented features

| Feature | Implemented |
| ------- | ----------- |
| All the rules ("Regole complete") | :heavy_check_mark: |
| CLI | :heavy_check_mark: |
| GUI | :heavy_check_mark: |
| Socket | :heavy_check_mark: |
| Advanced functionality 1 (FA 1) | :heavy_check_mark: Multiple matches ("Partite multiple") |
| Advanced functionality 2 (FA 2) | :heavy_check_mark: Advanced Gods ("Divinità avanzate") |

## Test coverage

# TODO

Refer to the [deliverables/report/](deliverables/report/) folder for further details.

## Compile

To run the tests and compile the software:

1. Install [Java SE 14](https://docs.oracle.com/en/java/javase/14/)
2. Install [Maven](https://maven.apache.org/install.html)
3. Clone this repo
4. In the cloned repo folder, run:
```bash
mvn package
```
5. The compiled artifact (`Santorini.jar`) will be inside the `target` folder.

## Quick start guide

The following commands are meant to be run inside the [deliverables/](`deliverables/`) folder.

### Windows

#### To run the server:

1. In a terminal window, run:
```bash
Santorini-Server.bat
```

#### To run the client:

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

#### To run the server:

1. In a terminal window, run:
```bash
./Santorini-Server.sh
```

#### To run the client:

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
| `CONFIG_BASE_PATH` | The folder in which the program will look for properties file (See the [Configuration](#Configuration) section)| A relative or absolute folder path, including the trailing `/`. Default: *empty (use default configuration values)* |

### Configuration

Some configurations can be overridden by copying the `config` folder of this repository on your system and by setting the `CONFIG_BASE_PATH` environment variable to point to that folder.

The configurations inside the folder will override **all** the default values. Remember to remove the `.example` extension from the file names.

#### global.properties

Miscellaneous properties required by both Server and Client

| Name | Description | Type | Default Value |
| -------------- | ----------- | ----------------- | ----- |
| `projectName` | The name of the project. Used as a MOTD in the logs.| String | Santorini |
| `version` | The current version of the project. Used as a MOTD in the logs.| String | N/A |
| `authors` | The authors of the project| String| A. Riva, A. Sanvito & K. T. Truong |
| `keepAliveIntervalMs` | The time in millisecond that should pass between each keepAlive message sent on the connection | Integer| 15000 |
| `nicknameMaxLength` | The maximum length allowed for a player's nickname| Integer| 30 |

#### server.properties

Server related properties

| Name | Description | Type| Default Value |
| -------------- | ----------- | ----------------- | ----- |
| `serverPort` | The port on which the server should listen for inbound connections| Integer | 7268 |
| `numberOfThreads` | The number of threads allowed to run at the same time to handle concurrent matches| Integer | 32 |

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
