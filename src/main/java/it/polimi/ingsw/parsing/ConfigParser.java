package it.polimi.ingsw.parsing;

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
     * @throws IOException if the path specified in cfg does not lead to a valid file
     */
    private void loadConfig(ConfigFile cfg){
        try {
            configs.load(new FileInputStream(cfg.path));
        } catch (IOException e){
            System.out.println("Could not read from " + cfg.path);
        }
    }

    /**
     * This method encapsulates the getProperty function on configs to make it read-only
     * @param key the name of the property to be retrieved
     * @return the value of the property
     */
    public String getProperty(String key){
        return configs.getProperty(key);
    }

    /**
     * This method allows to retrieve the instance of ConfigParser, if it exists, otherwise it creates one
     * and returns the newly created instance
     * @return the ConfigParser singleton instance
     */
    public static ConfigParser getInstance(){
        if(instance == null){
            instance = new ConfigParser();
        }
        return instance;
    }
}
