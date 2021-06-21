package me.tippie.skintool;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_15_R1.*;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class v1_15_R1 implements InternalsProvider {
	@Override public void setTextureProperty(final Player player, final String value, final String signature) {
		final EntityHuman entityHuman = ((CraftPlayer) player).getHandle();
		final GameProfile profile = entityHuman.getProfile();
		profile.getProperties().removeAll("textures");
		profile.getProperties().put("textures", new Property("textures", value, signature));
	}

	@Override public void sendReloadSkinForSelfPackets(final Player player) {
		final EntityPlayer ep = ((CraftPlayer) player).getHandle();
		final PacketPlayOutPlayerInfo removeInfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, ep);
		final PacketPlayOutEntityDestroy removeEntity = new PacketPlayOutEntityDestroy(new int[]{player.getEntityId()});
		final PacketPlayOutNamedEntitySpawn addNamed = new PacketPlayOutNamedEntitySpawn(ep);
		final PacketPlayOutPlayerInfo addInfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ep);
		final PacketPlayOutRespawn respawn = new PacketPlayOutRespawn(ep.dimension, player.getWorld().getSeed(), ep.getWorld().getWorldData().getType(), ep.playerInteractManager.getGameMode());
		final PacketPlayOutExperience exp = new PacketPlayOutExperience(player.getExp(), player.getTotalExperience(), player.getLevel());
		ep.playerConnection.sendPacket(removeInfo);
		ep.playerConnection.sendPacket(removeEntity);
		ep.playerConnection.sendPacket(addNamed);
		ep.playerConnection.sendPacket(addInfo);
		ep.playerConnection.sendPacket(respawn);
		ep.playerConnection.sendPacket(exp);
		((CraftPlayer) player).sendHealthUpdate();
	}
}
