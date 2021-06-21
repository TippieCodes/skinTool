package me.tippie.skintool;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.WorldType;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class v1_16_R3 implements InternalsProvider {
	@Override public void setTextureProperty(final Player player, final String value, final String signature) {
		final EntityHuman entityHuman = ((CraftPlayer) player).getHandle();
		final GameProfile profile = entityHuman.getProfile();
		profile.getProperties().removeAll("textures");
		profile.getProperties().put("textures", new Property("textures", value, signature));
	}

	@Override public void sendReloadSkinForSelfPackets(final Player player) {
		final EntityPlayer ep = ((CraftPlayer) player).getHandle();
		final PacketPlayOutPlayerInfo removeInfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, ep);
		final PacketPlayOutEntityDestroy removeEntity = new PacketPlayOutEntityDestroy(player.getEntityId());
		final PacketPlayOutNamedEntitySpawn addNamed = new PacketPlayOutNamedEntitySpawn(ep);
		final PacketPlayOutPlayerInfo addInfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ep);
		final PacketPlayOutRespawn respawn = new PacketPlayOutRespawn(ep.getWorld().getDimensionManager(), ep.getSpawnDimension(), player.getWorld().getSeed(), ep.playerInteractManager.getGameMode(), ep.playerInteractManager.getGameMode(), false, player.getWorld().getWorldType().equals(WorldType.FLAT), false);
		final PacketPlayOutExperience exp = new PacketPlayOutExperience(player.getExp(), player.getTotalExperience(), player.getLevel());
		ep.playerConnection.sendPacket(removeInfo);
		ep.playerConnection.sendPacket(removeEntity);
		ep.playerConnection.sendPacket(addNamed);
		ep.playerConnection.sendPacket(addInfo);
		ep.playerConnection.sendPacket(respawn);
		ep.playerConnection.sendPacket(exp);
		((CraftPlayer) player).sendHealthUpdate();
	}

	@Override public MinecraftSkin getSkin(final Player player) {
		final Property textures = ((CraftPlayer) player).getHandle().getProfile().getProperties().get("textures").iterator().next();
		return new MinecraftSkin(textures.getValue(), textures.getSignature());
	}
}
