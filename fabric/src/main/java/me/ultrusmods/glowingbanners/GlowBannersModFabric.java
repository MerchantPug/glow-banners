package me.ultrusmods.glowingbanners;

import me.ultrusmods.glowingbanners.attachment.capi.BannerGlowItemCapi;
import me.ultrusmods.glowingbanners.loot.SetBannerGlowFunction;
import me.ultrusmods.glowingbanners.registry.GlowBannersApis;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;

import java.util.Map;
import java.util.WeakHashMap;

public class GlowBannersModFabric implements ModInitializer {
    private static final Map<ItemStack, BannerGlowItemCapi> ITEM_API_CACHE = new WeakHashMap<>(128);

    public static void clearApiCache() {
        ITEM_API_CACHE.clear();
    }

    @Override
    public void onInitialize() {
        Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, SetBannerGlowFunction.ID, SetBannerGlowFunction.TYPE);
        registerApis();
        registerEvents();
    }

    private static void registerApis() {
        for (Item item : BuiltInRegistries.ITEM) {
            if (item instanceof BannerItem || item instanceof ShieldItem)
                GlowBannersApis.BANNER_GLOW_ITEM.registerForItems((itemStack, context) -> ITEM_API_CACHE.computeIfAbsent(itemStack, BannerGlowItemCapi::new), item);
        }
    }

    private static void registerEvents() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (id.getPath().contains("banner") && id.toString().contains("minecraft:blocks/")) {
                tableBuilder.unwrap().modifyPools(pool -> {
                    pool.apply(new SetBannerGlowFunction.Builder().build());
                });
            }
        });

        UseBlockCallback.EVENT.register(GlowBannersMod::interactWithBlock);
    }

}
