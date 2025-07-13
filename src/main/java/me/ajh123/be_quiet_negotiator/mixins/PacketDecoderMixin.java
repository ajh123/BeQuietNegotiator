package me.ajh123.be_quiet_negotiator.mixins;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import me.ajh123.be_quiet_negotiator.BeQuietNegotiator;
import me.ajh123.be_quiet_negotiator.ClientConfig;
import net.minecraft.network.*;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;
import net.minecraft.util.profiling.jfr.JvmProfiler;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.List;

@Mixin(PacketDecoder.class)
public class PacketDecoderMixin {
    @Shadow
    @Final
    private static Logger LOGGER;

    @Shadow
    @Final
    private ProtocolInfo<? extends PacketListener> protocolInfo;

    @Inject(
            method = "decode(Lio/netty/channel/ChannelHandlerContext;Lio/netty/buffer/ByteBuf;Ljava/util/List;)V",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list, CallbackInfo ci) throws IOException {
        if (BeQuietNegotiator.isConnectedToVanillaServer && ClientConfig.ignorePacketDecodingErrors()) {
            try {
                // <Default Minecraft implementation>>
                int i = byteBuf.readableBytes();
                if (i != 0) {
                    Packet<? extends PacketListener> packet = this.protocolInfo.codec().decode(byteBuf);
                    PacketType<? extends Packet<? extends PacketListener>> packetType = packet.type();
                    JvmProfiler.INSTANCE.onPacketReceived(this.protocolInfo.id(), packetType, channelHandlerContext.channel().remoteAddress(), i);
                    if (byteBuf.readableBytes() > 0) {
                        throw new IOException(
                                "Packet "
                                        + this.protocolInfo.id().id()
                                        + "/"
                                        + packetType
                                        + " ("
                                        + packet.getClass().getSimpleName()
                                        + ") was larger than I expected, found "
                                        + byteBuf.readableBytes()
                                        + " bytes extra whilst reading packet "
                                        + packetType
                        );
                    } else {
                        list.add(packet);
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug(Connection.PACKET_RECEIVED_MARKER, " IN: [{}:{}] {} -> {} bytes", this.protocolInfo.id().id(), packetType, packet.getClass().getName(), i);
                        }

                        ProtocolSwapHandler.handleInboundTerminalPacket(channelHandlerContext, packet);
                    }
                }
                // </Default Minecraft implementation>
            } catch (DecoderException e) {
                BeQuietNegotiator.LOGGER.error("Error decoding packet:", e);
                ci.cancel();
            }
        }
        // if false let Mixins automatically apply the default Minecraft implementation no work needed for us this is automatically done by the Mixin system
    }
}
