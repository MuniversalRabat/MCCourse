package com.muniversalarts.mccourse.block;

import com.muniversalarts.mccourse.container.ElectrifierContainer;
import com.muniversalarts.mccourse.tileentity.ElectrifierTile;
import com.muniversalarts.mccourse.tileentity.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import javax.swing.*;
import java.util.stream.Stream;

public class ElectrifierBlock extends Block {
    private static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.makeCuboidShape(0, 2, 0, 2, 14, 2),
            Block.makeCuboidShape(14, 2, 0, 16, 14, 2),
            Block.makeCuboidShape(0, 0, 0, 2, 2, 2),
            Block.makeCuboidShape(14, 0, 0, 16, 2, 2),
            Block.makeCuboidShape(0, 0, 14, 2, 2, 16),
            Block.makeCuboidShape(14, 0, 14, 16, 2, 16),
            Block.makeCuboidShape(0, 14, 0, 2, 16, 2),
            Block.makeCuboidShape(14, 14, 0, 16, 16, 2),
            Block.makeCuboidShape(0, 14, 14, 2, 16, 16),
            Block.makeCuboidShape(14, 14, 14, 16, 16, 16),
            Block.makeCuboidShape(0, 2, 15, 1, 14, 16),
            Block.makeCuboidShape(15, 2, 15, 16, 14, 16),
            Block.makeCuboidShape(1, 2, 1, 15, 14, 15),
            Block.makeCuboidShape(2, 14, 0, 14, 16, 2),
            Block.makeCuboidShape(2, 14, 15, 14, 15, 16),
            Block.makeCuboidShape(0, 14, 2, 1, 15, 14),
            Block.makeCuboidShape(15, 14, 2, 16, 15, 14)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.makeCuboidShape(0, 2, 14, 2, 14, 16),
            Block.makeCuboidShape(0, 2, 0, 2, 14, 2),
            Block.makeCuboidShape(0, 0, 14, 2, 2, 16),
            Block.makeCuboidShape(0, 0, 0, 2, 2, 2),
            Block.makeCuboidShape(14, 0, 14, 16, 2, 16),
            Block.makeCuboidShape(14, 0, 0, 16, 2, 2),
            Block.makeCuboidShape(0, 14, 14, 2, 16, 16),
            Block.makeCuboidShape(0, 14, 0, 2, 16, 2),
            Block.makeCuboidShape(14, 14, 14, 16, 16, 16),
            Block.makeCuboidShape(14, 14, 0, 16, 16, 2),
            Block.makeCuboidShape(15, 2, 15, 16, 14, 16),
            Block.makeCuboidShape(15, 2, 0, 16, 14, 1),
            Block.makeCuboidShape(1, 2, 1, 15, 14, 15),
            Block.makeCuboidShape(0, 14, 2, 2, 16, 14),
            Block.makeCuboidShape(15, 14, 2, 16, 15, 14),
            Block.makeCuboidShape(2, 14, 15, 14, 15, 16),
            Block.makeCuboidShape(2, 14, 0, 14, 15, 1)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.makeCuboidShape(14, 2, 14, 16, 14, 16),
            Block.makeCuboidShape(0, 2, 14, 2, 14, 16),
            Block.makeCuboidShape(14, 0, 14, 16, 2, 16),
            Block.makeCuboidShape(0, 0, 14, 2, 2, 16),
            Block.makeCuboidShape(14, 0, 0, 16, 2, 2),
            Block.makeCuboidShape(0, 0, 0, 2, 2, 2),
            Block.makeCuboidShape(14, 14, 14, 16, 16, 16),
            Block.makeCuboidShape(0, 14, 14, 2, 16, 16),
            Block.makeCuboidShape(14, 14, 0, 16, 16, 2),
            Block.makeCuboidShape(0, 14, 0, 2, 16, 2),
            Block.makeCuboidShape(15, 2, 0, 16, 14, 1),
            Block.makeCuboidShape(0, 2, 0, 1, 14, 1),
            Block.makeCuboidShape(1, 2, 1, 15, 14, 15),
            Block.makeCuboidShape(2, 14, 14, 14, 16, 16),
            Block.makeCuboidShape(2, 14, 0, 14, 15, 1),
            Block.makeCuboidShape(15, 14, 2, 16, 15, 14),
            Block.makeCuboidShape(0, 14, 2, 1, 15, 14)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.makeCuboidShape(14, 2, 0, 16, 14, 2),
            Block.makeCuboidShape(14, 2, 14, 16, 14, 16),
            Block.makeCuboidShape(14, 0, 0, 16, 2, 2),
            Block.makeCuboidShape(14, 0, 14, 16, 2, 16),
            Block.makeCuboidShape(0, 0, 0, 2, 2, 2),
            Block.makeCuboidShape(0, 0, 14, 2, 2, 16),
            Block.makeCuboidShape(14, 14, 0, 16, 16, 2),
            Block.makeCuboidShape(14, 14, 14, 16, 16, 16),
            Block.makeCuboidShape(0, 14, 0, 2, 16, 2),
            Block.makeCuboidShape(0, 14, 14, 2, 16, 16),
            Block.makeCuboidShape(0, 2, 0, 1, 14, 1),
            Block.makeCuboidShape(0, 2, 15, 1, 14, 16),
            Block.makeCuboidShape(1, 2, 1, 15, 14, 15),
            Block.makeCuboidShape(14, 14, 2, 16, 16, 14),
            Block.makeCuboidShape(0, 14, 2, 1, 15, 14),
            Block.makeCuboidShape(2, 14, 0, 14, 15, 1),
            Block.makeCuboidShape(2, 14, 15, 14, 15, 16)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    public ElectrifierBlock(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
        switch (state.get(FACING)){
            case NORTH:
                return SHAPE_N;
            case WEST:
                return SHAPE_W;
            case SOUTH:
                return SHAPE_S;
            case EAST:
                return SHAPE_E;
            default:
                return SHAPE_N;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(!worldIn.isRemote()){
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof ElectrifierTile){
                INamedContainerProvider containerProvider = new INamedContainerProvider() {
                    @Override
                    public ITextComponent getDisplayName() {
                        return new TranslationTextComponent("screen.mccourse.electrifier");
                    }

                    @Nullable
                    @Override
                    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                        return new ElectrifierContainer(i, worldIn, pos, playerInventory, playerEntity);
                    }
                };
                NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, tileEntity.getPos());
            } else {
                throw new IllegalStateException("Our Container Provider is missing!");
            }
        }

        return ActionResultType.SUCCESS;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world){
        return ModTileEntities.ELECTRIFIER_TILE_ENTITY.get().create();
    }

    @Override
    public boolean hasTileEntity(BlockState state){
        return true;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context){
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState rotate(BlockState state, Rotation rot)
    {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn){
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder){
        builder.add(FACING);
    }

}
