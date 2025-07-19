package org.mosa0.bls.events.events;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.command.argument.BlockStateArgument;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.List;

import static net.minecraft.block.Blocks.*;

public class LiquidSwap {
    public static void LiquidSwapHandler(MinecraftServer server) {

        World overworld = server.getOverworld();
        // Gets data about the worldborder
        double worldborderSize = overworld.getWorldBorder().getSize();
        double worldborderCenterX = overworld.getWorldBorder().getCenterX();
        double worldborderCenterZ = overworld.getWorldBorder().getCenterZ();

        // Start and end corners.
        int CornerX1 = (int) (worldborderSize / -2 + worldborderCenterX) - 1,
                CornerY1 = -64,
                CornerZ1 = (int) (worldborderSize / -2 + worldborderCenterZ) - 1;
        int CornerX2 = (int) (worldborderSize / 2 + worldborderCenterX) - 1,
                CornerY2 = 320,
                CornerZ2 = (int) (worldborderSize / 2 + worldborderCenterZ) - 1;

        Swapping(server, CornerX1, CornerY1, CornerZ1, CornerX2, CornerY2, CornerZ2);
    }
    
    // Handles the swapping of liquids in the worldborder.
    public static void Swapping (MinecraftServer server, int x1, int y1, int z1, int x2, int y2, int z2) {

        World overworld = server.getOverworld();
        // Lists to note down the position of all water and lava initially.
        List<BlockPos> toWaterList = Lists.newArrayList();
        List<BlockPos> toLavaList = Lists.newArrayList();

        for (BlockPos blockPos : BlockPos.iterate(x1, y1, z1, x2, y2, z2)) {
            // Notes the coords of the blocks that are water and lava, and then sets them to air.
            if (overworld.getFluidState(blockPos).getFluid().equals(Fluids.LAVA) ) {
                toWaterList.add(blockPos.toImmutable());
                overworld.setBlockState(blockPos, AIR.getDefaultState());
            } else if (overworld.getFluidState(blockPos).getFluid().equals(Fluids.WATER)) {
                toLavaList.add(blockPos.toImmutable());
                overworld.setBlockState(blockPos, AIR.getDefaultState());
            } else if (overworld.getFluidState(blockPos).getLevel() != 0) { // This sets all blocks that are not a source to air.
                overworld.setBlockState(blockPos, AIR.getDefaultState());
            }
        }

        // Places lava at all positions that were water.
        for (BlockPos blockPos : toLavaList) {
            overworld.setBlockState(blockPos, LAVA.getDefaultState());
        }

        // Places water at all positions that were lava.
        for (BlockPos blockPos : toWaterList) {
            overworld.setBlockState(blockPos, WATER.getDefaultState());
        }
    }
}
