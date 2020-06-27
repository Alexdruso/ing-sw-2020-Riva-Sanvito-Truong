package it.polimi.ingsw.utils.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This enum holds the paths of all config files
 */
public enum ConfigFile {
    /**
     * The entry representing the file for global properties
     */
    GLOBAL_CONFIG("global.properties"),
    /**
     * The entry representing the file for server properties
     */
    SERVER_CONFIG("server.properties");

    private InputStream inputStream;
    static final String CONFIG_BASE_PATH_ENV_VAR = "CONFIG_BASE_PATH";
    static final String CONFIG_BASE_PATH_DEFAULT = "/config/default_";

    /**
     * Enum constructor
     *
     * @param path The relative path from the project root to the configuration file
     */
    ConfigFile(String path) {
        String basePath = System.getenv(CONFIG_BASE_PATH_ENV_VAR);
        if (basePath == null) {
            this.inputStream = ConfigFile.class.getResourceAsStream(CONFIG_BASE_PATH_DEFAULT + path);
        } else {
            try {
                this.inputStream = new FileInputStream(Paths.get(basePath, path).toString());
            } catch (IOException e){
                Logger.getLogger(ConfigFile.class.getName()).log(
                        Level.SEVERE,
                        String.format(
                                "Unable to read configuration file from %s%nPlease, make sure it is available or set the %s env var appropriately.",
                                Paths.get(basePath, path),
                                ConfigFile.CONFIG_BASE_PATH_ENV_VAR
                        )
                );
                System.exit(1);
            }
        }
    }

    /**
     * This method is used to retrieve the inputStream for the configuration file of the Enum
     * @return an InputStream instance that is connected to the configuration file
     */
    public InputStream getInputStream(){
        return this.inputStream;
    }


}
