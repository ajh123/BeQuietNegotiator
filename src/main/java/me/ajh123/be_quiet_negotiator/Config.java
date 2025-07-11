package me.ajh123.be_quiet_negotiator;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = BeQuietNegotiator.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.ConfigValue<List<? extends String>> CHANNELS_STRINGS = BUILDER
            .comment("A list of channel names to listen on")
            .defineListAllowEmpty("channels", List.of("mod:example"), Config::dummyValidate);

    private static final ModConfigSpec.ConfigValue<Boolean> ACCEPT_ALL_CHANNELS = BUILDER
            .comment("If true, all channels will be accepted regardless of the list")
            .define("acceptAllChannels", true);

    static final ModConfigSpec SPEC = BUILDER.build();

    private static Set<ResourceLocation> channels;
    private static boolean acceptAllChannels;

    private static boolean dummyValidate(final Object obj){
        return true;
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event){
        BeQuietNegotiator.LOGGER.info("Is server config: {}?", event.getConfig().getType() == ModConfig.Type.SERVER);
        // Ensure the event is for the correct configuration type
        if (event.getConfig().getType() != ModConfig.Type.SERVER) {
            return;
        }

        // Convert the list of strings into a set of items
        channels = CHANNELS_STRINGS.get().stream()
                .map(itemName -> ResourceLocation.parse(itemName))
                .collect(Collectors.toSet());

        // Load the boolean value
        acceptAllChannels = ACCEPT_ALL_CHANNELS.get();
    }

    public static Set<ResourceLocation> channels() {
        return channels;
    }

    public static boolean acceptAllChannels() {
        return acceptAllChannels;
    }
}