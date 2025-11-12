package io.github.lukebemish.dynamic_asset_generator.fabric;

import com.google.auto.service.AutoService;
import com.mojang.serialization.Codec;
import io.github.lukebemish.dynamic_asset_generator.impl.client.platform.PlatformClient;
import io.github.lukebemish.dynamic_asset_generator.mixin.SpriteSourcesAccessor;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.resources.ResourceLocation;

@AutoService(PlatformClient.class)
public class PlatformClientImpl implements PlatformClient {
    @Override
    public void addSpriteSource(ResourceLocation location, Codec<? extends SpriteSource> codec) {
        SpriteSourcesAccessor.invokeRegister(location.toString(), codec);
    }
}
