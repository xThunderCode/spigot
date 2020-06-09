package me.thundercode.antiesp.misc;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class Utility {

	private Utility() {
		// In case of reflection.
		throw new UnsupportedOperationException();
	}

	public static void createItem(Inventory pInventory, Material pMaterial, String pName, String pLore, int pSlot) {
		// Initiating new item.
		ItemStack item = new ItemStack(pMaterial);
		ItemMeta itemMeta = item.getItemMeta();
		List<String> loreString = new ArrayList<String>();
		// Setting name.
		if (!pName.isEmpty()) {
			itemMeta.setDisplayName(pName);
		}
		// Setting lore.
		if (itemMeta != null && pLore != null) {
			loreString.add(pLore);
			itemMeta.setLore(loreString);
		}
		// Adding item meta.
		item.setItemMeta(itemMeta);
		// Adding to inventory.
		pInventory.setItem(pSlot, item);
	}

	public static boolean isValidItemStack(ItemStack pItemStack) {
		// Checking itemstack for null.
		if ((pItemStack != null) && (pItemStack.getItemMeta() != null)
				&& (pItemStack.getItemMeta().getDisplayName() != null)) {
			return true;
		} else {
			return false;
		}
	}

	public static String getServerVersion() {
		// Returning server version.
		String version = Bukkit.getServer().getVersion();
		return version;
	}

}
