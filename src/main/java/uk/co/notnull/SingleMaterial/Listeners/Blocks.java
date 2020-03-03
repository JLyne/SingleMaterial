package uk.co.notnull.SingleMaterial.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import uk.co.notnull.SingleMaterial.SingleMaterial;

public class Blocks implements Listener {
	private final SingleMaterial plugin;

	public Blocks(SingleMaterial plugin) {
		this.plugin = plugin;
	}

	@EventHandler(ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();

		if(!plugin.getMaterialManager().isAssignedMaterial(player, event.getBlockPlaced().getType())) {
			event.setCancelled(true);
		}
	}
}
