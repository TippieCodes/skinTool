package me.tippie.skintool;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Represents a minecraft skin with the base64 encoded value & signature belonging to it.
 */
public class MinecraftSkin {
	private final String value, signature;

	/**
	 * Creates a new a new Minecraft Skin Class used by several functions in this libary.
	 *
	 * @param value     the base64 value of this skin
	 * @param signature the signature belonging to the base64 value of this skin
	 */
	public MinecraftSkin(final String value, final String signature) {
		this.value = value;
		this.signature = signature;
	}

	/**
	 * Gets the base64 encoded value of this minecraft skin
	 *
	 * @return the base64 encoded value of this minecraft skin
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Gets the signature belonging to the base64 value of this minecraft skin (if set correctly)
	 *
	 * @return the signature belonging to the base64 value of this minecraft skin
	 */
	public String getSignature() {
		return signature;
	}

	/**
	 * Sets this skin for a player
	 *
	 * @param player the player that should get this skin
	 * @param plugin your plugin
	 */
	public void set(final Player player, final Plugin plugin) {
		SkinTool.getInternals().setTextureProperty(player, value, signature);
		for (final Player p : Bukkit.getOnlinePlayers()) {
			p.hidePlayer(plugin, player);
			p.showPlayer(plugin, player);
		}
		reloadSkinForSelf(player);
	}

	/**
	 * Gets the {@link MinecraftSkin} the player currently has
	 *
	 * @param player the {@link Player} what the minecraft skin should be get from
	 * @return the minecraft skin the player is currently wearing
	 */
	public static MinecraftSkin get(final Player player) {
		return null;
	}

	/**
	 * Reloads the skin the player is currently seeing
	 *
	 * @param player the player who should be reloaded
	 */
	public static void reloadSkinForSelf(final Player player) {
		final int slot = player.getInventory().getHeldItemSlot();
		final Location loc = player.getLocation().clone();
		SkinTool.getInternals().sendReloadSkinForSelfPackets(player);
		player.updateInventory();
		player.teleport(loc);
		player.getInventory().setHeldItemSlot(slot);
	}

	/**
	 * String of this object
	 *
	 * @return the string of this object
	 */
	@Override public String toString() {
		return "MinecraftSkin{" +
				"value='" + value + '\'' +
				", signature='" + signature + '\'' +
				'}';
	}
}
