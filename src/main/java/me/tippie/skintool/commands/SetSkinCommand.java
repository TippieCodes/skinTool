package me.tippie.skintool.commands;

import me.tippie.skintool.MinecraftSkin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSkinCommand implements CommandExecutor {
	@Override public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if (!(sender instanceof Player)) return false;
		final MinecraftSkin skin = SaveSkinCommand.getSkinStorage().get(args[0]);
		if (skin == null) {
			sender.sendMessage("Skin couldn't be found in storage!");
		} else {
			skin.set((Player) sender);
			sender.sendMessage("Skin set to "+args[0]+"!");
			System.out.println(skin);
		}
		return true;
	}
}
