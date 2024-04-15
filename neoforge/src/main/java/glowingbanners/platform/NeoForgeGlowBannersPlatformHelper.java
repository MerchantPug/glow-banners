package glowingbanners.platform;

import com.google.auto.service.AutoService;
import glowingbanners.network.s2c.SyncBannerGlowS2CPacket;
import glowingbanners.registry.GlowBannersAttachmentTypes;
import glowingbanners.registry.GlowBannersCapabilities;
import me.ultrusmods.glowingbanners.attachment.BannerGlowAttachment;
import me.ultrusmods.glowingbanners.platform.services.IGlowBannersPlatformHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.neoforged.neoforge.network.PacketDistributor;

@AutoService(IGlowBannersPlatformHelper.class)
public class NeoForgeGlowBannersPlatformHelper implements IGlowBannersPlatformHelper {
    @Override
    public String getAttachmentsTagKey() {
        return "neoforge:attachments";
    }

    public BannerGlowAttachment getData(BannerBlockEntity blockEntity) {
        return blockEntity.getData(GlowBannersAttachmentTypes.BANNER_GLOW);
    }

    @Override
    public void syncBlockEntity(BannerBlockEntity blockEntity) {
        PacketDistributor.TRACKING_CHUNK.with(blockEntity.getLevel().getChunkAt(blockEntity.getBlockPos())).send(new SyncBannerGlowS2CPacket(blockEntity.getBlockPos(), blockEntity.getData(GlowBannersAttachmentTypes.BANNER_GLOW)));
        blockEntity.invalidateCapabilities();
    }

    public BannerGlowAttachment getData(ItemStack stack) {
        return stack.getCapability(GlowBannersCapabilities.BANNER_GLOW_ITEM);
    }
}
