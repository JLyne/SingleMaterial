package uk.co.notnull.SingleMaterial.Commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.co.notnull.SingleMaterial.Messages;
import uk.co.notnull.SingleMaterial.SingleMaterial;

import java.util.UUID;

@CommandAlias("singlematerial|sm|smaterial")
public class SingleMaterialCommand extends BaseCommand {
    @Dependency
    private SingleMaterial plugin;

    @Subcommand("reroll")
    @Description("Reroll your randomly assigned block type")
    public void onReroll(CommandSender sender) {
        if(sender.hasPermission("singlematerial.bypass")) {
            getCurrentCommandIssuer().sendError(Messages.MATERIAL__BYPASSED);
            return;
        }

        Material assigned = plugin.getMaterialManager().rerollMaterial((Player) sender);
        getCurrentCommandIssuer().sendInfo(Messages.MATERIAL__REROLLED, "%material%", assigned.name());
    }
}
