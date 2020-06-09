package me.thundercode.antiesp.entity;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;

import net.minecraft.server.v1_14_R1.EntityArmorStand;
import net.minecraft.server.v1_14_R1.EntityTypes;

public class CustomEntity_1_14 extends EntityArmorStand {

	public CustomEntity_1_14(org.bukkit.World world) {
		// Passing nms.
		super(EntityTypes.ARMOR_STAND, ((CraftWorld) world).getHandle());
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
