package me.thundercode.antiesp.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import me.thundercode.antiesp.entity.CustomEntity_1_13;
import me.thundercode.antiesp.entity.CustomEntity_1_14;
import me.thundercode.antiesp.entity.CustomEntity_1_15;
import me.thundercode.antiesp.main.AntiESP;
import me.thundercode.antiesp.misc.Utility;

public class BlockEvent implements Listener {

	private AntiESP main;

	public BlockEvent(AntiESP instance) {
		// Passing main instance.
		this.main = instance;
	}

	@EventHandler
	private void onPlace(BlockPlaceEvent event) {
		Block placedBlock = event.getBlock();
		// Spawning custom entity if correct block is placed.
		if (this.isCorrectBlock(placedBlock)) {
			this.spawnEntity(placedBlock.getWorld(), placedBlock.getLocation().add(0.5, 0, 0.5));
		} else {
			if (!Utility.getServerVersion().contains("1.13") && placedBlock.getType().equals(Material.BARREL)) {
				this.spawnEntity(placedBlock.getWorld(), placedBlock.getLocation().add(0.5, 0, 0.5));
			}
		}
	}

	@EventHandler
	private void onBreak(BlockBreakEvent event) {
		Block brokenBlock = event.getBlock();
		// Removing custom entity if correct block is broken.
		if (this.isCorrectBlock(brokenBlock)) {
			this.handleEntity(brokenBlock.getLocation());
		} else {
			if (!Utility.getServerVersion().contains("1.13") && brokenBlock.getType().equals(Material.BARREL)) {
				this.handleEntity(brokenBlock.getLocation());
			}
		}
	}

	private boolean isCorrectBlock(Block pBlock) {
		// Checking if block equals intended blocks.
		if ((pBlock.getType().equals(Material.CHEST)) || (pBlock.getType().equals(Material.ENDER_CHEST))
				|| (pBlock.getType().equals(Material.TRAPPED_CHEST)) || (pBlock.getType().equals(Material.SPAWNER))
				|| (pBlock.getType().equals(Material.FURNACE))) {
			return true;
		} else {
			return false;
		}
	}

	private void spawnEntity(World pWorld, Location pLocation) {
		// Calling spawn method if correct server version is met.
		if (Utility.getServerVersion().contains("1.13")) {
			CustomEntity_1_13 customEntity = new CustomEntity_1_13(pWorld);
			customEntity.spawnEntity(pLocation);
			this.printDebug(customEntity.getId());
		}
		if (Utility.getServerVersion().contains("1.14")) {
			CustomEntity_1_14 customEntity = new CustomEntity_1_14(pWorld);
			customEntity.spawnEntity(pLocation);
			this.printDebug(customEntity.getId());
		}
		if (Utility.getServerVersion().contains("1.15")) {
			CustomEntity_1_15 customEntity = new CustomEntity_1_15(pWorld);
			customEntity.spawnEntity(pLocation);
			this.printDebug(customEntity.getId());
		}

	}

	private void printDebug(int pID) {
		// Printing debug message into the console.
		if (this.main.isDebugging()) {
			this.main.getLogger().info("Entity: " + pID + " has been registered.");
		}
	}

	private void handleEntity(Location pLocation) {
		// Looking for entitys of type Custom Entity in chunk.
		for (Entity entity : pLocation.getChunk().getEntities()) {
			if (pLocation.distanceSquared(entity.getLocation().subtract(0.5, 0, 0.5)) < 1 * 1
					&& entity instanceof ArmorStand) {
				// Calling remove Custom Entity for the right server version.
				if ((Utility.getServerVersion().contains("1.13"))
						&& (((org.bukkit.craftbukkit.v1_13_R2.entity.CraftArmorStand) entity)
								.getHandle() instanceof CustomEntity_1_13)) {
					this.remove(entity);
				}
				if ((Utility.getServerVersion().contains("1.14"))
						&& (((org.bukkit.craftbukkit.v1_14_R1.entity.CraftArmorStand) entity)
								.getHandle() instanceof CustomEntity_1_14)) {
					this.remove(entity);
				}
				if ((Utility.getServerVersion().contains("1.15"))
						&& (((org.bukkit.craftbukkit.v1_15_R1.entity.CraftArmorStand) entity)
								.getHandle() instanceof CustomEntity_1_15)) {
					this.remove(entity);
				}
			}
		}
	}

	private void remove(Entity pEntity) {
		// Delteing the entity.
		pEntity.remove();
		// Removing entity from range array.
		// Static to avoid throwaway instances.
		MoveEvent.getOutRange().remove(pEntity);
		// Printing debug if needed.
		if (main.isDebugging()) {
			main.getLogger().info("Entity: " + pEntity.getEntityId() + " has been removed.");
		}
	}

}
