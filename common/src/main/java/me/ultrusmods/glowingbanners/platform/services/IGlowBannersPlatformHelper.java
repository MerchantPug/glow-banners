package me.ultrusmods.glowingbanners.platform.services;

import me.ultrusmods.glowingbanners.attachment.BannerGlowAttachment;
import me.ultrusmods.glowingbanners.platform.ServiceUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerBlockEntity;

public interface IGlowBannersPlatformHelper {
    IGlowBannersPlatformHelper INSTANCE = ServiceUtil.load(IGlowBannersPlatformHelper.class);

    String getAttachmentsTagKey();
    BannerGlowAttachment getData(BannerBlockEntity blockEntity);
    void syncBlockEntity(BannerBlockEntity blockEntity);
    BannerGlowAttachment getData(ItemStack stack);
}
