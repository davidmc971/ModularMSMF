package io.github.davidmc971.modularmsmf.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.jupiter.api.Test;

public class TestJSONConfiguration {

    @Test
    public void Test() {
        JSONConfiguration testConfig = new JSONConfiguration();
        testConfig.set("teststring", "teststring");
        testConfig.set("testinteger", 123);
        testConfig.set("testboolean", true);
        testConfig.createSection("testsection");
        testConfig.set("testsection.testbloop", "bloop");
        testConfig.createSection("testsection.bloopsection");
        testConfig.set("testsection.bloopsection.foo", "bar");

        
        String before = testConfig.saveToString();
        System.out.println("[Before Save] \t" + before);

        File f = new File("testconfig.json");

        try {
            testConfig.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        testConfig = null;
        testConfig = JSONConfiguration.loadConfiguration(f);

        String after = testConfig.saveToString();
        System.out.println("[After Load] \t" + after);
        assertEquals(true, before.equals(after));
    }
}