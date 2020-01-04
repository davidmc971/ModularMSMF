package io.github.davidmc971.modularmsmf.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;

public class JSONConfiguration extends FileConfiguration {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String saveToString() {
        String header = buildHeader();
        String dump = "";

        Map<String, Object> mSectionMap = convertMemorySections(getValues(false));

        try {
            dump = objectMapper.writeValueAsString(mSectionMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return header + dump;
    }

    private Map<String, Object> convertMemorySections(Map<String, Object> map) {
        for (Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() instanceof MemorySection)
                map.put(
                    entry.getKey(),
                    convertMemorySections((Map<String, Object>)(((MemorySection)entry.getValue()).getValues(false)))
                );
        }
        return map;
    }

    @Override
    public void loadFromString(String contents) throws InvalidConfigurationException {
        Validate.notNull(contents, "Contents cannot be null");

        // System.out.println("CONTENT:\n" + contents);

        Map<?, ?> input = null;

        if (!contents.equals("")) {
            try {
                input = objectMapper.readValue(contents, Map.class);
            } catch (Exception e) {
                throw new InvalidConfigurationException(e);
            }
        } else {
            createSection("root");
        }

        // String header = parseHeader(contents);
        // if (header.length() > 0) {
        //     options().header(header);
        // }

        if (input != null) {
            convertMapsToSections(input, this);
        }

    }
    
    protected void convertMapsToSections(Map<?, ?> input, ConfigurationSection section) {
        for (Map.Entry<?, ?> entry : input.entrySet()) {
            String key = entry.getKey().toString();
            Object value = entry.getValue();

            if (value instanceof Map) {
                convertMapsToSections((Map<?, ?>) value, section.createSection(key));
            } else {
                section.set(key, value);
            }
        }
    }

    public static JSONConfiguration loadConfiguration(File file) {
        Validate.notNull(file, "File cannot be null");

        JSONConfiguration config = new JSONConfiguration();

        try {
            config.load(file);
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Cannot load " + file, ex);
        } catch (InvalidConfigurationException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Cannot load " + file, ex);
        }

        return config;
    }

    @Override
    protected String buildHeader() {
        return "";
    }
}