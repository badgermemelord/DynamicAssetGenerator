package io.github.lukebemish.dynamic_asset_generator.fabric;

import io.github.lukebemish.dynamic_asset_generator.impl.DynamicAssetGenerator;
import io.github.lukebemish.dynamic_asset_generator.impl.client.DynamicAssetGeneratorClient;

import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.Supplier;

import static io.github.lukebemish.dynamic_asset_generator.fabric.AssetGeneratorHelper.CLIENT_RESOURCES;

public class DynamicAssetGeneratorClientFabric implements ClientModInitializer {
    public static RuntimeResourcePack RESOURCE_PACK;
    @Override
    public void onInitializeClient() {

        DynamicAssetGeneratorClient.init();

        // Create the runtime pack
        RESOURCE_PACK = RuntimeResourcePack.create(new ResourceLocation(DynamicAssetGenerator.MOD_ID, "client_resources"));

        byte[] bytes = new byte[3];
        bytes[0] = 7;
        RESOURCE_PACK.addData(ResourceLocation.tryParse("pack.mcmeta"), bytes);



        RRPCallback.BEFORE_VANILLA.register(packs -> {
            packs.add(CLIENT_RESOURCES);
        });

    }
}


/*
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES)
                .registerReloadListener(new GeneratedPackReloadListener());



        RRPCallback.AFTER_VANILLA.register(a -> {
            PaletteExtractor.refresh();
            RESOURCE_PACK = RuntimeResourcePack.create(DynamicAssetGenerator.CLIENT_PACK);
            Map<ResourceLocation, Supplier<InputStream>> map = DynAssetGenClientPlanner.getResources();
            for (ResourceLocation rl : map.keySet()) {
                Supplier<InputStream> stream = map.get(rl);
                if (stream != null) {
                    RESOURCE_PACK.addLazyResource(PackType.CLIENT_RESOURCES, rl, (i,r)-> {
                        try {
                            return stream.get().readAllBytes();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    });
                }
            }
            a.add(RESOURCE_PACK);
        });
*/
