package com.nyfaria.nyfscalendar.init;

import com.nyfaria.nyfscalendar.NyfsCalendar;
import com.nyfaria.nyfscalendar.block.CalendarBlock;
import com.nyfaria.nyfscalendar.item.CalendarItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NyfsCalendar.MODID);

    public static final RegistryObject<Item> CALENDAR = ITEMS.register("calendar", () -> new CalendarItem(BlockInit.CALENDAR.get(),new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
}
