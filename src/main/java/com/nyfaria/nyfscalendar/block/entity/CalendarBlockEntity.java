package com.nyfaria.nyfscalendar.block.entity;

import com.nyfaria.nyfscalendar.config.NCConfig;
import com.nyfaria.nyfscalendar.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;

public class CalendarBlockEntity extends BlockEntity {

    private CompoundTag calendarTag;

    public CalendarBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(BlockInit.CALENDAR_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
    }

    public int[] calculateDate() {
        LocalDate currentDate = LocalDate.of(NCConfig.INSTANCE.startYear.get(), NCConfig.INSTANCE.startMonth.get(), NCConfig.INSTANCE.startDay.get()).plusDays(getLevel().getDayTime() / 24000L % 2147483647L);
        return new int[]{currentDate.getMonthValue(), currentDate.getDayOfMonth(), currentDate.getYear()};
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    public void setCalendarTag(CompoundTag calendarTag) {
        this.calendarTag = calendarTag;
        updateBlock();
    }

    public int getColor() {
        return calendarTag != null && calendarTag.contains("color", 99) ? calendarTag.getInt("color") : 0xFFFFFF;
    }
    protected void saveAdditional(CompoundTag pTag) {

        super.saveAdditional(pTag.merge(calendarTag));
    }
    public void load(CompoundTag pTag) {
        this.calendarTag = pTag;
        super.load(pTag);
    }
    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void updateBlock() {
        BlockState blockState = getBlockState();
        this.level.sendBlockUpdated(this.getBlockPos(), blockState, blockState, 3);
        this.setChanged();
    }

}
