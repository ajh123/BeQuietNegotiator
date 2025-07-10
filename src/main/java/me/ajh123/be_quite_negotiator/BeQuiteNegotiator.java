package me.ajh123.be_quite_negotiator;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(BeQuiteNegotiator.MODID)
public class BeQuiteNegotiator
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "be_quite_negotiator";
    public static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MODID);

    // This boolean is used to determine if the client is connected to a vanilla server.
    public static boolean isConnectedToVanillaServer = false;

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public BeQuiteNegotiator(IEventBus modEventBus, ModContainer modContainer) {
        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.SERVER, Config.SPEC);
        modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
    }
}
