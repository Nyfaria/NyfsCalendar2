package com.nyfaria.nyfscalendar.block;

import com.nyfaria.nyfscalendar.block.entity.CalendarBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class CalendarBlock extends Block implements EntityBlock {
    private static VoxelShape SHAPE = Shapes.box(0, 0, 0, 1, 1, 0.125);
    public CalendarBlock(Properties p_49224_) {
        super(p_49224_);
        this.registerDefaultState(this.stateDefinition.any().setValue(BlockStateProperties.FACING, Direction.NORTH));
    }


    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }


    public boolean triggerEvent(BlockState pState, Level pLevel, BlockPos pPos, int pId, int pParam) {
        super.triggerEvent(pState, pLevel, pPos, pId, pParam);
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        return blockentity == null ? false : blockentity.triggerEvent(pId, pParam);
    }

    @Nullable
    public MenuProvider getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos) {
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        return blockentity instanceof MenuProvider ? (MenuProvider)blockentity : null;
    }

    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> p_152133_, BlockEntityType<E> p_152134_, BlockEntityTicker<? super E> p_152135_) {
        return p_152134_ == p_152133_ ? (BlockEntityTicker<A>)p_152135_ : null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CalendarBlockEntity(pPos, pState);
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        int boop;
        switch(pContext.getHorizontalDirection()) {
            case NORTH:
                boop = 0;
                break;
            case SOUTH:
                boop = 8;
                break;
            case WEST:
                boop = 12;
                break;
            case EAST:
                boop = 4;
                break;
            default:
                boop = 0;
        }
        return defaultBlockState().setValue(BlockStateProperties.FACING, pContext.getNearestLookingDirection()).setValue(BlockStateProperties.ROTATION_16, boop);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(BlockStateProperties.FACING).add(BlockStateProperties.ROTATION_16);
    }

    @Override
    public boolean hasDynamicShape() {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if(pState.getValue(BlockStateProperties.FACING) == Direction.DOWN) {
            SHAPE = Shapes.box(0, 0, 0, 1, 0.125, 1);
        }
        if(pState.getValue(BlockStateProperties.FACING) == Direction.UP) {
            SHAPE = Shapes.box(0, 0.875, 0, 1, 1, 1);
        }


        if(pState.getValue(BlockStateProperties.FACING) == Direction.EAST) {
            SHAPE = Shapes.box(0.875, 0, 0, 1, 1, 1);
        }
        if(pState.getValue(BlockStateProperties.FACING) == Direction.WEST) {
            SHAPE = Shapes.box(0, 0, 0, 0.125, 1, 1);
        }

        if(pState.getValue(BlockStateProperties.FACING) == Direction.NORTH) {
            SHAPE = Shapes.box(0, 0, 0, 1, 1, 0.125);
        }
        if(pState.getValue(BlockStateProperties.FACING) == Direction.SOUTH) {
            SHAPE = Shapes.box(0, 0, 0.875, 1, 1, 1);
        }

        return SHAPE;
    }
}
