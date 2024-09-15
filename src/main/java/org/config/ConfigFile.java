package org.config;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static org.config.ConfigManager.writeConfigTemplate;
import static org.main.Variables.mainConfPath;
import static org.values.Global.*;
import static org.values.strings.Console.*;
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
            writeConfigTemplate(configFilePath, getTemplate());
        }
    }

    protected abstract String getTemplate();

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

    private void createFile(File file) {
        if (!file.exists()) {
            sendCreatingNewFile(file.getName());
            try {
                boolean success = file.createNewFile();
                if (!success) {
                    sendCantCreateConfError(file.getName());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String getKey(String key, boolean returnNull) {
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
        if (returnNull) {
            return null;
        } else {
            throw new RuntimeException(noKeyValueError(key, getConfigFileName()));
        }
    }

    public boolean setKeyWithSeperator(String key, String value, String keySeperator, boolean overwrite) {
        try {
            String[] lines = getLines();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getConfigFilePath()), StandardCharsets.UTF_8));
            String completeLine = key + keySeperator + value;
            boolean isNew = true;
            for (String line : lines) {
                if (line.startsWith(commentSymbol)) {
                    writer.write(line);
                }
                if (line.split(keySeperator)[0].equals(key)) {
                    isNew = false;
                    if (overwrite) {
                        line = completeLine;
                    } else {
                        line = line + commaRegex + value;
                    }
                }
                writer.write(line + newLine);
            }
            if (isNew) {
                writer.write(completeLine + newLine);
            }
            writer.close();
            return isNew;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean setKey(String key, String value, boolean overwrite) {
        return setKeyWithSeperator(key, value, keySeperator, overwrite);
    }

    @SuppressWarnings("unused")
    public boolean setKey(String key, String value) {
        return setKey(key, value, true);
    }

    public String getKey(String key) {
        return getKey(key, false);
    }
}
