package pl.eastwestfm.eaststone.util;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import pl.eastwestfm.eaststone.StonePlugin;

public class Config {

	private FileConfiguration f;

	public String databaseMode;
	public String databaseName;
	public int databaseAutoSaveTime;
	
	public int generatorRegenTime;
	public int generatorDurability;
	
	public String craftingItem;
	public String craftingName;
	public List<String> craftingLore;
	public int craftingSlot_1;
	public int craftingSlot_2;
	public int craftingSlot_3;
	public int craftingSlot_4;
	public int craftingSlot_5;
	public int craftingSlot_6;
	public int craftingSlot_7;
	public int craftingSlot_8;
	public int craftingSlot_9;
	
	public boolean breakingEnable;
	public String breakingItem;
	public boolean breakingDrop;
	
	public String messageCreate;
	public String messageDelete;
	public String messageCantCreate;
	public String messageEndGenerator;
	
	public Config() {
		this.f = StonePlugin.getInst().getConfig();
		loadConfig();
	}
	
	public void loadConfig() {
		
		this.databaseMode = f.getString("database.mode");
		this.databaseName = f.getString("database.name");
		this.databaseAutoSaveTime = f.getInt("database.auto-save-time");
		
		this.generatorRegenTime = f.getInt("generator.regen-time");
		this.generatorDurability = f.getInt("generator.durability");
		
		this.craftingItem = f.getString("crafting.item");
		this.craftingName = f.getString("crafting.name");
		this.craftingLore = f.getStringList("crafting.lore");
		this.craftingSlot_1 = f.getInt("crafting.slots.slot-1");
		this.craftingSlot_2 = f.getInt("crafting.slots.slot-2");
		this.craftingSlot_3 = f.getInt("crafting.slots.slot-3");
		this.craftingSlot_4 = f.getInt("crafting.slots.slot-4");
		this.craftingSlot_5 = f.getInt("crafting.slots.slot-5");
		this.craftingSlot_6 = f.getInt("crafting.slots.slot-6");
		this.craftingSlot_7 = f.getInt("crafting.slots.slot-7");
		this.craftingSlot_8 = f.getInt("crafting.slots.slot-8");
		this.craftingSlot_9 = f.getInt("crafting.slots.slot-9");
		
		this.breakingEnable = f.getBoolean("breaking.enabled");
		this.breakingItem = f.getString("breaking.item");
		this.breakingDrop = f.getBoolean("breaking.drop");
		
		this.messageCreate = f.getString("messages.create");
		this.messageDelete = f.getString("messages.delete");
		this.messageCantCreate = f.getString("messages.cant-create");
		this.messageEndGenerator = f.getString("messages.end-generator");
		
	}
	
	public void saveConfig() {
		
		f.set("database.mode", this.databaseMode);
		f.set("database.name", this.databaseName);
		f.set("database.auto-save-time", this.databaseAutoSaveTime);
		
		f.set("generator.regen-time", this.generatorRegenTime);
		f.set("generator.durability", this.generatorDurability);
		
		f.set("crafting.item", this.craftingItem);
		f.set("crafting.name", this.craftingName);
		f.set("crafting.lore", this.craftingLore);
		f.set("crafting.slots.slot-1", this.craftingSlot_1);
		f.set("crafting.slots.slot-2", this.craftingSlot_2);
		f.set("crafting.slots.slot-3", this.craftingSlot_3);
		f.set("crafting.slots.slot-4", this.craftingSlot_4);
		f.set("crafting.slots.slot-5", this.craftingSlot_5);
		f.set("crafting.slots.slot-6", this.craftingSlot_6);
		f.set("crafting.slots.slot-7", this.craftingSlot_7);
		f.set("crafting.slots.slot-8", this.craftingSlot_8);
		f.set("crafting.slots.slot-9", this.craftingSlot_9);
		
		f.set("breaking.enabled", this.breakingEnable);
		f.set("breaking.item", this.breakingItem);
		f.set("breaking.drop", this.breakingDrop);
		
		f.set("messages.create", this.messageCreate);
		f.set("messages.delete", this.messageDelete);
		f.set("messages.cant-create", this.messageCantCreate);
		f.set("messages.end-generator", this.messageEndGenerator);
		
		StonePlugin.getInst().saveConfig();

	}
	
	public void reloadConfig() {
		StonePlugin.getInst().reloadConfig();
		loadConfig();
		saveConfig();
	}
	
}
