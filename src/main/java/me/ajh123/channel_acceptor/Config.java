package me.ajh123.channel_acceptor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
@EventBusSubscriber(modid = ChannelAcceptor.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.ConfigValue<List<? extends String>> CHANNELS_STRINGS = BUILDER
            .comment("A list of channel names to listen on")
            .defineListAllowEmpty("channels", List.of("mod:example"), Config::dummyValidate);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean logDirtBlock;
    public static int magicNumber;
    public static String magicNumberIntroduction;
    public static Set<ResourceLocation> channels;

    private static boolean dummyValidate(final Object obj){
        return true;
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event){
        // convert the list of strings into a set of items
        channels = CHANNELS_STRINGS.get().stream()
                .map(itemName -> ResourceLocation.parse(itemName))
                .collect(Collectors.toSet());
    }
}
