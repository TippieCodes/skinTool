package me.tippie.skintool;

import org.bukkit.entity.Player;

public interface InternalsProvider {
	void setTextureProperty(final Player player, final String value, final String signature);

	void sendReloadSkinForSelfPackets(Player player);
}
