package io.github.lukebemish.dynamic_asset_generator.fabric;

/*import io.github.lukebemish.dynamic_asset_generator.impl.DynamicAssetGenerator;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;

import java.io.InputStream;
import java.util.Collection;
import java.util.Optional;*/

import io.github.lukebemish.dynamic_asset_generator.impl.DynamicAssetGenerator;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

public class GeneratedPackReloadListener implements SimpleSynchronousResourceReloadListener {

    @Override
    public ResourceLocation getFabricId() {
        return new ResourceLocation("yourmodid", "generated_pack_reload");
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        // Hook into reload cycle here
        DynamicAssetGenerator.CACHES.forEach((location, info) -> {
            if (info.cache().getPackType() == net.minecraft.server.packs.PackType.SERVER_DATA) {
                // Your regeneration logic here
            }
        });
    }
}

/*
public class GeneratedPackReloadListener implements SimpleSynchronousResourceReloadListener {
    private final PackType type;

    public GeneratedPackReloadListener(PackType type) {
        this.type = type;
    }

    @Override
    public ResourceLocation getFabricId() {
        // Unique ID for this reload listener
        return new ResourceLocation("yourmodid", "generated_" + type.getDirectory());
    }

    @Override
    public Collection<ResourceLocation> getFabricDependencies() {
        return SimpleSynchronousResourceReloadListener.super.getFabricDependencies();
    }

    @Override
    public void reload(net.minecraft.server.packs.resources.ResourceManager manager) {
        // Here you hook in your DynamicAssetGenerator logic.
        // Example:
        DynamicAssetGenerator.CACHES.forEach((location, info) -> {
            if (info.cache().getPackType() == type) {
                try {
                    Optional<InputStream> resource = manager.getResource(location).map(r -> {
                        try {
                            return r.open();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                    // Do something with resource, or push into your runtime packs
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}*/
