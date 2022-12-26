package pet.project.spark.util.config;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pet.project.spark.Main;
import pet.project.spark.model.config.Config;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ConfigManager {
    private static Logger LOG = LoggerFactory.getLogger(ConfigManager.class);
    private static final Gson gson = new Gson();
    public static Config load(String filePath) throws Exception {
        try {
            InputStream is = getFileFromResourcesAsStream(filePath);
            String json = getStringFromStream(is);
            return gson.fromJson(json, Config.class);
        } catch (Exception e) {
           LOG.error("error loading config file into class. err: " + e);
           throw e;
        }
    }

    private static InputStream getFileFromResourcesAsStream(String filePath) {
        ClassLoader classLoader = ConfigManager.class.getClassLoader();
        InputStream is = classLoader.getResourceAsStream(filePath);

        if (is == null) {
            throw new IllegalArgumentException("config file not found. filePath: " + filePath);
        } else {
            return is;
        }
    }

    private static String getStringFromStream(InputStream is) throws IOException {
        try {
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            for (int length; (length = is.read(buffer)) != -1;) {
                result.write(buffer, 0, length);
            }
            return result.toString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOG.error("error converting input stream into string. err: " + e);
            throw e;
        }
    }
}
