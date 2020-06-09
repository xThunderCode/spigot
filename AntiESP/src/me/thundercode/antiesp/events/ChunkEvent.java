package me.thundercode.antiesp.events;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import me.thundercode.antiesp.entity.CustomEntity_1_13;
import me.thundercode.antiesp.entity.CustomEntity_1_14;
import me.thundercode.antiesp.entity.CustomEntity_1_15;
import me.thundercode.antiesp.main.AntiESP;
import me.thundercode.antiesp.misc.Config;
import me.thundercode.antiesp.misc.Utility;

public class ChunkEvent implements Listener {

	private AntiESP main;
	private Config values;

	public ChunkEvent(AntiESP instance) {
		// Passing main instance.
		this.main = instance;
		this.values = new Config(main);
	}

	@EventHandler
	private void onChunkLoad(ChunkLoadEvent event) {
		this.values.loadData();
		// Cheking for config value.
		if (values.getLoadChunks()) {
			Chunk chunk = event.getChunk();
			// Looping all tile entitys of a chunk.
			for (BlockState tile : chunk.getTileEntities()) {
				// Spawning Custom Entity at tile Location if it's the right tile material.
				if ((this.isCorrectBlockState(tile) || (Material.BARREL.equals(tile.getBlock().getType())
						&& !Utility.getServerVersion().contains("1.13")))) {
					this.spawnEntity(chunk.getWorld(), tile.getLocation().add(0.5, 0, 0.5));
				}
			}
		}
	}

	@EventHandler
	private void onChunkUnload(ChunkUnloadEvent event) {
		// checking for config value.
		this.values.loadData();
		if (this.values.getUnloadChunks()) {
			Chunk chunk = event.getChunk();
			// Looping all entitys of a chunk.
			for (Entity entity : chunk.getEntities()) {
				if (entity instanceof ArmorStand) {
					// Checking if it's the custom entity and removing it then
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
	}

	private boolean isCorrectBlockState(BlockState pTile) {
		// Checking if it's the intended blockstate.
		// TODO: Add brewingstand, shulker, bed
		if ((Material.CHEST.equals(pTile.getBlock().getType())
				|| Material.ENDER_CHEST.equals(pTile.getBlock().getType())
				|| Material.TRAPPED_CHEST.equals(pTile.getBlock().getType())
				|| Material.SPAWNER.equals(pTile.getBlock().getType())
				|| Material.FURNACE.equals(pTile.getBlock().getType()))) {
			return true;
		} else {
			return false;
		}
	}

	private void remove(Entity pEntity) {
		// Deleting the entity.
		pEntity.remove();
		// Removing entity from range array.
		// Static to avoid throwaway instances.
		MoveEvent.getOutRange().remove(pEntity);
		// Printing debug if needed.
		if (main.isDebugging()) {
			main.getLogger().info("Entity: " + pEntity.getEntityId() + " has been removed.");
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
		if (main.isDebugging()) {
			main.getLogger().info("Entity: " + pID + " has been registered.");
		}
	}
}
