package com.nyfaria.nyfscalendar.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class CalendarItem extends BlockItem implements DyeableLeatherItem {
    public CalendarItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }
    public boolean hasCustomColor(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getTagElement("BlockEntityTag");
        return compoundtag != null && compoundtag.contains("color", 99);
    }

    public int getColor(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getTagElement("BlockEntityTag");
        return compoundtag != null && compoundtag.contains("color", 99) ? compoundtag.getInt("color") : 0xFFFFFF;
    }

    public void clearColor(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getTagElement("BlockEntityTag");
        if (compoundtag != null && compoundtag.contains("color")) {
            compoundtag.remove("color");
        }

    }
    public void setColor(ItemStack pStack, int pColor) {
        pStack.getOrCreateTagElement("BlockEntityTag").putInt("color", pColor);
    }
}
