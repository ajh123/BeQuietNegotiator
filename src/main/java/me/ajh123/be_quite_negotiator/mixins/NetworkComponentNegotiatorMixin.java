package me.ajh123.be_quite_negotiator.mixins;

import java.util.ArrayList;
import java.util.List;

import net.neoforged.neoforge.network.registration.NetworkRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import me.ajh123.be_quite_negotiator.Config;
import net.neoforged.neoforge.network.negotiation.NegotiableNetworkComponent;

@Mixin(NetworkRegistry.class)
public class NetworkComponentNegotiatorMixin {
    @ModifyArgs(
            method = "initializeNeoForgeConnection(Lnet/minecraft/network/protocol/configuration/ServerConfigurationPacketListener;Ljava/util/Map;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/neoforged/neoforge/network/negotiation/NetworkComponentNegotiator;" +
                            "negotiate(Ljava/util/List;Ljava/util/List;)" +
                            "Lnet/neoforged/neoforge/network/negotiation/NegotiationResult;",
                    remap = false
            ),
            remap = false
    )
    private static void modifyArgsForModded(Args args) {
        List<NegotiableNetworkComponent> serverComponents = args.get(0);
        List<NegotiableNetworkComponent> clientComponents = args.get(1);

        // Modify as needed
        List<NegotiableNetworkComponent> modifiedServer = new ArrayList<>(serverComponents);
        List<NegotiableNetworkComponent> modifiedClient = new ArrayList<>(clientComponents);

        for (NegotiableNetworkComponent component : clientComponents) {
            if (Config.channels().contains(component.id()) || Config.acceptAllChannels()) {
                modifiedServer.add(component);
            } else {
                System.out.println("Channel " + component.id() + " is not in the config");
            }
        }

        args.set(0, modifiedServer);
        args.set(1, modifiedClient);
    }

    @ModifyArgs(
            method = "initializeOtherConnection(Lnet/minecraft/network/protocol/configuration/ServerConfigurationPacketListener;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/neoforged/neoforge/network/negotiation/NetworkComponentNegotiator;" +
                            "negotiate(Ljava/util/List;Ljava/util/List;)" +
                            "Lnet/neoforged/neoforge/network/negotiation/NegotiationResult;",
                    remap = false
            ),
            remap = false
    )
    private static void modifyArgsForVanilla(Args args) {
        List<NegotiableNetworkComponent> serverComponents = args.get(0);
        List<NegotiableNetworkComponent> clientComponents = args.get(1);

        // Modify as needed
        List<NegotiableNetworkComponent> modifiedServer = new ArrayList<>(serverComponents);
        List<NegotiableNetworkComponent> modifiedClient = new ArrayList<>(clientComponents);

        for (NegotiableNetworkComponent component : clientComponents) {
            if (Config.channels().contains(component.id()) || Config.acceptAllChannels()) {
                modifiedServer.add(component);
            } else {
                System.out.println("Channel " + component.id() + " is not in the config");
            }
        }

        args.set(0, modifiedServer);
        args.set(1, modifiedClient);
    }
}
