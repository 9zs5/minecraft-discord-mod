package com.donutsmp.mixin;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.network.ClientPlayNetworkHandler;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
	// Mixin is registered but the main logic is handled in DonutSMPMod via tick events
}