```
Bukkit.getConsoleSender().sendMessage(Component.text(plugin.getLanguageManager().getStandardLanguage()
	.getString("event.welcome").replaceAll("_var", player.getDisplayName())));
```

Mit Component arbeiten REEEEEEEE