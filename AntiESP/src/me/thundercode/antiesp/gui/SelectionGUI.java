package me.thundercode.antiesp.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.thundercode.antiesp.main.AntiESP;
import me.thundercode.antiesp.misc.Utility;

public class SelectionGUI implements Listener {

	private AntiESP main;

	public SelectionGUI(AntiESP instance) {
		// Passing main instance.
		main = instance;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	private void onClick(InventoryClickEvent event) {
		// Checking for right inventory.
		if ((event.getView().getTitle().toString().equalsIgnoreCase("Select The Render Distance"))
				&& (Utility.isValidItemStack(event.getCurrentItem()))
				&& (event.getView().getPlayer() instanceof Player)) {
			// Registering item actions.
			this.registerItemAction(event.getCurrentItem(), "Distance: Tiny", "25",
					(Player) event.getView().getPlayer());
			this.registerItemAction(event.getCurrentItem(), "Distance: Medium", "50",
					(Player) event.getView().getPlayer());
			this.registerItemAction(event.getCurrentItem(), "Distance: Large", "75",
					(Player) event.getView().getPlayer());
			event.setCancelled(true);
		}
	}

	public void createSelectionGUI(Player pPlayer) {
		// Creating GUI on first show up.
		Inventory inventory = Bukkit.createInventory(pPlayer, 27, "Select The Render Distance");
		Utility.createItem(inventory, Material.GRAY_STAINED_GLASS,
				ChatColor.DARK_GRAY + "Visit the config file for more precise values", null, 4);
		Utility.createItem(inventory, Material.RED_WOOL, ChatColor.RED + "Distance: Tiny",
				ChatColor.DARK_GRAY + "25 Blocks", 11);
		Utility.createItem(inventory, Material.RED_WOOL, ChatColor.RED + "Distance: Medium",
				ChatColor.DARK_GRAY + "50 Blocks", 13);
		Utility.createItem(inventory, Material.RED_WOOL, ChatColor.RED + "Distance: Large",
				ChatColor.DARK_GRAY + "75 Blocks", 15);
		pPlayer.openInventory(inventory);
	}

	private void editConfigValueAndSave(String pPath, String pValue) {
		// Edit and save config.
		main.getConfig().set(pPath, pValue);
		main.saveConfig();
	}

	private void registerItemAction(ItemStack pItem, String pItemName, String pNewValue, Player pPlayer) {
		if (pItem.getItemMeta().getDisplayName().contains(pItemName) && (pItem.getItemMeta() != null)) {
			// Edit and save config & initiating new base GUI.
			this.editConfigValueAndSave("values.renderDistance", pNewValue);
			pPlayer.closeInventory();
			IngameGUI gui = new IngameGUI(main);
			gui.createGUI(pPlayer);
		}
	}
}
