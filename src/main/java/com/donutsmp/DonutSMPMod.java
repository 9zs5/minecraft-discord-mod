package com.donutsmp;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class DonutSMPMod implements ClientModInitializer {

	public static final String MOD_ID = "donutsmp-mod";
	private static DiscordWebhookManager webhookManager;
	private static String lastKnownServer = "";
	private static boolean hasNotified = false;

	@Override
	public void onInitializeClient() {
		webhookManager = new DiscordWebhookManager();

		// Register client tick event to check server connection
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.getNetworkHandler() != null && client.getSession() != null) {
				String currentServer = getServerAddress(client);
				String username = client.getSession().getUsername();

				// Check if connected to DonutSMP
				if (isDonutSMPServer(currentServer)) {
					// Notify once per connection
					if (!hasNotified || !currentServer.equals(lastKnownServer)) {
						webhookManager.notifyPlayerJoined(username, currentServer);
						hasNotified = true;
						lastKnownServer = currentServer;
					}
				} else {
					// Left DonutSMP
					if (hasNotified && !currentServer.isEmpty()) {
						webhookManager.notifyPlayerLeft(username);
						hasNotified = false;
					}
				}
			} else {
				// Disconnected
				if (hasNotified) {
					hasNotified = false;
				}
			}
		});
	}

	private static String getServerAddress(MinecraftClient client) {
		try {
			if (client.getCurrentServerEntry() != null) {
				return client.getCurrentServerEntry().address.toLowerCase();
			}
			if (client.getNetworkHandler() != null && client.getNetworkHandler().getServerInfo() != null) {
				return client.getNetworkHandler().getServerInfo().address.toLowerCase();
			}
		} catch (Exception e) {
			// Ignore errors
		}
		return "";
	}

	private static boolean isDonutSMPServer(String address) {
		return address.contains("donutsmp.net") || address.contains("donutsmp") || address.equals("localhost");
	}

	public static DiscordWebhookManager getWebhookManager() {
		return webhookManager;
	}
}