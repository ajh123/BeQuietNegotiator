package me.ajh123.channel_acceptor;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.slf4j.Logger;

@EventBusSubscriber(modid = ChannelAcceptor.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ClientConfig
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.ConfigValue<Boolean> ACCEPT_VANILLA_SERVER = BUILDER
            .comment("If true, client will accept connections to a vanilla server, no matter the current mod set.")
            .define("acceptVanillaServer", true);

    static final ModConfigSpec SPEC = BUILDER.build();

    private static boolean acceptVanillaServer;


    @SubscribeEvent
    static void onLoad(final ModConfigEvent event){
        // Ensure the event is for the correct configuration type
        if (event.getConfig().getType() != ModConfig.Type.CLIENT) {
            return;
        }

        // Load the boolean value
        acceptVanillaServer = ACCEPT_VANILLA_SERVER.get();
    }

    public static boolean acceptVanillaServer() {
        return acceptVanillaServer;
    }
}