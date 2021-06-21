package me.tippie.skintool;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_13_R2.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class MinecraftSkin {
	private final String value, signature;
	public MinecraftSkin(final String value, final String signature){
		this.value = value;
		this.signature = signature;
	}

	public String getValue() {
		return value;
	}

	public String getSignature() {
		return signature;
	}

	public void set(final Player player){
		final EntityHuman entityHuman = ((CraftPlayer) player).getHandle();
		final GameProfile profile = entityHuman.getProfile();
		profile.getProperties().removeAll("textures");
		profile.getProperties().put("textures", new Property("textures",value,signature));
		for (final Player p : Bukkit.getOnlinePlayers()) {
			p.hidePlayer(SkinTool.getInstance(), player);
			p.showPlayer(SkinTool.getInstance(), player);
		}
		reloadSkinForSelf(player);
	}

	@Override public String toString() {
		return "MinecraftSkin{" +
				"value='" + value + '\'' +
				", signature='" + signature + '\'' +
				'}';
	}

	private static void reloadSkinForSelf(final Player player) {
		final int slot = player.getInventory().getHeldItemSlot();
		final EntityPlayer ep = ((CraftPlayer) player).getHandle();
		final Location loc = player.getLocation().clone();
		final PacketPlayOutPlayerInfo removeInfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, ep);
		final PacketPlayOutEntityDestroy removeEntity = new PacketPlayOutEntityDestroy(new int[] { player.getEntityId() });
		final PacketPlayOutNamedEntitySpawn addNamed = new PacketPlayOutNamedEntitySpawn(ep);
		final PacketPlayOutPlayerInfo addInfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ep);
		final PacketPlayOutRespawn respawn = new PacketPlayOutRespawn(ep.dimension, ep.getWorld().getDifficulty(), ep.getWorld().getWorldData().getType(), ep.playerInteractManager.getGameMode());
		final PacketPlayOutExperience exp = new PacketPlayOutExperience(player.getExp(),player.getTotalExperience(),player.getLevel());
		ep.playerConnection.sendPacket(removeInfo);
		ep.playerConnection.sendPacket(removeEntity);
		ep.playerConnection.sendPacket(addNamed);
		ep.playerConnection.sendPacket(addInfo);
		ep.playerConnection.sendPacket(respawn);
		ep.playerConnection.sendPacket(exp);
		player.updateInventory();
		((CraftPlayer) player).sendHealthUpdate();
		player.teleport(loc);
		player.getInventory().setHeldItemSlot(slot);
	}
}
