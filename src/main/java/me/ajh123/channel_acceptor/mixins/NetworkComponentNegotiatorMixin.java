package me.ajh123.channel_acceptor.mixins;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import me.ajh123.channel_acceptor.Config;
import net.neoforged.neoforge.network.negotiation.NegotiableNetworkComponent;
import net.neoforged.neoforge.network.negotiation.NetworkComponentNegotiator;

@Mixin(NetworkComponentNegotiator.class)
public class NetworkComponentNegotiatorMixin {
    @ModifyArgs(
        method = "negotiate", 
        at = @At(
            value = "INVOKE", 
            target = "Lnet/neoforged/neoforge/network/negotiation/NetworkComponentNegotiator;negotiate(Ljava/util/List;Ljava/util/List;)Lnet/neoforged/neoforge/network/negotiation/NegotiationResult;"
        ),
        remap = false
    )
    private static void modifyNegotiates(Args args) {
        List<NegotiableNetworkComponent> serverList = args.get(0);
        List<NegotiableNetworkComponent> clientList = args.get(1);
        
        List<NegotiableNetworkComponent> modifiedServer = new ArrayList<>(serverList);

        for (NegotiableNetworkComponent component : clientList) {
            if (Config.channels.contains(component.id())) {
                modifiedServer.add(component);
            }
        }

        args.set(0, modifiedServer);
        args.set(1, clientList);
    }
}
