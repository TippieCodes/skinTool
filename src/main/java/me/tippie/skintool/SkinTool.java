package me.tippie.skintool;

import me.tippie.skintool.commands.GetSkinCommand;
import me.tippie.skintool.commands.SaveSkinCommand;
import me.tippie.skintool.commands.SetSkinCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class SkinTool extends JavaPlugin {
	private static SkinTool instance;
	@Override
	public void onEnable() {
		this.getCommand("getskin").setExecutor(new GetSkinCommand());
		this.getCommand("saveskin").setExecutor(new SaveSkinCommand());
		this.getCommand("setskin").setExecutor(new SetSkinCommand());
		instance = this;
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
		// do nothing
	}

	public static SkinTool getInstance() {
		return instance;
	}
}
