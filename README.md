# DonutSMP Mod

A Fabric mod for Minecraft 1.21 that sends Discord notifications when you connect to donutsmp.net

## Features

✅ Automatically detects when you connect to donutsmp.net
✅ Sends Discord webhook notification: "(username) has connected to donutsmp"
✅ Sends notification when you leave the server
✅ Lightweight and non-intrusive
✅ Works with Fabric 1.21

## Setup Instructions

### Step 1: Create Discord Webhook

1. Go to your Discord server
2. Click on **Server Settings** (gear icon)
3. Navigate to **Integrations** → **Webhooks**
4. Click **New Webhook**
5. Give it a name (e.g., "DonutSMP Bot")
6. Select the channel where you want notifications
7. Click **Copy Webhook URL**

### Step 2: Configure the Mod

1. Open `src/main/java/com/donutsmp/DiscordWebhookManager.java`
2. Find this line:
   ```java
   private static final String DISCORD_WEBHOOK_URL = "https://discord.com/api/webhooks/YOUR_WEBHOOK_ID/YOUR_WEBHOOK_TOKEN";
   ```
3. Replace `YOUR_WEBHOOK_ID/YOUR_WEBHOOK_TOKEN` with your actual webhook URL
4. Save the file

### Step 3: Build the Mod

**On Windows:**
```bash
gradlew.bat build
```

**On Mac/Linux:**
```bash
./gradlew build
```

The build will take a few minutes. You'll see: `BUILD SUCCESSFUL`

### Step 4: Install the Mod

1. Locate your `.minecraft` folder:
   - **Windows:** `%APPDATA%\.minecraft`
   - **Mac:** `~/Library/Application Support/minecraft`
   - **Linux:** `~/.minecraft`

2. Go to the `mods` folder (create it if it doesn't exist)

3. Copy the built JAR file from:
   ```
   build/libs/donutsmp-mod-1.0.0.jar
   ```
   to your:
   ```
   .minecraft/mods/
   ```

### Step 5: Launch Minecraft

1. Make sure you have **Fabric Loader** installed for Minecraft 1.21
2. Open Minecraft with the Fabric profile
3. Connect to `donutsmp.net`
4. You should see a Discord notification: ":green_circle: **YourUsername** has connected to donutsmp"

## How It Works

- The mod runs in the background and checks every game tick (20 times per second)
- When it detects you've connected to donutsmp.net, it sends a webhook notification to Discord
- When you leave or disconnect, it sends a "left" notification
- The notification only sends once per connection
- No commands are executed, no data is collected beyond your username and connection status

## Troubleshooting

### Webhook Not Working
- Check that the webhook URL is correctly copied (should be long and contain `/api/webhooks/`)
- Make sure the Discord channel still exists
- Check your Discord webhook hasn't been deleted
- Look in the Minecraft logs for error messages (bottom right corner)

### Notifications Not Appearing
- Make sure you're actually connected to `donutsmp.net` (check the F3 screen or server list)
- Give the mod a few seconds after connecting (it checks every second)
- Check that Fabric Loader is properly installed
- Try reconnecting to the server

### Server Address Not Recognized
- The mod checks for "donutsmp.net" or "donutsmp" in the server address
- If the server uses a different address, edit line 35 in `DonutSMPMod.java`

## Building from Source

Requirements:
- Java 21 or higher
- Gradle (included with the project)

```bash
./gradlew clean build
```

Output: `build/libs/donutsmp-mod-1.0.0.jar`

## File Structure

```
.
├── build.gradle                          # Build configuration
├── gradle.properties                     # Version info
├── src/main/java/com/donutsmp/
│   ├── DonutSMPMod.java                 # Main mod entry point
│   ├── DiscordWebhookManager.java       # Webhook handler
│   └── mixin/
│       └── ClientPlayNetworkHandlerMixin.java
└── src/main/resources/
    ├── fabric.mod.json                  # Mod metadata
    └── donutsmp.mixins.json            # Mixin config
```

## License

MIT
