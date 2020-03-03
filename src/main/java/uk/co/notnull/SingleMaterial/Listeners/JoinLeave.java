package uk.co.notnull.SingleMaterial.Listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import uk.co.notnull.SingleMaterial.SingleMaterial;

public class JoinLeave implements Listener {
    private SingleMaterial plugin;

    public JoinLeave(SingleMaterial plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Material assigned = plugin.getMaterialManager().assignMaterial(player);

        if(assigned == null) {
            player.kickPlayer("No more material types are available.");
        } else {
            player.sendMessage("You have been assigned " + assigned.toString());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        plugin.getMaterialManager().removePlayer(player);
    }
}
