package me.thundercode.antiesp.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.thundercode.antiesp.events.MoveEvent;
import me.thundercode.antiesp.gui.IngameGUI;
import me.thundercode.antiesp.main.AntiESP;
import net.md_5.bungee.api.ChatColor;

public class ESPCmd implements CommandExecutor, TabCompleter {

	private AntiESP main;
	private String title = ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "AntiESP" + ChatColor.DARK_GRAY + "] ";

	public ESPCmd(AntiESP instance) {
		main = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player && label.equalsIgnoreCase("esp") && sender.isOp()) {
			Player player = (Player) sender;
			// Notification if player executes /esp.
			if (args.length == 0) {
				sender.sendMessage(title + "Current Version: " + main.getVersion());
				sender.sendMessage(title + "Plugin made by xThunderCodex");
				sender.sendMessage(
						title + ChatColor.GOLD + "/esp help " + ChatColor.DARK_GRAY + "for a list of all commands.");
				return true;
			}
			// Toggling debugmode to: true.
			if (args[0].equalsIgnoreCase("debug")) {
				if (!main.isDebugging()) {
					main.setDebugging(true);
					sender.sendMessage(title + "Debug mode has been enabled. (Console)");
				} else {
					main.setDebugging(false);
					sender.sendMessage(title + "Debug mode has been disabled.");
				}
				return true;
			}
			// Clearing range arrays in MoveEvent.
			if (args[0].equalsIgnoreCase("clear")) {
				// Static to avoid throwaway instances.
				MoveEvent.clearArray();
				sender.sendMessage(title + "Array has been cleared.");
				return true;
			}
			// Basic help command, that shows all plugin commands.
			if (args[0].equalsIgnoreCase("help")) {
				sender.sendMessage(
						title + ChatColor.GOLD + "/esp " + ChatColor.DARK_GRAY + "Prefix & General Information.");
				sender.sendMessage(
						title + ChatColor.GOLD + "/esp help " + ChatColor.DARK_GRAY + "Shows you the command list.");
				sender.sendMessage(
						title + ChatColor.GOLD + "/esp debug " + ChatColor.DARK_GRAY + "Enables build in debug mode.");
				sender.sendMessage(title + ChatColor.GOLD + "/esp clear " + ChatColor.DARK_GRAY + "Lag/Bug encounter.");
				sender.sendMessage(
						title + ChatColor.GOLD + "/esp config " + ChatColor.DARK_GRAY + "Configurate via ingame GUI.");
				sender.sendMessage(title + "For more configuration visit the config.yml.");
				return true;
			}
			// Initiating configuration GUI.
			if ((args[0].equalsIgnoreCase("config")) || (args[0].equalsIgnoreCase("gui"))) {
				IngameGUI gui = new IngameGUI(main);
				gui.createGUI(player);
				sender.sendMessage(
						title + ChatColor.RED + "Chunks may need to reload after applying the configurations!");
				return true;
			}
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		// Checking for base command.
		if ((cmd.getName().equalsIgnoreCase("esp")) && (args.length == 1)) {
			ArrayList<String> arguments = new ArrayList<String>();
			// Checking if arguments aren't null.
			if (!(args[0] == null) && !args[0].equals("")) {
				String[] cmds = { "help", "config", "debug", "clear" };
				for (String str : cmds) {
					// Adding commands to arguments.
					arguments.add(str);
				}
				return arguments;
			}
		}
		return null;
	}
}
