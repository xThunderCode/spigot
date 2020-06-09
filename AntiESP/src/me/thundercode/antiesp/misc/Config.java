package me.thundercode.antiesp.misc;

import me.thundercode.antiesp.main.AntiESP;

public class Config {

	private boolean defaultChest;
	private boolean enderChest;
	private boolean trappedChest;
	private boolean defaultBarrel;
	private boolean defaultSpawner;
	private boolean defaultFurnace;
	private boolean loadChunks;
	private boolean unloadChunks;
	private double renderDistance;
	private long tickRate;
	private AntiESP main;

	public Config(AntiESP instance) {
		// Passing main instance.
		main = instance;
		this.loadData();
	}

	public void loadData() {
		// Setting data to variables.
		this.setDefaultChest(getConfigBoolean("blocks.defaultChest"));
		this.setEnderChest(getConfigBoolean("blocks.enderChest"));
		this.setTrappedChest(getConfigBoolean("blocks.trappedChest"));
		this.setDefaultBarrel(getConfigBoolean("blocks.defaultBarrel"));
		this.setDefaultSpawner(getConfigBoolean("blocks.defaultSpawner"));
		this.setDefaultFurnace(getConfigBoolean("blocks.defaultFurnace"));
		this.setLoadChunks(getConfigBoolean("world.loadChunks"));
		this.setUnloadChunks(getConfigBoolean("world.unloadChunks"));
		this.setRenderDistance(getConfigDouble("values.renderDistance"));
		this.setTickRate(getConfigLong("values.tickRate"));
	}

	private boolean getConfigBoolean(String pPath) {
		// Getting boolean from config.
		String stringData = main.getConfig().getString(pPath);
		boolean toBoolean = Boolean.parseBoolean(stringData);
		return toBoolean;
	}

	private double getConfigDouble(String pPath) {
		// Getting double from config.
		String stringData = main.getConfig().getString(pPath);
		double toDouble = Double.parseDouble(stringData);
		return toDouble;
	}

	private long getConfigLong(String pPath) {
		// Getting long from config.
		String stringData = main.getConfig().getString(pPath);
		long toLong = Long.parseLong(stringData);
		return Math.round(toLong) / 10;
	}

	public boolean getDefaultChest() {
		return defaultChest;
	}

	private void setDefaultChest(boolean defaultChest) {
		this.defaultChest = defaultChest;
	}

	public boolean getEnderChest() {
		return enderChest;
	}

	private void setEnderChest(boolean enderChest) {
		this.enderChest = enderChest;
	}

	public boolean getTrappedChest() {
		return trappedChest;
	}

	private void setTrappedChest(boolean trappedChest) {
		this.trappedChest = trappedChest;
	}

	public boolean getDefaultBarrel() {
		return defaultBarrel;
	}

	private void setDefaultBarrel(boolean defaultBarrel) {
		this.defaultBarrel = defaultBarrel;
	}

	public boolean getDefaultFurnace() {
		return defaultFurnace;
	}

	private void setDefaultFurnace(boolean defaultFurnace) {
		this.defaultFurnace = defaultFurnace;
	}

	public boolean getLoadChunks() {
		return loadChunks;
	}

	private void setLoadChunks(boolean loadChunks) {
		this.loadChunks = loadChunks;
	}

	public double getRenderDistance() {
		return renderDistance;
	}

	private void setRenderDistance(double renderDistance) {
		this.renderDistance = renderDistance;
	}

	public boolean getUnloadChunks() {
		return unloadChunks;
	}

	private void setUnloadChunks(boolean unloadChunks) {
		this.unloadChunks = unloadChunks;
	}

	public boolean getDefaultSpawner() {
		return defaultSpawner;
	}

	private void setDefaultSpawner(boolean defaultSpawner) {
		this.defaultSpawner = defaultSpawner;
	}

	public long getTickRate() {
		return tickRate;
	}

	private void setTickRate(long tickRate) {
		this.tickRate = tickRate;
	}
}
