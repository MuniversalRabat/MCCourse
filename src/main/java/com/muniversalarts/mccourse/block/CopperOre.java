package com.muniversalarts.mccourse.block;

import com.muniversalarts.mccourse.sound.ModSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class CopperOre extends Block {
    public CopperOre(Properties properties) {
        super(properties);
    }

    @Override
    public void onPlayerDestroy(IWorld worldIn, BlockPos pos, BlockState state) {
        if(worldIn.isRemote()){
            Minecraft.getInstance().player.playSound(ModSoundEvents.SMALL_EXPLOSION.get(), 2f, 1f);
        } /* else {
            worldIn.playSound(); // https://mcforge.readthedocs.io/en/1.16.x/effects/sounds/
        } */

        super.onPlayerDestroy(worldIn, pos, state);
    }
}
