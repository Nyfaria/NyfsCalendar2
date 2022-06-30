package com.nyfaria.nyfscalendar.init;

import com.nyfaria.nyfscalendar.block.CalendarBlock;
import com.nyfaria.nyfscalendar.block.entity.CalendarBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.rmi.registry.Registry;

import static com.nyfaria.nyfscalendar.NyfsCalendar.MODID;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MODID);

    public static final RegistryObject<Block> CALENDAR = BLOCKS.register("calendar", () -> new CalendarBlock(BlockBehaviour.Properties.of(Material.WOOL).strength(0.8f)));

    public static final RegistryObject<BlockEntityType<CalendarBlockEntity>> CALENDAR_BLOCK_ENTITY = BLOCK_ENTITIES.register("calendar_block_entity", () -> BlockEntityType.Builder.of(CalendarBlockEntity::new, BlockInit.CALENDAR.get()).build(null));
}
