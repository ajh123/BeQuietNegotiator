package me.ajh123.channel_acepptor;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(ChannelAcepptor.MODID)
public class ChannelAcepptor
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "channel_acepptor";
    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public ChannelAcepptor(IEventBus modEventBus, ModContainer modContainer)
    {
        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ChannelAcepptor) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
