package uk.co.notnull.SingleMaterial.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class Inventories implements Listener {

    public Inventories() {
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if(!event.getWhoClicked().hasPermission("singlematerial.bypass")
                && event.getClickedInventory() == event.getWhoClicked().getInventory()) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryDragEvent(InventoryDragEvent event) {
        if(!event.getWhoClicked().hasPermission("singlematerial.bypass") &&
                event.getInventory() == event.getWhoClicked().getInventory()) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onDropItem(PlayerDropItemEvent event) {
        if(!event.getPlayer().hasPermission("singlematerial.bypass")) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onDropItem(EntityPickupItemEvent event) {
        if(event.getEntity() instanceof Player && !event.getEntity().hasPermission("singlematerial.bypass")) {
            event.setCancelled(true);
        }
    }
}
