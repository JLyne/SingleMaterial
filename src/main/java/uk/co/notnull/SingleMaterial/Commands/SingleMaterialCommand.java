package uk.co.notnull.SingleMaterial.Commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.co.notnull.SingleMaterial.Messages;
import uk.co.notnull.SingleMaterial.SingleMaterial;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;

@CommandAlias("singlematerial|sm|smaterial")
public class SingleMaterialCommand extends BaseCommand {
    @Dependency
    private SingleMaterial plugin;

    private final HashMap<Player, Instant> lastReroll;

    public SingleMaterialCommand() {
        lastReroll = new HashMap<>();
    }

    @Subcommand("reroll")
    @Description("Reroll your randomly assigned block type")
    public void onReroll(CommandSender sender) {
        if(sender.hasPermission("singlematerial.bypass")) {
            getCurrentCommandIssuer().sendError(Messages.MATERIAL__BYPASSED);
            return;
        }

        Instant last = lastReroll.getOrDefault((Player) sender, Instant.EPOCH);

        if(last.isAfter(Instant.now().minusSeconds(120))) {
            Instant ready = last.plusSeconds(120);
            Duration remaining = Duration.between(Instant.now(), ready);
            getCurrentCommandIssuer().sendError(Messages.MATERIAL__COOLDOWN, "%seconds%", String.valueOf(remaining.getSeconds()));
            return;
        }

        Material assigned = plugin.getMaterialManager().rerollMaterial((Player) sender);
        getCurrentCommandIssuer().sendInfo(Messages.MATERIAL__REROLLED, "%material%", assigned.name());
        lastReroll.put((Player) sender, Instant.now());
    }
}
