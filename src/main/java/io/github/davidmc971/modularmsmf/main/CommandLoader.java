package io.github.davidmc971.modularmsmf.main;

import java.io.IOException;
import java.util.ArrayList;

import com.google.common.reflect.ClassPath;

import io.github.davidmc971.modularmsmf.commands.*;
import io.github.davidmc971.modularmsmf.ModularMSMF;

/**
 * 
 * @author davidmc971
 *
 */

public class CommandLoader {
	ModularMSMF plugin;
	
	public CommandLoader (ModularMSMF plugin) {
		this.plugin = plugin;
	}
	
	public ArrayList<AbstractCommand> loadCommands(ClassLoader classLoader){
		plugin.getLogger().info("===========\nloadCommands()\n===========");
		ArrayList<AbstractCommand> commandList = new ArrayList<AbstractCommand>();
		
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
						commandList.add((AbstractCommand)clazz.getConstructor(plugin.getClass()).newInstance(plugin));
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
			for (AbstractCommand cmd : commandList) {
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

	private ArrayList<AbstractCommand> loadCommandsFallback() {
		//Reflections reflections = new Reflections("com.mycompany");    
		//Set<Class<? extends MyInterface>> classes = reflections.getSubTypesOf(MyInterface.class);
		ArrayList<AbstractCommand> commandList = new ArrayList<AbstractCommand>();
		
		for(AbstractCommand cmd : new AbstractCommand[] {
				new CommandBan(plugin),
				new CommandFeed(plugin),
				new CommandHeal(plugin),
				new CommandHome(plugin),
				new CommandKick(plugin),
				new CommandKill(plugin),
				new CommandLanguage(plugin),
				new CommandModularMSMF(plugin),
				new CommandMoney(plugin),
				new CommandMotd(plugin),
				new CommandMute(plugin),
				new CommandPlayershell(plugin),
				new CommandReport(plugin),
				new CommandServerInfo(plugin),
				new CommandSetSpawn(plugin),
				new CommandSlaughter(plugin),
				new CommandSpawn(plugin),
				new CommandTeleport(plugin)
		}) {
			commandList.add(cmd);
		}
		
		return commandList;
	}
}
