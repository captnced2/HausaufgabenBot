package org.config;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static org.config.Config.createFile;
import static org.main.Variables.mainConfPath;
import static org.values.Global.*;
import static org.values.strings.Errors.*;

public abstract class ConfigFile {

    final String configFileName;
    final String configFilePath;
    final File configFile;

    protected ConfigFile(String fileName) {
        configFileName = fileName;
        configFilePath = mainConfPath + configFileName;
        configFile = new File(configFilePath);
        if (!configFile.exists()) {
            createFile(configFile);
        }
    }

    public String getConfigFileName() {
        return configFileName;
    }

    protected String getConfigFilePath() {
        return configFilePath;
    }

    protected String[] getLines() {
        ArrayList<String> lines = new ArrayList<>();
        String line;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(configFile), StandardCharsets.UTF_8));
            while ((line = br.readLine()) != null) {
                if (!line.startsWith(commentSymbol)) {
                    lines.add(line);
                }
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] arr = new String[lines.size()];
        return lines.toArray(arr);
    }

    public String getKey(String key) {
        String[] lines = getLines();
        if (lines.length == 0 || key.isEmpty()) {
            throw new RuntimeException(configFileEmptyError(getConfigFileName()));
        }
        for (String line : lines) {
            String[] split = line.split(keySeperator);
            String lineKey = split[0];
            if (lineKey.equals(key)) {
                return split[1];
            }
        }
        throw new RuntimeException(noKeyValueError(key, getConfigFileName()));
    }
}
