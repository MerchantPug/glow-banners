package glowingbanners;

import glowingbanners.network.s2c.SyncBannerGlowS2CPacket;
import glowingbanners.registry.GlowBannersAttachmentTypes;
import glowingbanners.registry.GlowBannersCapabilities;
import glowingbanners.registry.GlowBannersGlobalLootModifierSerializers;
import glowingbanners.registry.GlowBannersLootConditionTypes;
import me.ultrusmods.glowingbanners.GlowBannersMod;
import me.ultrusmods.glowingbanners.attachment.capi.BannerGlowItemCapi;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.Map;
import java.util.WeakHashMap;

@Mod(GlowBannersMod.MOD_ID)
public class GlowBannersModNeoForge {
    public GlowBannersModNeoForge(IEventBus bus) {
    }

    @Mod.EventBusSubscriber(modid = GlowBannersMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class NeoForgeBusEvents {
        @SubscribeEvent
        public static void registerContents(PlayerInteractEvent.RightClickBlock event) {
            InteractionResult result = GlowBannersMod.interactWithBlock(event.getEntity(), event.getLevel(), event.getHand(), event.getHitVec());
            if (result != InteractionResult.PASS) {
                event.setCancellationResult(result);
                event.setCanceled(true);
            }
        }
    }

    @Mod.EventBusSubscriber(modid = GlowBannersMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {
        @SubscribeEvent
        public static void registerPayload(RegisterPayloadHandlerEvent event) {
            event.registrar(GlowBannersMod.MOD_ID)
                    .versioned("1.0.0")
                    .play(SyncBannerGlowS2CPacket.ID, SyncBannerGlowS2CPacket::read, SyncBannerGlowS2CPacket::handle);
        }

        @SubscribeEvent
        public static void registerContents(RegisterEvent event) {
            if (event.getRegistryKey() == NeoForgeRegistries.Keys.ATTACHMENT_TYPES) {
                GlowBannersAttachmentTypes.registerAll(event::register);
            } else if (event.getRegistryKey() == NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS) {
                GlowBannersGlobalLootModifierSerializers.registerAll(event::register);
            } else if (event.getRegistryKey() == Registries.LOOT_CONDITION_TYPE) {
                GlowBannersLootConditionTypes.registerAll(event::register);
            }
        }

        private static final Map<ItemStack, BannerGlowItemCapi> ITEM_CAPABILITY_CACHE = new WeakHashMap<>(256);

        @SubscribeEvent
        public static void registerCapabilities(RegisterCapabilitiesEvent event) {
            for (Item item : BuiltInRegistries.ITEM) {
                if (item instanceof BannerItem || item instanceof ShieldItem)
                    event.registerItem(
                            GlowBannersCapabilities.BANNER_GLOW_ITEM,
                            (itemStack, aVoid) -> ITEM_CAPABILITY_CACHE.computeIfAbsent(itemStack, stack -> ITEM_CAPABILITY_CACHE.computeIfAbsent(itemStack, BannerGlowItemCapi::new)), item);
            }
        }


        public static void clearCapCache() {
            ITEM_CAPABILITY_CACHE.clear();
        }
    }
}
