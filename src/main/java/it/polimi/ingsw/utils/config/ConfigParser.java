package it.polimi.ingsw.utils.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This singleton, when instantiated, reads and stores all configuration variables.
 * Values should be retrieved by getConfiguration().
 */
public class ConfigParser {
    private static final Logger LOGGER = Logger.getLogger(ConfigParser.class.getName());
    //NOTE: For now, configs should only be loaded at startup. No hot-reload, as this leads
    //to possible desync issues

    private static ConfigParser instance;
    private final Properties configs;

    /**
     * Constructor for the class.
     * Being a singleton, this class should be instantiated via getInstance().
     */
    private ConfigParser(){
        configs = new Properties();
        for(ConfigFile c: ConfigFile.values()){
            loadConfig(c);
        }
    }

    /**
     * This method reads a properties configuration file, storing all read variables into configs
     *
     * @param cfg the ConfigFile instance that represents that file that is to be read
     */
    private void loadConfig(ConfigFile cfg){
        try {
            configs.load(new FileInputStream(cfg.path));
        } catch (IOException e){
            LOGGER.log(Level.SEVERE, String.format("Unable to read configuration file from %s%nPlease, make sure it is available or set the %s env var appropriately.", cfg.path, ConfigFile.CONFIG_BASE_PATH_ENV_VAR));
            System.exit(1);
        }
    }

    /**
     * Reads the value bound to the specified key from the configuration.
     *
     * @param key the name of the property to be retrieved
     * @return the value bound to the property
     * @throws IllegalArgumentException if the specified key does not exist
     */
    public String getProperty(String key) {
        String property = configs.getProperty(key);
        if(property == null){
            throw new IllegalArgumentException("Key does not exist");
        } else {
            return property;
        }
    }

    /**
     * Reads the value bound to the specified key from the configuration, casting it to an int.
     *
     * @param key the name of the property to be retrieved
     * @return the value bound to the property
     * @throws IllegalArgumentException if the specified key does not exist
     */
    public int getIntProperty(String key) throws IllegalArgumentException {
        return Integer.parseInt(getProperty(key));
    }

    /**
     * This method allows to retrieve the instance of ConfigParser, if it exists, otherwise it creates one
     * and returns the newly created instance
     *
     * @return the ConfigParser singleton instance
     */
    public static ConfigParser getInstance(){
        if(instance == null){
            instance = new ConfigParser();
        }
        return instance;
    }
}
