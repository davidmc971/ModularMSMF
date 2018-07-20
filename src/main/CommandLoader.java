package main;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;

import com.google.common.reflect.ClassPath;

import commands.AbstractCommand;

public class CommandLoader {
	ModularMSMF plugin;
	
	public CommandLoader (ModularMSMF plugin) {
		this.plugin = plugin;
	}
	
	public ArrayList<AbstractCommand> loadCommands(ClassLoader classLoader){
		ArrayList<AbstractCommand> commandList = new ArrayList<AbstractCommand>();
		
		String packageName = "commands";

		plugin.getLogger().info(classLoader.toString());
		plugin.getLogger().info(classLoader.getClass().getName());
		
		ClassPath path = null;
		try {
			path = ClassPath.from(classLoader);
			plugin.getLogger().info("path: " + path.toString());
			for(ClassPath.ClassInfo info : path.getAllClasses()) {
				plugin.getLogger().info("info: " + info.getName() + " packageName: " + info.getPackageName());
			}
		} catch (IOException e1) {
			plugin.getLogger().severe(e1.toString());
		}
		
		plugin.getLogger().info("Next section");
		
		if(path != null) {
			for (ClassPath.ClassInfo info : path.getTopLevelClassesRecursive(packageName)) {
				plugin.getLogger().info("info: " + info.getName() + " packageName: " + info.getPackageName());
				if(!info.getName().equals("commands.AbstractCommand")) {
					try {
						Class<?> clazz = Class.forName(info.getName(), true, classLoader);
						commandList.add((AbstractCommand)clazz.getConstructor(plugin.getClass()).newInstance(plugin));

					} catch (Exception e) {
						plugin.getLogger().severe(e.toString());
					}
				}
			}
		}

		{
			String temp = "";
			for (AbstractCommand cmd : commandList) {
				temp += cmd.getCommandLabel() + ", ";
			}
			
			try {
				plugin.getLogger().info("Commands [" + temp.substring(0, temp.length() - 2) + "] loaded!");
			} catch (Exception e) {
				plugin.getLogger().severe("Something seems to be not right with commands!");
				//plugin.getLogger().severe("Using secondary Loader.");
				plugin.getLogger().severe(e.toString());
			}
		}
		
		if (commandList.isEmpty()) {
			plugin.getLogger().severe("Main Command loading did not work, using backup loader.");
			commandList.clear();
			commandList = loadCommandsFallback();
		}
		
		return loadCommandsFallback();
	}

	private ArrayList<AbstractCommand> loadCommandsFallback() {
		
		
		return new ArrayList<AbstractCommand>();
	}
}
