package me.ultrusmods.glowingbanners.platform;

import com.google.auto.service.AutoService;
import me.ultrusmods.glowingbanners.attachment.BannerGlowAttachment;
import me.ultrusmods.glowingbanners.network.s2c.SyncBannerGlowS2CPacket;
import me.ultrusmods.glowingbanners.platform.services.IGlowBannersPlatformHelper;
import me.ultrusmods.glowingbanners.registry.GlowBannersApis;
import me.ultrusmods.glowingbanners.registry.GlowBannersAttachmentTypes;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerBlockEntity;

@AutoService(IGlowBannersPlatformHelper.class)
public class FabricGlowBannersPlatformHelper implements IGlowBannersPlatformHelper {
    @Override
    public String getAttachmentsTagKey() {
        return "fabric:attachments";
    }

    public BannerGlowAttachment getData(BannerBlockEntity blockEntity) {
        if (blockEntity == null) {
            return null;
        }
        return blockEntity.getAttachedOrCreate(GlowBannersAttachmentTypes.BANNER_GLOW);
    }

    public void syncBlockEntity(BannerBlockEntity blockEntity) {
        for (ServerPlayer player : PlayerLookup.tracking(blockEntity)) {
            ServerPlayNetworking.send(player, new SyncBannerGlowS2CPacket(blockEntity.getBlockPos(), blockEntity.getAttached(GlowBannersAttachmentTypes.BANNER_GLOW)));
        }
    }

    public BannerGlowAttachment getData(ItemStack stack) {
        return GlowBannersApis.BANNER_GLOW_ITEM.find(stack, null);
    }
}
