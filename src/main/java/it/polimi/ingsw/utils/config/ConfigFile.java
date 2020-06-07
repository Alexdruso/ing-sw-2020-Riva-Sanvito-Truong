package it.polimi.ingsw.utils.config;

/**
 * This enum holds the paths of all config files
 */
public enum ConfigFile {
    GLOBAL_CONFIG("config/global.properties"),
    SERVER_CONFIG("config/server.properties");

    public final String path;

    /**
     * Enum constructor
     *
     * @param path The relative path from the project root to the configuration file
     */
    ConfigFile(String path) {
        this.path = path;
    }


}
