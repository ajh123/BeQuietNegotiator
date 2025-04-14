# Be Quiet Negotiator

Be Quiet Negotiator is a lightweight, client-side-only NeoForge mod that lets modded clients connect to vanilla or minimally modded servers (such as Fabric servers with only server-side mods) without triggering NeoForge’s strict network negotiation process. Normally, NeoForge enforces a handshake protocol to ensure both client and server agree on modded payloads and capabilities. This mod quietly suppresses that negotiation when connecting to servers that don’t speak NeoForge, allowing the connection to go through cleanly without disconnects or errors — hence, it “negotiates” by staying silent.

This mod is ideal for players who use NeoForge client mods (e.g. performance tools, HUDs, minimaps, or quality-of-life enhancements) and want to play on vanilla servers, Fabric servers with server-side-only mods, or proxy networks with modded backends. It’s also useful in multi-server setups where a minimal lobby server may not need full NeoForge support. Be Quiet Negotiator is safe by design: it only activates when the server is identified as non-NeoForge, leaving normal modded-to-modded connections completely untouched. Configuration is simple and toggleable, giving users fine control over when quiet negotiation is allowed.

Important Note: Some mods may depend on successful negotiation or expect server-side support for certain features. In these cases, functionality may break silently or result in confusing behaviour. Use caution, and avoid placing or interacting with modded blocks, items, or GUIs on servers that aren’t running NeoForge. This mod enables compatibility, but it cannot make incompatible mods magically work.

## Development information

Once you have your clone, simply open the repository in the IDE of your choice. The usual recommendation for an IDE is either IntelliJ IDEA or Eclipse.

If at any point you are missing libraries in your IDE, or you've run into problems you can
run `gradlew --refresh-dependencies` to refresh the local cache. `gradlew clean` to reset everything 
{this does not affect your code} and then start the process again.

### Mapping Names:

By default, the MDK is configured to use the official mapping names from Mojang for methods and fields 
in the Minecraft codebase. These names are covered by a specific license. All modders should be aware of this
license. For the latest license text, refer to the mapping file itself, or the reference copy here:
https://github.com/NeoForged/NeoForm/blob/main/Mojang.md

### Additional Resources: 

Community Documentation: https://docs.neoforged.net/  
NeoForged Discord: https://discord.neoforged.net/
