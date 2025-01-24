package me.ajh123.channel_acceptor.mixins;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.ajh123.channel_acceptor.Config;
import net.neoforged.neoforge.network.negotiation.NegotiableNetworkComponent;
import net.neoforged.neoforge.network.negotiation.NegotiationResult;
import net.neoforged.neoforge.network.negotiation.NetworkComponentNegotiator;

@Mixin(NetworkComponentNegotiator.class)
public class NetworkComponentNegotiatorMixin {
    @Inject(
        method = "negotiate",
        at = @At("HEAD"),
        cancellable = true
    )
    private static void onNegotiate(
        List<NegotiableNetworkComponent> server,
        List<NegotiableNetworkComponent> client,
        CallbackInfoReturnable<NegotiationResult> cir
    ) {
        List<NegotiableNetworkComponent> modifiedServer = new ArrayList<>(server);

        for (NegotiableNetworkComponent component : client) {
            if (Config.channels.contains(component.id())) {
                modifiedServer.add(component);
            }
        }

        NegotiationResult result = NetworkComponentNegotiator.negotiate(modifiedServer, client);
        cir.setReturnValue(result);
    }
}
