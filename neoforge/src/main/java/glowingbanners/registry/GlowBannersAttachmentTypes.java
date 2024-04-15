package glowingbanners.registry;

import glowingbanners.attachment.SerializableBannerGlowAttachment;
import me.ultrusmods.glowingbanners.attachment.BannerGlowAttachment;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class GlowBannersAttachmentTypes {
    public static final AttachmentType<SerializableBannerGlowAttachment> BANNER_GLOW = AttachmentType.serializable(SerializableBannerGlowAttachment::new).build();

    public static void registerAll(RegistrationCallback<AttachmentType<?>> callback) {
        callback.register(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, BannerGlowAttachment.ID, () -> BANNER_GLOW);
    }
}
