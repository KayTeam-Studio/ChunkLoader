/*
 *   Copyright (C) 2021 segu23
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package org.kayteam.chunkloader.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class UpdateChecker {

    private final JavaPlugin javaPlugin;

    private int resourceId;
    private URL resourceURL;
    private String currentVersionString;
    private String latestVersionString;
    private UpdateCheckResult updateCheckResult;

    public UpdateChecker(JavaPlugin javaPlugin, int resourceId) {
        this.javaPlugin = javaPlugin;
        try {
            this.resourceId = resourceId;
            this.resourceURL = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceId);
        } catch (Exception exception) {
            return;
        }

        currentVersionString = javaPlugin.getDescription().getVersion();
        latestVersionString = getLatestVersion();

        if (latestVersionString == null) {
            updateCheckResult = UpdateCheckResult.NO_RESULT;
            return;
        }

        int currentVersion = Integer.parseInt(currentVersionString.replace("v", "").replace(".", ""));
        int latestVersion = Integer.parseInt(getLatestVersion().replace("v", "").replace(".", ""));

        if (currentVersion < latestVersion) updateCheckResult = UpdateCheckResult.OUT_DATED;
        else if (currentVersion == latestVersion) updateCheckResult = UpdateCheckResult.UP_TO_DATE;
        else updateCheckResult = UpdateCheckResult.UNRELEASED;
    }

    public enum UpdateCheckResult {
        NO_RESULT, OUT_DATED, UP_TO_DATE, UNRELEASED,
    }

    public int getResourceId() {
        return resourceId;
    }

    public String getResourceURL() {
        return "https://www.spigotmc.org/resources/" + resourceId;
    }

    public String getCurrentVersionString() {
        return currentVersionString;
    }

    public String getLatestVersionString() {
        return latestVersionString;
    }

    public UpdateCheckResult getUpdateCheckResult() {
        return updateCheckResult;
    }

    public String getLatestVersion() {
        try {
            URLConnection urlConnection = resourceURL.openConnection();
            return new BufferedReader(new InputStreamReader(urlConnection.getInputStream())).readLine();
        } catch (Exception exception) {
            return null;
        }
    }

    public void sendOutDatedMessage(CommandSender commandSender) {
        List<String> message = new ArrayList<>();
        message.add("");
        message.add("&f>>");
        message.add("&f>> &6The plugin " + javaPlugin.getDescription().getName() + "is outdated.");
        message.add("&f>> &6Current Version &f" + javaPlugin.getDescription().getVersion());
        message.add("&f>>");
        message.add("&f>> &6Latest Version &f" + latestVersionString);
        message.add("&f>> &6Download latest version here");
        message.add("&f>> &f" + getResourceURL());
        message.add("&f>>");
        message.add("");
        for (String line : message) {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
        }
    }
}