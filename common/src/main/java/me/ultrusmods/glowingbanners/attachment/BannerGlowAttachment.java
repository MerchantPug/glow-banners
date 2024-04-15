package me.ultrusmods.glowingbanners.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.ultrusmods.glowingbanners.GlowBannersMod;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class BannerGlowAttachment {
    public static final ResourceLocation ID = GlowBannersMod.asResource("banner_glow");
    public static final Codec<BannerGlowAttachment> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Codec.BOOL.optionalFieldOf("all_glow", false).forGetter(BannerGlowAttachment::shouldAllGlow),
            Codec.list(Codec.INT).optionalFieldOf("glowing_layers", List.of()).xmap(Collections::unmodifiableCollection, List::copyOf).forGetter(BannerGlowAttachment::getGlowingLayers)
    ).apply(inst, BannerGlowAttachment::new));

    private boolean allGlow;
    private final SortedSet<Integer> glowingLayers = new TreeSet<>(Integer::compare);

    public BannerGlowAttachment() {
        this(false, List.of());
    }

    public BannerGlowAttachment(boolean allGlow, Collection<Integer> glowingLayers) {
        this.allGlow = allGlow;
        this.glowingLayers.addAll(glowingLayers);
    }

    public boolean shouldAllGlow() {
        return this.allGlow;
    }

    public void setAllGlow(boolean value) {
        this.allGlow = value;
    }

    public boolean isLayerGlowing(int layerIndex) {
        return glowingLayers.contains(layerIndex);
    }

    public void addGlowToLayer(int layerIndex) {
        this.glowingLayers.add(layerIndex);
    }

    public void removeGlowFromLayer(int layerIndex) {
        this.glowingLayers.remove(layerIndex);
    }

    public void clearGlowingLayers() {
        this.glowingLayers.clear();
    }

    public Collection<Integer> getGlowingLayers() {
        return Collections.unmodifiableCollection(glowingLayers);
    }

    public void setGlowingLayers(Collection<Integer> value) {
        this.clearGlowingLayers();
        for (int val : value.stream().sorted(Integer::compareTo).toList()) {
            this.addGlowToLayer(val);
        }
    }

    public void setFromOther(BannerGlowAttachment other) {
        this.setAllGlow(other.shouldAllGlow());
        this.setGlowingLayers(other.getGlowingLayers());
    }
}
