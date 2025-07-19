package org.mosa0.bls.events.events;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.command.argument.BlockStateArgument;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.SetBlockCommand;
import net.minecraft.util.Clearable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.List;

import static net.minecraft.block.Blocks.*;


public class BlockSwap {
    public static void PickBlock(MinecraftServer server) {
        // Makes an instance of overworld.
        World overworld = server.getOverworld();

        // Gets the worldborder size.
        double worldborderSize = overworld.getWorldBorder().getSize();
        double worldborderCenterX = overworld.getWorldBorder().getCenterX();
        double worldborderCenterZ = overworld.getWorldBorder().getCenterZ();

        // Picks a set of random coordinates within the worldborder's range.
        int blockSwapX = ((int) (Math.random() * worldborderSize - (worldborderSize / 2))) + (int) worldborderCenterX;
        int blockSwapY = (int) (-64 + (Math.random() * (320 - (-64)))); // Y-axis min and max height.
        int blockSwapZ = ((int) (Math.random() * worldborderSize - (worldborderSize / 2))) + (int) worldborderCenterZ;

        // Get the block at the computed coordinates.
        Block initialBlockType = overworld.getBlockState(new BlockPos(blockSwapX, blockSwapY, blockSwapZ)).getBlock();
        System.out.println("Block is " + initialBlockType);
        // Rerolls the block until it's not unbreakable, stone, deepslate, or a type of air.
        while (initialBlockType.getHardness() < 0 || initialBlockType.getDefaultState().isAir() || initialBlockType.getDefaultState().isOf(STONE) || initialBlockType.getDefaultState().isOf(DEEPSLATE) || initialBlockType.getDefaultState().isOf(WATER) || initialBlockType.getDefaultState().isOf(LAVA)) {

            blockSwapX = ((int) (Math.random() * worldborderSize - (worldborderSize / 2))) + (int) worldborderCenterX;
            blockSwapY = (int) (-64 + (Math.random() * (320 - (-64))));
            blockSwapZ = ((int) (Math.random() * worldborderSize - (worldborderSize / 2))) + (int) worldborderCenterZ;

            initialBlockType = overworld.getBlockState(new BlockPos(blockSwapX, blockSwapY, blockSwapZ)).getBlock();
        }

        Block newBlockType = Registries.BLOCK.getRandom(Random.create()).orElseThrow().value();

        // Blocks on here are removed for game balancing purposes.
        final Block[] blockBlacklist = new Block[]{
                initialBlockType.getDefaultState().getBlock(),
                AIR,
                CAVE_AIR,
                VOID_AIR,
                WATER,
                LAVA,
                STONE,
                DEEPSLATE,
                COAL_BLOCK,
                EMERALD_BLOCK,
                IRON_BLOCK,
                GOLD_BLOCK,
                DIAMOND_BLOCK,
                NETHERITE_BLOCK,
                LAPIS_BLOCK,
                COAL_ORE,
                EMERALD_ORE,
                IRON_ORE,
                GOLD_ORE,
                LAPIS_ORE,
                DIAMOND_ORE,
                ANCIENT_DEBRIS,
                DEEPSLATE_IRON_ORE,
                DEEPSLATE_GOLD_ORE,
                DEEPSLATE_DIAMOND_ORE,
                DEEPSLATE_LAPIS_ORE,
                DEEPSLATE_COAL_ORE,
                RAW_IRON_BLOCK,
                RAW_GOLD_BLOCK
                // I'm not blacklisting deepslate emerald ore because it's funny.
        };

        // Same thing as line 34 but removing blocks on blockBlacklist, and blocks that would cause lag.
        while (newBlockType.getHardness() < 0 || java.util.Arrays.asList(blockBlacklist).contains(newBlockType) || newBlockType.getDefaultState().getFluidState().isEmpty()
                || newBlockType.getDefaultState().hasRandomTicks() || !newBlockType.getDefaultState().canPlaceAt(null, null) || newBlockType.getDefaultState().hasBlockEntity()) {

            newBlockType = Registries.BLOCK.getRandom(Random.create()).orElseThrow().value();
        }


        // Start and end corners.
        int CornerX1 = (int) (worldborderSize / -2 + worldborderCenterX) - 1;
        int CornerY1 = -64;
        int CornerZ1 = (int) (worldborderSize / -2 + worldborderCenterZ) - 1;
        int CornerX2 = (int) (worldborderSize / 2 + worldborderCenterX);
        int CornerY2 = 320;
        int CornerZ2 = (int) (worldborderSize / 2 + worldborderCenterZ);

        ReplaceBlocks(server, CornerX1, CornerY1, CornerZ1, CornerX2, CornerY2, CornerZ2, initialBlockType, newBlockType);


    }

    // Handles the replacement of blocks in the worldborder.
    public static void ReplaceBlocks(MinecraftServer server, int x1, int y1, int z1, int x2, int y2, int z2, Block initialBlockType, Block newBlockType) {
        // Makes new instances of World and List<BlockPos>.
        World overworld = server.getOverworld();

        // Lists to note down the position of all initialBlockType blocks.
        List<BlockPos> initialBlockCords = Lists.newArrayList();

        for (BlockPos blockPos : BlockPos.iterate(x1, y1, z1, x2, y2, z2)) {
            // Notes the coords of the blocks that are water and lava, and then sets them to air.
            if (overworld.getBlockState(blockPos).getBlock().equals(initialBlockType) ) {
                initialBlockCords.add(blockPos.toImmutable());
            }
        }

        if (initialBlockType.getStateManager().getStates().isEmpty()) {
            for (BlockPos blockPos : initialBlockCords) {
                overworld.setBlockState(blockPos); // temp
            }
        } else {
            for (BlockPos blockPos : initialBlockCords) {
                overworld.setBlockState(blockPos, newBlockType.getDefaultState());
            }
        }
    }
}