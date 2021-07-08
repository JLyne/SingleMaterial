package uk.co.notnull.SingleMaterial;

import co.aikar.commands.MessageType;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import uk.co.notnull.SingleMaterial.Commands.SingleMaterialCommand;
import uk.co.notnull.SingleMaterial.Listeners.Blocks;
import uk.co.notnull.SingleMaterial.Listeners.Inventories;
import uk.co.notnull.SingleMaterial.Listeners.JoinLeave;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Locale;
import java.util.Objects;

public class SingleMaterial extends JavaPlugin implements Listener {
	private PaperCommandManager commandManager;
	private MaterialManager materialManager;
	private static SingleMaterial instance;

	public SingleMaterial() {
	    instance = this;
    }

    @Override
    public void onEnable() {
		initConfig();
		createFile("languages/en-US.yml");

		materialManager = new MaterialManager(this, getConfig().getStringList("blacklist"));

		getServer().getPluginManager().registerEvents(new Inventories(), this);
		getServer().getPluginManager().registerEvents(new JoinLeave(this), this);
		getServer().getPluginManager().registerEvents(new Blocks(this), this);

        registerCommands();

        Bukkit.getOnlinePlayers().forEach(player -> {
            if(player.hasPermission("singlematerial.bypass")) {
                return;
            }

            Material assigned = materialManager.assignMaterial(player);

            if(assigned == null) {
                commandManager.formatMessage(
                        commandManager.getCommandIssuer(player), MessageType.ERROR, Messages.MATERIAL__NONE_AVAILABLE);
            } else {
                commandManager.getCommandIssuer(player).sendInfo(Messages.MATERIAL__ASSIGNED, "%material%", assigned.name());
            }
        });
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(player -> player.getInventory().clear());
	    HandlerList.unregisterAll((JavaPlugin) this);
    }

    private void registerCommands() {
        commandManager = new PaperCommandManager(this);
        registerLanguages();

        commandManager.enableUnstableAPI("help");
        commandManager.registerCommand(new SingleMaterialCommand());
    }

    private void initConfig() {
    	getConfig();
        saveDefaultConfig();
	}

	public static SingleMaterial getInstance() {
        return instance;
    }

    public PaperCommandManager getCommandManager() {
        return commandManager;
    }

    public MaterialManager getMaterialManager() {
        return materialManager;
    }

    /**
     * Create a file to be used in the plugin
     * @param name the name of the file
     */
    private void createFile(String name) {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        File languageFolder = new File(getDataFolder(), "languages");
        if (!languageFolder.exists()) {
            languageFolder.mkdirs();
        }

        File file = new File(getDataFolder(), name);

        if (!file.exists()) {
            try (InputStream in = getClassLoader().getResourceAsStream(name)) {
                Files.copy(in, file.toPath());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Load all the language files for the plugin
     */
    public void registerLanguages() {
        try {
            File languageFolder = new File(getDataFolder(), "languages");
            for (File file : Objects.requireNonNull(languageFolder.listFiles())) {
                if (file.isFile()) {
                    if (file.getName().endsWith(".yml")) {
                        String updatedName = file.getName().replace(".yml", "");
                        commandManager.addSupportedLanguage(Locale.forLanguageTag(updatedName));
                        commandManager.getLocales().loadYamlLanguageFile(new File(languageFolder, file.getName()), Locale.forLanguageTag(updatedName));
                    }
                }
            }
            commandManager.getLocales().setDefaultLocale(Locale.forLanguageTag("en-US"));
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
