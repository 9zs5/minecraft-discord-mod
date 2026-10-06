package com.donutsmp;

import com.google.gson.JsonObject;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DiscordWebhookManager {

	// CONFIGURE YOUR WEBHOOK URL HERE
	// Get this from Discord: Server Settings > Webhooks > New Webhook
	// Copy the URL and paste it below
	private static final String DISCORD_WEBHOOK_URL = "https://discord.com/api/webhooks/YOUR_WEBHOOK_ID/YOUR_WEBHOOK_TOKEN";

	public void notifyPlayerJoined(String username, String server) {
		String message = String.format(":green_circle: **%s** has connected to donutsmp", username);
		sendWebhookMessage(message);
	}

	public void notifyPlayerLeft(String username) {
		String message = String.format(":red_circle: **%s** has left donutsmp", username);
		sendWebhookMessage(message);
	}

	public void sendWebhookMessage(String content) {
		new Thread(() -> {
			try {
				URL url = new URL(DISCORD_WEBHOOK_URL);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setDoOutput(true);

				JsonObject json = new JsonObject();
				json.addProperty("content", content);

				String payload = json.toString();
				byte[] postData = payload.getBytes(StandardCharsets.UTF_8);

				try (OutputStream os = conn.getOutputStream()) {
					os.write(postData);
				}

				int responseCode = conn.getResponseCode();
				if (responseCode == 200 || responseCode == 204) {
					System.out.println("[DonutSMP] Webhook notification sent");
				} else {
					System.err.println("[DonutSMP] Webhook error: " + responseCode);
				}

				conn.disconnect();
			} catch (Exception e) {
				System.err.println("[DonutSMP] Failed to send webhook: " + e.getMessage());
				e.printStackTrace();
			}
		}).start();
	}
}