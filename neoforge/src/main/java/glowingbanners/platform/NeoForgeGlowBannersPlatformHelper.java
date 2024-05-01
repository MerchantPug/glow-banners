package glowingbanners.platform;

import glowingbanners.registry.GlowBannersAttachmentTypes;
import me.ultrusmods.glowingbanners.component.BannerGlowComponent;
import me.ultrusmods.glowingbanners.network.s2c.SyncBannerGlowS2CPacket;
import me.ultrusmods.glowingbanners.platform.services.IGlowBannersPlatformHelper;
import me.ultrusmods.glowingbanners.registry.GlowBannersDataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

public class NeoForgeGlowBannersPlatformHelper implements IGlowBannersPlatformHelper {
    @Nullable
    @Override
    public BannerGlowComponent getData(BannerBlockEntity blockEntity) {
        return blockEntity.getExistingData(GlowBannersAttachmentTypes.BANNER_GLOW).orElse(null);
    }

    @Override
    public BannerGlowComponent getOrCreateData(BannerBlockEntity blockEntity) {
        return blockEntity.getData(GlowBannersAttachmentTypes.BANNER_GLOW);
    }

    @Override
    public BannerGlowComponent setData(BannerBlockEntity blockEntity, BannerGlowComponent component) {
        return blockEntity.setData(GlowBannersAttachmentTypes.BANNER_GLOW, component);
    }

    @Override
    public void syncBlockEntity(BannerBlockEntity blockEntity) {
        PacketDistributor.sendToPlayersTrackingChunk((ServerLevel) blockEntity.getLevel(), blockEntity.getLevel().getChunkAt(blockEntity.getBlockPos()).getPos(), new SyncBannerGlowS2CPacket(blockEntity.getBlockPos(), blockEntity.getData(GlowBannersAttachmentTypes.BANNER_GLOW)));
        blockEntity.invalidateCapabilities();
    }
}
