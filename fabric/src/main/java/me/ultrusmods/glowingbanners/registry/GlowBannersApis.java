package me.ultrusmods.glowingbanners.registry;

import me.ultrusmods.glowingbanners.GlowBannersMod;
import me.ultrusmods.glowingbanners.attachment.capi.BannerGlowItemCapi;
import net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup;

public class GlowBannersApis {

    public static final ItemApiLookup<BannerGlowItemCapi, Void> BANNER_GLOW_ITEM =
            ItemApiLookup.get(GlowBannersMod.asResource("banner_glow"), BannerGlowItemCapi.class, Void.class);
}
