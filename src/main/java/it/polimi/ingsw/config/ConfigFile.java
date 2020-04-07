package it.polimi.ingsw.config;

/**
 * This enum holds the paths of all config files
 */
public enum ConfigFile {
    GLOBAL_CONFIG("config/global.properties");

    public final String path;

    /**
     * Enum constructor
     * @param path The relative path from the project root to the configuration file
     */
    private ConfigFile(String path) {
        this.path = path;
    }


}
