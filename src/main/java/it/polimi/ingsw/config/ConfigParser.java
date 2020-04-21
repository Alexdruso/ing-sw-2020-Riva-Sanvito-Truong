package it.polimi.ingsw.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This singleton, when instantiated, reads and stores all configuration variables.
 * Values should be retrieved by getConfiguration().
 */
public class ConfigParser {
    //NOTE: For now, configs should only be loaded at startup. No hot-reload, as this leads
    //to possible desync issues

    private static ConfigParser instance;
    private Properties configs;

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
     * @param cfg the ConfigFile instance that represents that file that is to be read
     */
    private void loadConfig(ConfigFile cfg){
        try {
            configs.load(new FileInputStream(cfg.path));
        } catch (IOException e){
            System.out.println("Could not read from " + cfg.path);
        }
    }

    /**
     * Reads the value bound to the specified key from the configuration.
     *
     * @param key the name of the property to be retrieved
     * @return the value bound to the property
     * @throws IllegalArgumentException if the specified key does not exist
     */
    public String getProperty(String key) throws IllegalArgumentException{
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
