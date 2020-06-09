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
import me.thundercode.antiesp.misc.Config;
import me.thundercode.antiesp.misc.Utility;

public class IngameGUI implements Listener {

	private AntiESP main;
	private Config values;

	public IngameGUI(AntiESP instance) {
		// Passing main instance.
		this.main = instance;
		this.values = new Config(this.main);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	private void onClick(InventoryClickEvent event) {
		// Checking for right inventory.
		if ((event.getView().getTitle().toString().equalsIgnoreCase("AntiESP Configuration GUI"))
				&& (Utility.isValidItemStack(event.getCurrentItem()))) {
			// Registering actions for certain items.
			this.registerItemAction(event.getCurrentItem(), "Default Chest", this.values.getDefaultChest(),
					event.getInventory(), Material.CHEST, "blocks.defaultChest", 1);
			this.registerItemAction(event.getCurrentItem(), "Ender Chest", this.values.getEnderChest(),
					event.getInventory(), Material.ENDER_CHEST, "blocks.enderChest", 2);
			this.registerItemAction(event.getCurrentItem(), "Trapped Chest", this.values.getTrappedChest(),
					event.getInventory(), Material.TRAPPED_CHEST, "blocks.trappedChest", 3);
			this.registerItemAction(event.getCurrentItem(), "Default Spawner", this.values.getDefaultSpawner(),
					event.getInventory(), Material.SPAWNER, "blocks.defaultSpawner", 5);
			this.registerItemAction(event.getCurrentItem(), "Default Furnace", this.values.getDefaultFurnace(),
					event.getInventory(), Material.FURNACE, "blocks.defaultFurnace", 6);
			this.registerItemAction(event.getCurrentItem(), "Load Chunks", this.values.getLoadChunks(),
					event.getInventory(), Material.GREEN_WOOL, "world.loadChunks", 20);
			this.registerItemAction(event.getCurrentItem(), "Unload Chunks", this.values.getUnloadChunks(),
					event.getInventory(), Material.RED_WOOL, "world.unloadChunks", 22);
			if (!Utility.getServerVersion().contains("1.13")) {
				this.registerItemAction(event.getCurrentItem(), "Default Barrel", this.values.getDefaultBarrel(),
						event.getInventory(), Material.BARREL, "blocks.defaultBarrel", 4);
			} else {
				event.setCancelled(true);
			}
			// Initiating Selection GUI.
			if ((event.getCurrentItem().getItemMeta().getDisplayName().contains("Render Distance"))
					&& (event.getView().getPlayer() instanceof Player)) {
				event.getView().getPlayer().closeInventory();
				SelectionGUI selectionGUI = new SelectionGUI(this.main);
				selectionGUI.createSelectionGUI((Player) event.getView().getPlayer());
			}
			event.setCancelled(true);
		}
	}

	public void createGUI(Player pPlayer) {
		// Creating GUI on first show up.
		Inventory inventory = Bukkit.createInventory(pPlayer, 27, "AntiESP Configuration GUI");
		Utility.createItem(inventory, Material.CHEST, "Default Chest", ChatColor.DARK_GRAY + "Click to toggle", 1);
		Utility.createItem(inventory, Material.ENDER_CHEST, "Ender Chest", ChatColor.DARK_GRAY + "Click to toggle", 2);
		Utility.createItem(inventory, Material.TRAPPED_CHEST, "Trapped Chest", ChatColor.DARK_GRAY + "Click to toggle",
				3);
		Utility.createItem(inventory, Material.SPAWNER, "Default Spawner", ChatColor.DARK_GRAY + "Click to toggle", 5);
		Utility.createItem(inventory, Material.FURNACE, "Default Furnace", ChatColor.DARK_GRAY + "Click to toggle", 6);
		Utility.createItem(inventory, Material.SHULKER_BOX, "Shulker Box", ChatColor.RED + "Coming soon..", 7);
		Utility.createItem(inventory, Material.GREEN_WOOL, "Load Chunks", ChatColor.DARK_GRAY + "Click to toggle", 20);
		Utility.createItem(inventory, Material.RED_WOOL, "Unload Chunks", ChatColor.DARK_GRAY + "Click to toggle", 22);
		Utility.createItem(inventory, Material.DIAMOND_PICKAXE, "Render Distance", "§8Click to configure", 24);
		// Version compatibility.
		if (!Utility.getServerVersion().contains("1.13")) {
			Utility.createItem(inventory, Material.BARREL, "Default Barrel", ChatColor.DARK_GRAY + "Click to toggle",
					4);
		} else {
			Utility.createItem(inventory, Material.BARRIER, "Item not found",
					ChatColor.DARK_GRAY + "Update to 1.14 or above", 4);
		}
		pPlayer.openInventory(inventory);
	}

	private void editConfigValueAndSave(String pPath, String pValue) {
		// Edit and save config.
		this.main.getConfig().set(pPath, pValue);
		this.main.saveConfig();
		this.values.loadData();
	}

	private void registerItemAction(ItemStack pItem, String pItemName, boolean pValue, Inventory pInventory,
			Material pMaterial, String pPath, int pSlot) {
		// Exception checks.
		if ((pItem.getItemMeta().getDisplayName().contains(pItemName)) && (pItem.getItemMeta() != null)) {
			if ((pValue) && (pPath != null) && (!pPath.isEmpty())) {
				// Creating Item.
				Utility.createItem(pInventory, pMaterial, ChatColor.RED + pItemName, ChatColor.DARK_GRAY + "State: Off",
						pSlot);
				this.editConfigValueAndSave(pPath, "false");
				pValue = false;
			} else {
				// Creating Item.
				Utility.createItem(pInventory, pMaterial, ChatColor.GREEN + pItemName,
						ChatColor.DARK_GRAY + "State: On", pSlot);
				if (pPath != null) {
					this.editConfigValueAndSave(pPath, "true");
				}
				pValue = true;
			}
		}
	}
}
