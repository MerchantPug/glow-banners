package me.ultrusmods.glowingbanners.network;

import me.ultrusmods.glowingbanners.network.s2c.SyncBannerGlowS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class GlowBannersPackets {
    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(SyncBannerGlowS2CPacket.TYPE, (packet, player, responseSender) -> packet.handle());
    }
}
