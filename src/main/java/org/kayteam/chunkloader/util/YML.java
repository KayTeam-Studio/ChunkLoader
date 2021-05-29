package org.kayteam.chunkloader.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.kayteam.chunkloader.main.ChunkLoader;

import java.io.*;

public class YML {
	private ChunkLoader plugin;
	private FileConfiguration ConfigFile;
	private java.io.File File;
	private String Name;

	public YML(ChunkLoader plugin, String name) {
		this.plugin = plugin;
		this.Name = name+".yml";
	}

	public FileConfiguration getFile() {
		if (ConfigFile == null) {
			reloadFile();
		}
		return ConfigFile;
	}

	public void reloadFile() {
		if (ConfigFile == null) {
			File = new File(plugin.getDataFolder(), Name);
		}

		ConfigFile = YamlConfiguration.loadConfiguration(File);
		Reader Stream;

		try {
			Stream = new InputStreamReader(plugin.getResource(Name), "UTF8");
			if (Stream != null) {
				YamlConfiguration Config = YamlConfiguration.loadConfiguration(Stream);
				ConfigFile.setDefaults(Config);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public void saveFile() {
		try {
			ConfigFile.save(File);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void registerFile() {
		File = new File(plugin.getDataFolder(), Name);
		if (!File.exists()) {
			this.getFile().options().copyDefaults(true);
			saveFile();
		}
	}
}
