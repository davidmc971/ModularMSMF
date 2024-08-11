package io.github.davidmc971.modularmsmf.basics.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
// import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.basics.listeners.BasicEvents;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.ModularMSMFBasics;
import io.github.davidmc971.modularmsmf.basics.util.KillType;
import io.github.davidmc971.modularmsmf.basics.util.Util;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil.ChatFormat;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil;

/**
 * @author Lightkeks
 * 
 * will be merged into CommandKill.java
 */

public class CommandKillPlayer implements IModularMSMFCommand/* , Listener, InventoryHolder */ {

	private BasicEvents basicEvents;

	public CommandKillPlayer() {
		basicEvents = ModularMSMFBasics.Instance().getBasicEvents();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!PermissionManager.checkPermission(sender, "kill")) {
			ChatUtil.sendMsgNoPerm(sender);
			return true;
		}
		if (args.length == 0) {
			return killHelp(sender, command, label, args); // will be changed with a usable GUI
			//return killGUI(sender, command, label, args); //opening a GUI to choose options (like down below)
		}
		if (args.length == 1) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				// if (args[0].toLowerCase().equals(sender.getName().toLowerCase())) {
				// 	return killMeSub(sender, command, label, args);
				// }
				if (args[0].toLowerCase().equals(player.getName().toLowerCase())) {
					basicEvents.registerKilledPlayer(player, KillType.KILL);
					Util.broadcastWithConfiguredLanguageEach(ChatFormat.DEATH, "events.killed",
							"_player", player.getName());
					player.setHealth(0);
					return true;
				}
			}
			Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "player.nonexistant");
			return true;
		}
		if (args.length <= 2) {
			Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "arguments.toomany");
			return true;
		}
		return true;
	}

	private boolean killHelp(CommandSender sender, Command command, String label, String[] args) {
		// sender.sendMessage("/kill (me/all/<target>)");
		// sender.sendMessage("/kill me - suicide");
		sender.sendMessage("/kill all - all players die");
		sender.sendMessage("/kill <target> - <target> dies");
		return true;
	}

	// private boolean killMeSub(CommandSender sender, Command command, String label, String[] args) {
	// 	if (sender instanceof ConsoleCommandSender || !PermissionManager.checkPermission(sender, "kill_me")) {
	// 		ChatUtil.sendMsgNoPerm(sender);
	// 		return true;
	// 	}
	// 	basicEvents.registerKilledPlayer(((Player) sender), KillType.SUICIDE);
	// 	Util.broadcastWithConfiguredLanguageEach(ChatFormat.DEATH, "events.suicide", "_player",
	// 			sender.getName());
	// 	((Player) sender).setHealth(0);
	// 	return true;
	// }

/* 	private boolean killGUI(CommandSender sender, Command command, String label, String[] args){
		final Inventory inv;

		public Kill_GUI(){
			//Create a new ownerless inventory (not a real inv)
			inv = Bukkit.createInventory(null, 9, "Kill GUI");

			//Put items into inv
			initializeItems();
		}

		public void initializeItems(){
			inv.addItem(createGuiItem(Material.PLAYER_HEAD, "Option to kill yourself", "§4KILL YOURSELF"));
			inv.addItem(createGuiItem(Material.TNT, "Cast a spell to kill all at once", "§4KILL ALL USERS AT ONCE"));
		}

		protected ItemStack createGuiItem(final Material material, final String name, final String... lore){
			final ItemStack item = new ItemStack(material, 1);
			final ItemMeta meta = item.getItemMeta();

			meta.setDisplayName(name);
			meta.setLore(Arrays.asList(lore));

			return item;
		}

		@EventHandler
		public void onInventoryClick(final InventoryClickEvent e){
			if (!e.getInventory().equals(inv)) return;

			e.setCancelled(true);
			final ItemStack clickedItem = e.getCurrentItem();

			if(clickedItem == null || clickedItem.getType().isAir()) return;

			final Player p = (Player) e.getWhoClicked();

			p.sendMessage("You clicked at slot "+e.getRawSlot());
		}

		@EventHandler
		public void onInventoryClick(final InventoryDragEvent e){
			if(e.getInventory().equals(inv)){
				e.setCancelled(true);
			}
		}
		return true;
	}  */

	@Override
	public String Label() {
		return "kill";
	}

	@Override
	public String[] Aliases() {
		return null;
	}

	@Override
	public boolean Enabled() {
		return true;
	}

/* 	@Override
	public @NotNull Inventory getInventory() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getInventory'");
	} */
}