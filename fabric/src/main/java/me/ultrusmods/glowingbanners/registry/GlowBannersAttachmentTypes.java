package me.ultrusmods.glowingbanners.registry;

import me.ultrusmods.glowingbanners.attachment.BannerGlowAttachment;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;

public class GlowBannersAttachmentTypes {
    public static final AttachmentType<BannerGlowAttachment> BANNER_GLOW = AttachmentRegistry.<BannerGlowAttachment>builder()
            .persistent(BannerGlowAttachment.CODEC)
            .initializer(BannerGlowAttachment::new)
            .buildAndRegister(BannerGlowAttachment.ID);
}
