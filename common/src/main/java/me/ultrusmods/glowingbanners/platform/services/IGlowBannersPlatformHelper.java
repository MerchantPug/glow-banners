package me.ultrusmods.glowingbanners.platform.services;

import me.ultrusmods.glowingbanners.component.BannerGlowComponent;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import org.jetbrains.annotations.Nullable;

public interface IGlowBannersPlatformHelper {
    @Nullable
    BannerGlowComponent getData(BannerBlockEntity blockEntity);

    BannerGlowComponent getOrCreateData(BannerBlockEntity blockEntity);

    BannerGlowComponent setData(BannerBlockEntity blockEntity, BannerGlowComponent component);

    BannerGlowComponent removeData(BannerBlockEntity blockEntity);

    void syncBlockEntity(BannerBlockEntity blockEntity);
}
