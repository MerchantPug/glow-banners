package me.ultrusmods.glowingbanners.attachment.capi;

import me.ultrusmods.glowingbanners.attachment.BannerGlowAttachment;
import me.ultrusmods.glowingbanners.platform.services.IGlowBannersPlatformHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

public class BannerGlowItemCapi extends BannerGlowAttachment {
    private static final String ATTACHMENTS_KEY = IGlowBannersPlatformHelper.INSTANCE.getAttachmentsTagKey();
    private final ItemStack provider;

    public BannerGlowItemCapi(ItemStack provider) {
        this.provider = provider;
    }

    @Override
    public boolean shouldAllGlow() {
        return this.getGlowBannerTag().getBoolean("all_glow");
    }

    @Override
    public void setAllGlow(boolean value) {
        this.getGlowBannerTag().putBoolean("all_glow", value);
    }

    @Override
    public boolean isLayerGlowing(int layerIndex) {
        ListTag listTag = this.getGlowBannerTag().getList("glowing_layers", ListTag.TAG_INT);
        return listTag.contains(IntTag.valueOf(layerIndex));
    }

    @Override
    public void addGlowToLayer(int layerIndex) {
        ListTag listTag = this.getGlowBannerTag().getList("glowing_layers", ListTag.TAG_INT).copy();
        IntTag intTag = IntTag.valueOf(layerIndex);
        if (!listTag.contains(intTag))
            listTag.add(intTag);
        listTag.sort(Comparator.comparing(tag -> ((IntTag)tag).getAsInt()));
        this.getGlowBannerTag().put("glowing_layers", listTag);
    }

    @Override
    public void removeGlowFromLayer(int layerIndex) {
        ListTag listTag = this.getGlowBannerTag().getList("glowing_layers", ListTag.TAG_INT).copy();
        listTag.removeIf(tag -> tag == IntTag.valueOf(layerIndex));
        this.getGlowBannerTag().put("glowing_layers", listTag);
    }

    @Override
    public void clearGlowingLayers() {
        this.getGlowBannerTag().remove("glowing_layers");
    }

    @Override
    public Collection<Integer> getGlowingLayers() {
        return this.getGlowBannerTag().getList("glowing_layers", ListTag.TAG_INT).stream().map(tag -> ((IntTag)tag).getAsInt()).collect(Collectors.toSet());
    }

    private CompoundTag getGlowBannerTag() {
        if (!this.provider.getOrCreateTag().contains("BlockEntityTag")) {
            this.provider.getOrCreateTag().put("BlockEntityTag", new CompoundTag());
        }
        if (!BlockItem.getBlockEntityData(this.provider).contains(ATTACHMENTS_KEY)) {
            BlockItem.getBlockEntityData(this.provider).put(ATTACHMENTS_KEY, new CompoundTag());
        }
        if (!BlockItem.getBlockEntityData(this.provider).getCompound(ATTACHMENTS_KEY).contains(BannerGlowAttachment.ID.toString())) {
            this.provider.getOrCreateTag().getCompound("BlockEntityTag").getCompound(ATTACHMENTS_KEY).put(BannerGlowAttachment.ID.toString(), new CompoundTag());
        }
        return this.provider.getOrCreateTag().getCompound("BlockEntityTag").getCompound(ATTACHMENTS_KEY).getCompound(BannerGlowAttachment.ID.toString());
    }
}
