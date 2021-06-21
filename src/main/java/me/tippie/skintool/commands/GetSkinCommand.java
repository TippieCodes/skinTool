package me.tippie.skintool.commands;

import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_13_R2.EntityPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class GetSkinCommand implements CommandExecutor {
	@Override public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if (!(sender instanceof Player)) return false;
		final Player player = (Player) sender;
		final EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
		final Property texture = entityPlayer.getProfile().getProperties().get("textures").iterator().next();
		player.sendMessage("value="+texture.getValue());
		player.sendMessage("signature="+texture.getSignature());
		return true;
	}
}
