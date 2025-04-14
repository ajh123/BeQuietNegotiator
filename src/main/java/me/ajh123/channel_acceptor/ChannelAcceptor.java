package me.ajh123.channel_acceptor;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(ChannelAcceptor.MODID)
public class ChannelAcceptor
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "channel_acceptor";
    public static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MODID);

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public ChannelAcceptor(IEventBus modEventBus, ModContainer modContainer) {
        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.SERVER, Config.SPEC);
        modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
    }
}
