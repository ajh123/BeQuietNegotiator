# Be Quiet Negotiator

Be Quiet Negotiator is a lightweight, client-side-only NeoForge mod that lets modded clients connect to vanilla or
minimally modded servers (such as Fabric servers with only server-side mods) without triggering NeoForge’s
strict network negotiation process.

## How it works

Normally, NeoForge enforces a handshake protocol to ensure both client and server agree on modded payloads and
capabilities. This mod quietly suppresses that negotiation when connecting to servers that don’t speak NeoForge,
allowing the connection to go through cleanly without disconnects or errors — hence, it “negotiates” by staying silent.

In addition, other errors that may occur during the connection process, such as packet decoding errors
or unexpected packet sizes, are also ignored, allowing the client to connect without issues.

Specifically, Be Quiet Negotiator uses Mixins to patch the NeoForge client code, specifically targeting any annoying
methods which produce errors or disconnects when connecting to servers that do not support NeoForge.

Furthermore, all patches are configurable, allowing users to enable or disable specific patches as needed.

## Use cases

This mod is ideal for:
1. Players using NeoForge clients who want to join vanilla servers, preventing disconnects caused by missing network negotiation responses.
2. Players connecting through hybrid or proxy networks, where the entrypoint is a vanilla or non-NeoForge server, avoiding failed handshakes when the client expects NeoForge.
3. Modpack developers or testers using full-stack NeoForge mods client-side, who want to connect to unmodded or differently modded servers without having to disable mods each time.
4. Players attempting to join servers without NeoForge, while still having mods installed that expect a modded server-side (e.g., worldgen, item, or GUI mods with networking).
5. LAN or casual multiplayer setups, where only the client is running NeoForge with full-feature mods, and the server is unmodded or running a different mod loader.

## Making a production-ready proxy network?

Do not use this mod! You will likely run into issues with mod compatibility, as Be Quiet Negotiator just suppresses errors
and does not magically make incompatible mods work.

Instead of using a traditional proxy to route connections to backend servers, I recommend giving each NeoForge server
its own public IP address / domain.

If you don't have enough IP addresses, you can use a reverse proxy like
[Gate Lite Mode](https://gate.minekube.com/guide/lite) or my own [mc-router](https://github.com/CloudinatorMC/mc-router/)
to route connections based on the Handshake packet's Server Address field.

To transfer players between servers, use the vanilla `/transfer` command introduced in Minecraft 1.20.5, or install the
[Server Redirect mod](https://modrinth.com/plugin/server-redirect).

## Included Patches (since v1.0.1)

- `bypassNegotiationErrors` - Allows the client to connect to servers that do not support NeoForge by ignoring negotiation errors.
- `bypassCustomFeatureFlags` - Ignores custom feature flags when connecting to a server, allowing the client to connect without issues.
- `ignorePacketDecodingErrors` - Ignores packet decoding errors when connecting to a server, allowing the client to connect without issues.
- `ignorePacketHandlerErrors` - Ignores client-side packet handling errors when connecting to a server, allowing the client to connect without issues.

## Important notes

### Connection Safety

Be Quiet Negotiator is safe by design: it only activates when the server is identified as non-NeoForge, leaving
normal modded-to-modded connections completely untouched. Configuration is simple and toggleable, giving users fine
control over when quiet negotiation is allowed.

### Mod Compatibility

Some mods may depend on successful negotiation or expect server-side support for certain features. In these cases, functionality may break silently or result in confusing behaviour.

Use caution, and avoid placing or interacting with modded blocks, items, or GUIs on servers that aren’t running NeoForge. **This mod enables connection compatibility, but it cannot make incompatible mods magically work.**

We have a list of known incompatible mods in [docs/incompatibility.md](https://github.com/ajh123/BeQuietNegotiator/blob/main/docs/incompatibility.md).

## Development information

Once you have your clone, simply open the repository in the IDE of your choice.
The usual recommendation for an IDE is either IntelliJ IDEA or Eclipse.

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
