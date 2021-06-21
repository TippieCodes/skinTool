package me.tippie.skintool;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.player.EntityHuman;
import org.bukkit.WorldType;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class v1_17_R1 implements InternalsProvider {
	@Override public void setTextureProperty(final Player player, final String value, final String signature) {
		final EntityHuman entityHuman = ((CraftPlayer) player).getHandle();
		final GameProfile profile = entityHuman.getProfile();
		profile.getProperties().removeAll("textures");
		profile.getProperties().put("textures", new Property("textures", value, signature));
	}

	@Override public void sendReloadSkinForSelfPackets(final Player player) {
		final EntityPlayer ep = ((CraftPlayer) player).getHandle();
		final PacketPlayOutPlayerInfo removeInfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e, ep);
		final PacketPlayOutEntityDestroy removeEntity = new PacketPlayOutEntityDestroy(player.getEntityId());
		final PacketPlayOutNamedEntitySpawn addNamed = new PacketPlayOutNamedEntitySpawn(ep);
		final PacketPlayOutPlayerInfo addInfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, ep);
		final PacketPlayOutRespawn respawn = new PacketPlayOutRespawn(ep.getWorld().getDimensionManager(), ep.getSpawnDimension(), player.getWorld().getSeed(), ep.d.getGameMode(), ep.d.getGameMode(), false, player.getWorld().getWorldType().equals(WorldType.FLAT), false);
		final PacketPlayOutExperience exp = new PacketPlayOutExperience(player.getExp(), player.getTotalExperience(), player.getLevel());
		ep.b.sendPacket(removeInfo);
		ep.b.sendPacket(removeEntity);
		ep.b.sendPacket(addNamed);
		ep.b.sendPacket(addInfo);
		ep.b.sendPacket(respawn);
		ep.b.sendPacket(exp);
		((CraftPlayer) player).sendHealthUpdate();
	}

	@Override public MinecraftSkin getSkin(final Player player) {
		final Property textures = ((CraftPlayer) player).getHandle().getProfile().getProperties().get("textures").iterator().next();
		return new MinecraftSkin(textures.getValue(), textures.getSignature());
	}
}
