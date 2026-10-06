package com.donutsmp;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CoordinateTracker {

	private static final Map<String, PlayerCoordinates> playerCoords = new ConcurrentHashMap<>();

	static class PlayerCoordinates {
		String username;
		double x;
		double y;
		double z;
		long lastUpdated;

		PlayerCoordinates(String username, double x, double y, double z) {
			this.username = username;
			this.x = x;
			this.y = y;
			this.z = z;
			this.lastUpdated = System.currentTimeMillis();
		}

		@Override
		public String toString() {
			return String.format("%s is at X: %.1f Y: %.1f Z: %.1f", username, x, y, z);
		}
	}

	public static void updatePlayerCoordinates(String username, double x, double y, double z) {
		playerCoords.put(username.toLowerCase(), new PlayerCoordinates(username, x, y, z));
	}

	public static PlayerCoordinates getPlayerCoordinates(String username) {
		return playerCoords.get(username.toLowerCase());
	}

	public static Map<String, PlayerCoordinates> getAllCoordinates() {
		return new HashMap<>(playerCoords);
	}

	public static void clearPlayerCoordinates(String username) {
		playerCoords.remove(username.toLowerCase());
	}
}
