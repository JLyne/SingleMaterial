package uk.co.notnull.SingleMaterial.Commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
            //getCurrentCommandIssuer().send Error here
            return;
        }

        plugin.getMaterialManager().rerollMaterial((Player) sender);
    }
}
