package me.tippie.skintool;

import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.logging.Level;

public class SkinTool {
	/**
	 * Internal functions used to make the library version compatible
	 */
	@Getter private static InternalsProvider internals;

	static {
		try {
			final String packageName = SkinTool.class.getPackage().getName();
			final String internalsName = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
			internals = (InternalsProvider) Class.forName(packageName + "." + internalsName).newInstance();
		} catch (final ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException exception) {
			Bukkit.getLogger().log(Level.SEVERE, "SkinTool could not find a valid implementation for this server version.");
		}
	}
}
