package org.config.files;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static org.values.Global.*;

public abstract class ConfigFile {
    protected final String[] lines;

    ConfigFile(String file) {
        ArrayList<String> lines = new ArrayList<>();
        String line;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
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
        this.lines = lines.toArray(arr);
    }

    public String getKey(String key) {
        if (lines.length == 0 || key.isEmpty()) {
            return null;
        }
        for (String line : lines) {
            String[] split = line.split(keySeperator);
            String lineKey = split[0];
            if (lineKey.equals(key)) {
                return split[1];
            }
        }
        return null;
    }
}
