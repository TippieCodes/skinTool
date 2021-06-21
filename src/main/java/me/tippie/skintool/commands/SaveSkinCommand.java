package me.tippie.skintool.commands;

import com.mojang.authlib.properties.Property;
import me.tippie.skintool.MinecraftSkin;
import net.minecraft.server.v1_13_R2.EntityHuman;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class SaveSkinCommand implements CommandExecutor {
	private static final HashMap<String, MinecraftSkin> skinStorage = new HashMap();

	@Override public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if (!(sender instanceof Player)) return false;
		final Player player = (Player) sender;
		final EntityHuman entityHuman = ((CraftPlayer) player).getHandle();
		final Property texture = (Property) entityHuman.getProfile().getProperties().get("textures").toArray()[0];
		final MinecraftSkin skin = new MinecraftSkin(texture.getValue(), texture.getSignature());
		skinStorage.put(args[0], skin);
		System.out.println(skin);
		player.sendMessage("Skin saved!");
		return true;
	}

	public static HashMap<String, MinecraftSkin> getSkinStorage() {
		return skinStorage;
	}
}
