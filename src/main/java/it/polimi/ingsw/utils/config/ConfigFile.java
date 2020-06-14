package it.polimi.ingsw.utils.config;

import java.nio.file.Paths;

/**
 * This enum holds the paths of all config files
 */
public enum ConfigFile {
    GLOBAL_CONFIG("global.properties"),
    SERVER_CONFIG("server.properties");

    public final String path;
    public static final String CONFIG_BASE_PATH_ENV_VAR = "CONFIG_BASE_PATH";
    public static final String CONFIG_BASE_PATH_DEFAULT = "config";

    /**
     * Enum constructor
     *
     * @param path The relative path from the project root to the configuration file
     */
    ConfigFile(String path) {
        String basePath = System.getenv(CONFIG_BASE_PATH_ENV_VAR);
        if (basePath == null) {
            basePath = CONFIG_BASE_PATH_DEFAULT;
        }
        this.path = Paths.get(basePath, path).toString();
    }


}
