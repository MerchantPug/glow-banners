package glowingbanners.registry;

import me.ultrusmods.glowingbanners.attachment.BannerGlowAttachment;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class GlowBannersAttachmentTypes {
    public static final AttachmentType<BannerGlowAttachment> BANNER_GLOW = AttachmentType
            .builder(BannerGlowAttachment::new)
            .serialize(BannerGlowAttachment.CODEC)
            .build();

    public static void registerAll(RegistrationCallback<AttachmentType<?>> callback) {
        callback.register(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, BannerGlowAttachment.ID, () -> BANNER_GLOW);
    }
}
