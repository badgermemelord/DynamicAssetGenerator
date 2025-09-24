package io.github.lukebemish.dynamic_asset_generator.fabric;

import io.github.lukebemish.dynamic_asset_generator.impl.DynAssetGenServerPlanner;
import io.github.lukebemish.dynamic_asset_generator.impl.DynamicAssetGenerator;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.flag.FeatureFlagSet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.function.Supplier;

public class DynamicAssetGeneratorFabric implements ModInitializer {
    public static RuntimeResourcePack DATA_PACK;
/*    @Override
    public void onInitialize() {
        RRPCallback.AFTER_VANILLA.register(a -> {
            DATA_PACK = RuntimeResourcePack.create(DynamicAssetGenerator.SERVER_PACK);
            Map<ResourceLocation, Supplier<InputStream>> map = DynAssetGenServerPlanner.getResources();
            for (ResourceLocation rl : map.keySet()) {
                Supplier<InputStream> stream = map.get(rl);
                if (stream != null) {
                    DATA_PACK.addLazyResource(PackType.SERVER_DATA, rl, (i,r)-> {
                        try (InputStream is = stream.get()) {
                            if (is==null) DynamicAssetGenerator.LOGGER.error("No InputStream supplied for {}; will likely die terribly...", rl);
                            return is==null? null : is.readAllBytes();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    });
                }
            }
            a.add(DATA_PACK);
        });
    }*/

/*    @Override
    public void onInitialize(ModContainer container) {
        DynamicAssetGenerator.init();
        registerForType(PackType.SERVER_DATA);
    }

    static void registerForType(PackType type) {
        // Can't use the "default" and "top" events as they compute namespaces too early
        ResourceLoader.get(type).registerResourcePackProfileProvider(consumer ->
                DynamicAssetGenerator.CACHES.forEach(((location, info) -> {
                    if (info.cache().getPackType() == type) {
                        var metadata = DynamicAssetGenerator.fromCache(info.cache());
                        Pack pack = Pack.create(
                                DynamicAssetGenerator.MOD_ID+':'+info.cache().getName().toString(),
                                Component.literal(info.cache().getName().toString()),
                                true,
                                s -> new GeneratedPackResources(info.cache()),
                                new Pack.Info(metadata.getDescription(),
                                        metadata.getPackFormat(),
                                        FeatureFlagSet.of()),
                                type,
                                info.position(),
                                true,
                                PackSource.DEFAULT
                        );
                        consumer.accept(pack);
                    }
                })));
    }*/


    @Override
    public void onInitialize() {
        DynamicAssetGenerator.init();

        // Hook into the datapack pipeline
        ResourceManagerHelper.get(PackType.SERVER_DATA)
                .registerReloadListener(new GeneratedPackReloadListener(PackType.SERVER_DATA));
    }
}
