package me.thundercode.antiesp.main;

import org.bukkit.plugin.java.JavaPlugin;

import me.thundercode.antiesp.command.ESPCmd;
import me.thundercode.antiesp.events.BlockEvent;
import me.thundercode.antiesp.events.ChunkEvent;
import me.thundercode.antiesp.events.MoveEvent;
import me.thundercode.antiesp.gui.IngameGUI;
import me.thundercode.antiesp.gui.SelectionGUI;
import me.thundercode.antiesp.misc.Utility;

public class AntiESP extends JavaPlugin {

	private final double version;
	private boolean isDebugging;

	public AntiESP() {
		this.version = Double.parseDouble(this.getDescription().getVersion());
	}

	@Override
	public void onEnable() {
		// Version compatibility.
		if (this.isValidServerVersion()) {
			this.loadConfig();
			// Registering events.
			this.getServer().getPluginManager().registerEvents(new BlockEvent(this), this);
			this.getServer().getPluginManager().registerEvents(new MoveEvent(this), this);
			this.getServer().getPluginManager().registerEvents(new ChunkEvent(this), this);
			this.getServer().getPluginManager().registerEvents(new IngameGUI(this), this);
			this.getServer().getPluginManager().registerEvents(new SelectionGUI(this), this);
			// Registering command(s).
			this.getCommand("esp").setExecutor(new ESPCmd(this));
			this.getLogger().info("Enabled succesfully");
		}
	}

	public boolean isValidServerVersion() {
		// Checking for server version and printing info.
		if ((Utility.getServerVersion().contains("1.15"))
				|| (Utility.getServerVersion().contains("1.14") || (Utility.getServerVersion().contains("1.13")))) {
			if (Utility.getServerVersion().contains("1.14")) {
				this.getLogger().info("Minecraft 1.14 may not be fully compatible");
				this.getLogger().info("Please update to 1.15");
			}
			if (Utility.getServerVersion().contains("1.13")) {
				this.getLogger().info("Minecraft 1.13 may not be fully compatible");
				this.getLogger().info("Please update to 1.15");
			}
			return true;
		} else {
			this.getLogger().info("Failed to enable plugin");
			this.getLogger().info("Your sever version is not compatible with this plugin");
			this.getServer().getPluginManager().disablePlugin(this);
			return false;
		}
	}

	private void loadConfig() {
		// Initiating config.yml.
		this.getConfig().options().copyDefaults(true);
		this.getConfig().options().header("# AntiESP configuration file");
		this.saveConfig();
	}

	public double getVersion() {
		return this.version;
	}

	public boolean isDebugging() {
		return this.isDebugging;
	}

	public void setDebugging(boolean isDebugging) {
		this.isDebugging = isDebugging;
	}

}
