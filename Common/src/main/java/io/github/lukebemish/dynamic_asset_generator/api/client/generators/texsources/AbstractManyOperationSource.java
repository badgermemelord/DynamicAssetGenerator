/*
 * Copyright (C) 2023 Luke Bemish and contributors
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */

package io.github.lukebemish.dynamic_asset_generator.api.client.generators.texsources;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.lukebemish.dynamic_asset_generator.api.ResourceGenerationContext;
import io.github.lukebemish.dynamic_asset_generator.api.client.generators.TexSource;
import io.github.lukebemish.dynamic_asset_generator.api.client.generators.TexSourceDataHolder;
import io.github.lukebemish.dynamic_asset_generator.api.client.image.ImageUtils;
import io.github.lukebemish.dynamic_asset_generator.api.colors.operations.PointwiseOperation;
import io.github.lukebemish.dynamic_asset_generator.impl.util.MultiCloser;
import net.minecraft.server.packs.resources.IoSupplier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * An abstract {@link TexSource} that is described by a {@link PointwiseOperation.Any}.
 */
abstract public class AbstractManyOperationSource implements TexSource {
    private final List<TexSource> sources;

    public AbstractManyOperationSource(List<TexSource> sources) {
        this.sources = sources;
    }

    public List<TexSource> getSources() {
        return sources;
    }

    /**
     * @return the operation that describes this source
     */
    public abstract PointwiseOperation.Any<Integer> getOperation();

    /**
     * Creates a {@link Codec} for a subtype of this class based on a function for constructing single instances.
     */
    public static <T extends AbstractManyOperationSource> Codec<T> makeCodec(Function<List<TexSource>, T> ctor) {
        return RecordCodecBuilder.create(instance -> instance.group(
                TexSource.CODEC.listOf().fieldOf("sources").forGetter(AbstractManyOperationSource::getSources)
        ).apply(instance, ctor));
    }

    @Override
    public @Nullable IoSupplier<NativeImage> getSupplier(TexSourceDataHolder data, ResourceGenerationContext context) {
        List<IoSupplier<NativeImage>> inputs = new ArrayList<>();
        for (TexSource o : this.getSources()) {
            var source = o.getSupplier(data, context);
            if (source == null) {
                data.getLogger().error("Texture given was nonexistent...\n{}",o.stringify());
                return null;
            }
            inputs.add(source);
        }
        return () -> {
            List<NativeImage> images = new ArrayList<>();
            for (var input : inputs) {
                images.add(input.get());
            }
            try (MultiCloser ignored = new MultiCloser(images)) {
                return ImageUtils.generateScaledImage(getOperation(), images);
            }
        };
    }
}
