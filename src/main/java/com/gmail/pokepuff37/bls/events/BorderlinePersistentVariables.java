package com.gmail.pokepuff37.bls.events;

import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;

import static net.minecraft.entity.effect.StatusEffectInstance.fromNbt;

public class BorderlinePersistentVariables extends PersistentState {

    public static boolean wasSetUp = false;
    public static long timerTicksMax = 0;
    public static int timerMinutesMax = 0;
    public static int timerSecondsMax = 0;

    public static PersistentState.Type<BorderlinePersistentVariables> getPersistentStateType(ServerWorld world) {
        return new PersistentState.Type(() -> new BorderlinePersistentVariables(world), (nbt, registries) -> fromNbt(world, nbt) DataFixTypes);
    }

    public BorderlinePersistentVariables(ServerWorld world) {
        this.markDirty();
    }

    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        return null;
    }
}