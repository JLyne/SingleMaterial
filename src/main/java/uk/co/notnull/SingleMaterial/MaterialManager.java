package uk.co.notnull.SingleMaterial;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MaterialManager {
	ArrayList<Material> availableMaterials;
	HashMap<Player, Material> assignedMaterials;

	public MaterialManager(SingleMaterial singleMaterial, List<String> blacklist) {
		availableMaterials = new ArrayList<>();
		assignedMaterials = new HashMap<>();
		ArrayList<Material> blacklistedMaterials = new ArrayList<>();

		blacklist.forEach(materialName -> {
			try {
				Material material = Material.valueOf(materialName);
				blacklistedMaterials.add(material);
			} catch (IllegalArgumentException ignored) {
			}
		});

		for(Material material : Material.values()) {
			if(material.isLegacy() || material.isAir()) {
				continue;
			}

			if(!material.isBlock() || !material.isItem()) { //TODO: Whitelist
				continue;
			}

			if(blacklistedMaterials.contains(material)) {
				continue;
			}

			singleMaterial.getLogger().info(material.name());
			availableMaterials.add(material);
		}
	}

	public void fillInventory(Player player) {
		Material assigned = assignedMaterials.get(player);

		if(assigned != null) {
			player.getInventory().clear();
			player.getInventory().addItem(new ItemStack(assigned, 35 * assigned.getMaxStackSize()));
			player.getEquipment().setHelmet(new ItemStack(assigned, 1));
		}
	}

	public Material assignMaterial(Player player) {
		if(availableMaterials.isEmpty()) {
			return null;
		}

		if(player.hasPermission("singlematerial.bypass")) {
			return null;
		}

		Material assigned = availableMaterials.get(new Random().nextInt(availableMaterials.size()));

		availableMaterials.remove(assigned);
		assignedMaterials.put(player, assigned);

		fillInventory(player);

		return assigned;
	}

	public Material rerollMaterial(Player player) {
		if(availableMaterials.isEmpty()) {
			return null;
		}

		Material oldMaterial = assignedMaterials.get(player);
		assignedMaterials.remove(player);
		Material newMaterial = assignMaterial(player);

		availableMaterials.add(oldMaterial);

		return newMaterial;
	}

	public void removePlayer(Player player) {
		Material assigned = assignedMaterials.get(player);

		if(assigned != null) {
			assignedMaterials.remove(player);
			availableMaterials.add(assigned);
		}
	}

	public boolean isAssignedMaterial(Player player, Material material) {
		return player.hasPermission("singlematerial.bypass") || assignedMaterials.get(player).equals(material);
	}
}
