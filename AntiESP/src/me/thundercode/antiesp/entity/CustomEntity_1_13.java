package me.thundercode.antiesp.entity;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;

import net.minecraft.server.v1_13_R2.EntityArmorStand;

public class CustomEntity_1_13 extends EntityArmorStand {

	public CustomEntity_1_13(org.bukkit.World pWorld) {
		// Passing nms.
		super(((CraftWorld) pWorld).getHandle());
	}

	public void spawnEntity(Location loc) {
		// Spawning nms entity with custom attributes.
		this.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		this.setInvisible(true); // true
		this.setMarker(true);
		this.setNoGravity(true);
		this.world.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);
	}
}
