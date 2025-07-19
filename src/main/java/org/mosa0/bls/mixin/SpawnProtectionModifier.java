package org.mosa0.bls.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.ServerPropertiesHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Type;
import java.nio.file.Path;

@Mixin(ServerPropertiesHandler.class)
public abstract class SpawnProtectionModifier {

    @Shadow @Mutable @Final
    public int spawnProtection;
    @Inject(method = "load", at = @At("RETURN"))
    private static void onLoadWorld(Path path, CallbackInfoReturnable<ServerPropertiesHandler> cir) {
        ServerPropertiesHandler instance = cir.getReturnValue();

        // Cast to Mixin to access shadowed field
        SpawnProtectionModifier mixin = (SpawnProtectionModifier) (Object) instance;

        assert mixin != null;
        mixin.spawnProtection = 0;
    }
}