package io.github.davidmc971.modularmsmf.core.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.bukkit.configuration.InvalidConfigurationException;
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

        testConfig = null;
        testConfig = new JSONConfiguration();
        try {
            testConfig.loadFromString(before);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }

        String after = testConfig.saveToString();
        System.out.println("[After Load] \t" + after);
        assertEquals(true, before.equals(after));
    }
}