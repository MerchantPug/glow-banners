package glowingbanners.attachment;

import me.ultrusmods.glowingbanners.GlowBannersMod;
import me.ultrusmods.glowingbanners.attachment.BannerGlowAttachment;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.neoforged.neoforge.common.util.INBTSerializable;

public class SerializableBannerGlowAttachment implements INBTSerializable<Tag> {
    private BannerGlowAttachment attachment;

    public SerializableBannerGlowAttachment() {
        attachment = new BannerGlowAttachment();
    }

    public BannerGlowAttachment getAttachment() {
        return this.attachment;
    }

    @Override
    public Tag serializeNBT() {
        return BannerGlowAttachment.CODEC.encodeStart(NbtOps.INSTANCE, this.attachment).getOrThrow(false, GlowBannersMod.LOGGER::error);
    }

    @Override
    public void deserializeNBT(Tag tag) {
        this.attachment = BannerGlowAttachment.CODEC.decode(NbtOps.INSTANCE, tag).getOrThrow(false, GlowBannersMod.LOGGER::error).getFirst();
    }
}
