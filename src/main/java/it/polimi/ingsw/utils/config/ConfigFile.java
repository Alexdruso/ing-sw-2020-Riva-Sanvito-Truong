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
    GLOBAL_CONFIG("global.properties"),
    SERVER_CONFIG("server.properties");

    private InputStream inputStream;
    public static final String CONFIG_BASE_PATH_ENV_VAR = "CONFIG_BASE_PATH";
    public static final String CONFIG_BASE_PATH_DEFAULT = "/config/default_";

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

    public InputStream getInputStream(){
        return this.inputStream;
    }


}
