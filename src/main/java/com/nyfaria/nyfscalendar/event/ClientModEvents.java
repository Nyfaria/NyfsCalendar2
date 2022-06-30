package com.nyfaria.nyfscalendar.event;

import com.nyfaria.nyfscalendar.NyfsCalendar;
import com.nyfaria.nyfscalendar.block.entity.CalendarBlockEntity;
import com.nyfaria.nyfscalendar.client.renderer.CalendarBlockRenderer;
import com.nyfaria.nyfscalendar.init.BlockInit;
import com.nyfaria.nyfscalendar.init.ItemInit;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NyfsCalendar.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerEnityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockInit.CALENDAR_BLOCK_ENTITY.get(), (a) -> new CalendarBlockRenderer<>());
    }

    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre event) {
        if (!event.getAtlas().location().equals(InventoryMenu.BLOCK_ATLAS)) {
            return;
        }
        event.addSprite(CalendarBlockRenderer.INDICATOR);
    }

    @SubscribeEvent
    public static void onBlockColors(ColorHandlerEvent.Block event) {
        event.getBlockColors().register((pState, pLevel, pPos, pTintIndex) -> {
            CalendarBlockEntity blockEntity = ((CalendarBlockEntity) pLevel.getBlockEntity(pPos));
            if (blockEntity != null) {
                return blockEntity.getColor();
            }
            return 0xFFFFFF;
        }, BlockInit.CALENDAR.get());
    }

    @SubscribeEvent
    public static void onItemColor(ColorHandlerEvent.Item event) {
        event.getItemColors().register((pStack, pTintIndex) -> ((DyeableLeatherItem)pStack.getItem()).getColor(pStack), ItemInit.CALENDAR.get());
    }
}
