package org.mosa0.bls.events;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;

public class BorderlinePersistentVariables extends PersistentState {

    public static boolean wasSetUp = false;
    public static long timerTicksMax = 0;
    public static int timerMinutesMax = 0;
    public static int timerSecondsMax = 0;

//    public static PersistentState.Type<BorderlinePersistentVariables> getPersistentStateType(ServerWorld world) {
//        return new PersistentState.Type(() -> new BorderlinePersistentVariables(world), (nbt, registries) -> fromNbt(world, nbt) DataFixTypes);
//    }

    private static Object fromNbt(ServerWorld world, Object nbt) {

        return nbt;
    }

    public BorderlinePersistentVariables(ServerWorld world) {
        this.markDirty();
    }

    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        return null;
    }
}