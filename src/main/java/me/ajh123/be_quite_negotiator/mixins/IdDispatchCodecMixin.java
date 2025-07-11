package me.ajh123.be_quite_negotiator.mixins;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DecoderException;
import me.ajh123.be_quite_negotiator.BeQuiteNegotiator;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.VarInt;
import net.minecraft.network.codec.IdDispatchCodec;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(IdDispatchCodec.class)
public class IdDispatchCodecMixin {
    @Shadow
    @Final
    private List<IdDispatchCodec.Entry<ByteBuf, ?, ?>> byId;

    @Inject(
            method = "decode(Lio/netty/buffer/ByteBuf;)Ljava/lang/Object;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void decode(ByteBuf byteBuf, CallbackInfoReturnable<StreamCodec<ByteBuf, ?>> cir) {
        if (BeQuiteNegotiator.isConnectedToVanillaServer) {
            try {
                // <Default Minecraft implementation>
                int i = VarInt.read(byteBuf);
                if (i >= 0 && i < byId.size()) {
                    IdDispatchCodec.Entry<ByteBuf, ?, ?> entry = this.byId.get(i);

                    try {
                        cir.setReturnValue((StreamCodec<ByteBuf, ?>) entry.serializer().decode(byteBuf));
                    } catch (Exception exception) {
                        throw new DecoderException("Failed to decode packet '" + entry.type() + "'", exception);
                    }
                } else {
                    throw new DecoderException("Received unknown packet id " + i);
                }
                // </Default Minecraft implementation>
            } catch (DecoderException e) {
                BeQuiteNegotiator.LOGGER.error("Error decoding packet:", e);
                // We cannot return null here, as it would cause a NullPointerException later in the code.
                // Instead, we return an empty codec and byteBuf.
                cir.setReturnValue(new StreamCodec<>() {
                    @Override
                    public @NotNull Object decode(@NotNull ByteBuf buf) {
                        return new byte[0];
                    }

                    @Override
                    public void encode(@NotNull ByteBuf buf, @NotNull Object value) {
                        // we cannot encode as decoding does not need to encode anything
                    }
                });
                // Do not throw an exception here, as it would crash the client.
            }
        }
    }
}
