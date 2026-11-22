# ChatChunk Replacer

A Fabric 26.3 server-side mod project.

## Use

1. Back up your world first. This permanently replaces every block in the chunk you are standing in, from the world's minimum build height to its maximum build height.
2. Install this mod and Fabric API in the same Fabric instance/server.
3. Start a single-player world with cheats enabled (or use an account with operator permission).
4. Stand in the chunk you want to replace and type a block ID by itself in chat, for example:
   - `diamond_block`
   - `minecraft:stone`
   - `minecraft:obsidian`
5. Press Enter. The message is consumed and the chunk is replaced.

## Important limitations

- This is a **server-side** mod. It must be loaded by the integrated single-player server or the multiplayer server. Installing it only on your client will not make it work on an unmodified multiplayer server.
- Operator permission level 2 is required.
- A full-height chunk replacement changes up to 16 × 16 × the world's build height blocks and can cause a noticeable pause. Test in a disposable world first.
- It replaces block states with each block's default state; it does not preserve block entities, inventories, signs, or special block data.
- This source project has not been compiled or tested in CS Launcher Plus. The generated ZIP is a project source package, not a ready-to-install JAR.

## Build

Build with JDK 25 or newer and Gradle 9.6 or newer:

    gradle build

The output JAR should appear in `build/libs/`.

The version values in `gradle.properties` follow Fabric's current 26.3 development recommendations. If Fabric updates these versions, use the Fabric Develop page to refresh them:
https://fabricmc.net/develop/
