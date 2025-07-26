package me.ajh123.be_quiet_negotiator.mixins;

import me.ajh123.be_quiet_negotiator.BeQuietNegotiator;
import me.ajh123.be_quiet_negotiator.ClientConfig;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.network.protocol.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientCommonPacketListenerImpl.class)
public class ClientCommonPacketListenerImplMixin {
    @Inject(
            method = "onPacketError(Lnet/minecraft/network/protocol/Packet;Ljava/lang/Exception;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void onPacketError(Packet<?> arg, Exception exception, CallbackInfo ci) {
        if (ClientConfig.ignorePacketHandlerErrors()) {
            ci.cancel();
            BeQuietNegotiator.LOGGER.error("Failed to handle packet {}", arg, exception);
        }
    }
}
