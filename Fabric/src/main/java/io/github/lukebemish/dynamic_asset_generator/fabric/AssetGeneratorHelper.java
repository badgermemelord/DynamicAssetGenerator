package io.github.lukebemish.dynamic_asset_generator.fabric;

import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.minecraft.resources.ResourceLocation;

public class AssetGeneratorHelper {
    // Create your runtime packs up front (prevents NPEs)
    public static final RuntimeResourcePack DATA_PACK =
            RuntimeResourcePack.create(new ResourceLocation("dynamic_asset_generator", "data_pack"));
    public static final RuntimeResourcePack CLIENT_RESOURCES =
            RuntimeResourcePack.create(new ResourceLocation("dynamic_asset_generator", "client_resources"));

    public static void init() {
        // Register datapack
        RRPCallback.BEFORE_VANILLA.register(packs -> {
            packs.add(DATA_PACK);
        });

        // Register client resources
        RRPCallback.BEFORE_VANILLA.register(packs -> {
            packs.add(CLIENT_RESOURCES);
        });
    }
}
