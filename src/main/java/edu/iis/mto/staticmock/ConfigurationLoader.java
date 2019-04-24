package edu.iis.mto.staticmock;

import java.io.File;

import org.codehaus.jackson.map.ObjectMapper;

public class ConfigurationLoader {

    private static final String FILE_EXT = "json";
    private static final String MTO_CONF_PATH = "mtoConfPath";
    private static ConfigurationLoader instance;

    private ConfigurationLoader() {
    }

    public Configuration loadConfiguration() {

        String configurationDir = System.getProperty(MTO_CONF_PATH);
        StringBuilder stringBuilder = new StringBuilder(configurationDir);
        stringBuilder.append(Configuration.class.getSimpleName()).append(FILE_EXT);
        try {
            File file = new File(stringBuilder.toString());

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(file, Configuration.class);
        } catch (Exception e) {
            throw new ConfigurationException(e);
        }

    }

    public static ConfigurationLoader getInstance() {

        if (instance == null) {
            instance = new ConfigurationLoader();
        }
        return instance;
    }
}
