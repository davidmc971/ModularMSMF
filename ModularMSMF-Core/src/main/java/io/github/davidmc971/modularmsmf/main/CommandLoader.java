package io.github.davidmc971.modularmsmf.main;

import java.io.IOException;
import java.util.ArrayList;

import com.google.common.reflect.ClassPath;

import io.github.davidmc971.modularmsmf.commands.*;
import io.github.davidmc971.modularmsmf.eco.EconomySystem;
import io.github.davidmc971.modularmsmf.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;

/**
 * 
 * @author davidmc971
 *
 */

public class CommandLoader {
	ModularMSMFCore plugin;
	
	public CommandLoader (ModularMSMFCore plugin) {
		this.plugin = plugin;
	}
	
	public ArrayList<IModularMSMFCommand> loadCommands(ClassLoader classLoader){
		ArrayList<IModularMSMFCommand> commandList = new ArrayList<IModularMSMFCommand>();
		
		String packageName = "commands";

		plugin.getLogger().info(classLoader.toString());
		plugin.getLogger().info(classLoader.getClass().getName());
		
		ClassPath path = null;
		try {
			path = ClassPath.from(classLoader);
			//plugin.getLogger().info("path: " + path.toString());
			//for(ClassPath.ClassInfo info : path.getAllClasses()) {
				//plugin.getLogger().info("info: " + info.getName() + " packageName: " + info.getPackageName());
			//}
		} catch (IOException e1) {
			plugin.getLogger().severe(e1.toString());
		}
		
		//plugin.getLogger().info("Next section");
		
		if(path != null) {
			for (ClassPath.ClassInfo info : path.getTopLevelClassesRecursive(packageName)) {
				//plugin.getLogger().info("info: " + info.getName() + " packageName: " + info.getPackageName());
				if(!info.getName().equals("commands.AbstractCommand")) {
					try {
						Class<?> clazz = Class.forName(info.getName(), true, classLoader);
						commandList.add((IModularMSMFCommand)clazz.getConstructor(plugin.getClass()).newInstance(plugin));
					} catch (Exception e) {
						plugin.getLogger().severe(e.toString());
					}
				}
			}
		}
		
		if (commandList.isEmpty()) {
			plugin.getLogger().severe("Main Command loading did not work, using backup loader.");
			commandList.clear();
			commandList = loadCommandsFallback();
		}
		
		{
			String temp = "";
			for (IModularMSMFCommand cmd : commandList) {
				for(String s : cmd.getCommandLabels()) {
					temp += s + ", ";
				}
			}
			
			try {
				plugin.getLogger().info("Commands [" + temp.substring(0, temp.length() - 2) + "] loaded!");
			} catch (Exception e) {
				plugin.getLogger().severe("Something seems to be not right with commands!");
				plugin.getLogger().severe(e.toString());
			}
		}
		
		return commandList;
	}

	private ArrayList<IModularMSMFCommand> loadCommandsFallback() {
		//Reflections reflections = new Reflections("com.mycompany");    
		//Set<Class<? extends MyInterface>> classes = reflections.getSubTypesOf(MyInterface.class);
		ArrayList<IModularMSMFCommand> commandList = new ArrayList<IModularMSMFCommand>();
		
		for(IModularMSMFCommand cmd : new IModularMSMFCommand[] {
				new CommandBan(),
				new CommandBack(),
				new CommandFeed(),
				new CommandHeal(),
				new CommandHome(),
				new CommandKick(),
				new CommandKill(),
				new CommandLanguage(),
				new CommandModularMSMF(),
				new EconomySystem(),
				//new CommandMotd(),
				new CommandMute(),
				new CommandPlayershell(),
				//new CommandReport(),
				new CommandServerInfo(),
				new CommandSetSpawn(),
				new CommandSlaughter(),
				new CommandSpawn(),
				new CommandTeleport(),
				new CommandClearChat()
		}) {
			commandList.add(cmd);
		}
		
		return commandList;
	}
}