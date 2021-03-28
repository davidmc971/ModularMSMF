package io.github.davidmc971.modularmsmf.economy;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class ModularMSMFEconomy extends JavaPlugin {
    public ModularMSMFEconomy() {
        try {
            Map<String, Object> test = new HashMap<>();
            test.put("description", "we are l33t");
            test.put("usage", "asdasdasdasd");
            Map<String, Map<String, Object>> bled = new HashMap<>();
            bled.put("eco", test);

            Field descriptionField = JavaPlugin.class.getDeclaredField("description");
            descriptionField.setAccessible(true);
            PluginDescriptionFile pldf = (PluginDescriptionFile) descriptionField.get(this);

            Field commandsField = PluginDescriptionFile.class.getDeclaredField("commands");
            commandsField.setAccessible(true);

            this.getLogger().info("description: " + pldf);
            this.getLogger().info("commands: " + commandsField.get(pldf));
            commandsField.set(pldf, bled);
            this.getLogger().info("commands: " + commandsField.get(pldf));

            descriptionField.setAccessible(false);
            commandsField.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        this.getLogger().info("Constructor - Hello");
        this.getDescription().getCommands().forEach((k, v) -> {
            this.getLogger().info("k: " + k + " v:" + v);
        });
    }

    @Override
    public void onEnable() {
        this.getLogger().info("onEnable() - Hello");
    }
}
