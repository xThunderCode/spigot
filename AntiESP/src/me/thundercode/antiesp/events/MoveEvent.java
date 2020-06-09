package me.thundercode.antiesp.events;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.thundercode.antiesp.entity.CustomEntity_1_13;
import me.thundercode.antiesp.entity.CustomEntity_1_14;
import me.thundercode.antiesp.entity.CustomEntity_1_15;
import me.thundercode.antiesp.main.AntiESP;
import me.thundercode.antiesp.misc.Config;
import me.thundercode.antiesp.misc.Utility;

public class MoveEvent implements Listener {

	// Static use to avoid throwaway instances.
	private static ArrayList<Entity> outRange = new ArrayList<>();
	private static ArrayList<Entity> inRange = new ArrayList<>();
	private long cooldown;
	private Config values;
	private AntiESP main;

	public MoveEvent(AntiESP instance) {
		// Passing main instance.
		this.main = instance;
		this.values = new Config(main);
	}

	@EventHandler
	private void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (this.cooldown >= 20L * this.values.getTickRate()) {
			// Checking for entities.
			for (Entity entity : player.getWorld().getEntities()) {
				// Checking for correct server version and manage blocks then.
				if (entity instanceof ArmorStand) {
					if ((Utility.getServerVersion().contains("1.13"))
							&& (((org.bukkit.craftbukkit.v1_13_R2.entity.CraftArmorStand) entity)
									.getHandle() instanceof CustomEntity_1_13)) {
						handleBlocks(entity, player);
					}
					if ((Utility.getServerVersion().contains("1.14"))
							&& (((org.bukkit.craftbukkit.v1_14_R1.entity.CraftArmorStand) entity)
									.getHandle() instanceof CustomEntity_1_14)) {
						handleBlocks(entity, player);
					}
					if ((Utility.getServerVersion().contains("1.15"))
							&& (((org.bukkit.craftbukkit.v1_15_R1.entity.CraftArmorStand) entity)
									.getHandle() instanceof CustomEntity_1_15)) {
						handleBlocks(entity, player);
					}
				}
			}
			this.cooldown = 0L;
			this.values.loadData();
		}
		if (this.cooldown <= 20L * this.values.getTickRate()) {
			this.cooldown++;
		}
	}

	private void handleBlocks(Entity pEntity, Player pPlayer) {
		// Checking if players are close enough to certain block.
		if ((pPlayer.getLocation().distanceSquared(pEntity.getLocation())) > (this.values.getRenderDistance()
				* this.values.getRenderDistance())) {
			// Hiding blocks.
			this.hideBlock(pPlayer, Material.CHEST, pEntity, this.values.getDefaultChest());
			this.hideBlock(pPlayer, Material.ENDER_CHEST, pEntity, this.values.getEnderChest());
			this.hideBlock(pPlayer, Material.TRAPPED_CHEST, pEntity, this.values.getTrappedChest());
			this.hideBlock(pPlayer, Material.SPAWNER, pEntity, this.values.getDefaultSpawner());
			this.hideBlock(pPlayer, Material.FURNACE, pEntity, this.values.getDefaultFurnace());
			if (!Utility.getServerVersion().contains("1.13")) {
				this.hideBlock(pPlayer, Material.BARREL, pEntity, this.values.getDefaultBarrel());
			}
			if (!getOutRange().contains(pEntity)) {
				getOutRange().add(pEntity);
			}
			getInRange().remove(pEntity);
		}
		// Checking if players are far enough to certain block.
		if ((pPlayer.getLocation().distanceSquared(pEntity.getLocation())) < (this.values.getRenderDistance()
				* this.values.getRenderDistance())) {
			if (!getInRange().contains(pEntity)) {
				// Updating blocks.
				this.updateBlock(Material.CHEST, pEntity, this.values.getDefaultChest());
				this.updateBlock(Material.ENDER_CHEST, pEntity, this.values.getEnderChest());
				this.updateBlock(Material.TRAPPED_CHEST, pEntity, this.values.getTrappedChest());
				this.updateBlock(Material.SPAWNER, pEntity, this.values.getDefaultSpawner());
				this.updateBlock(Material.FURNACE, pEntity, this.values.getDefaultFurnace());
				if (!Utility.getServerVersion().contains("1.13")) {
					this.updateBlock(Material.BARREL, pEntity, this.values.getDefaultBarrel());
				}
				getInRange().add(pEntity);
			}
			getOutRange().remove(pEntity);
		}
	}

	@SuppressWarnings("deprecation")
	private void hideBlock(Player pPlayer, Material pMaterial, Entity pEntity, boolean pValue) {
		// Replacing intended block with clientside air.
		if ((pEntity.getLocation().getBlock().getType().equals(pMaterial)) && (pValue)) {
			pPlayer.sendBlockChange(pEntity.getLocation(), Material.AIR, (byte) 0);
		}
	}

	private void updateBlock(Material pMaterial, Entity pEntity, boolean pValue) {
		// Updating "invisible" block.
		if ((pEntity.getLocation().getBlock().getType().equals(pMaterial)) && (pValue)) {
			pEntity.getLocation().getBlock().getState().update(true, true);
		}
	}

	public static void clearArray() {
		// Clearing arrays.
		getInRange().clear();
		getOutRange().clear();
	}

	public static ArrayList<Entity> getOutRange() {
		return outRange;
	}

	public static ArrayList<Entity> getInRange() {
		return inRange;
	}

}