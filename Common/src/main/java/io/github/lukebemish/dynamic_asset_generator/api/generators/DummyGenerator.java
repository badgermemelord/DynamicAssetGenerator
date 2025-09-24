/*
 * Copyright (C) 2022-2023 Luke Bemish and contributors
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */

package io.github.lukebemish.dynamic_asset_generator.api.generators;

import com.mojang.serialization.Codec;
import io.github.lukebemish.dynamic_asset_generator.api.ResourceGenerator;
import io.github.lukebemish.dynamic_asset_generator.api.ResourceGenerationContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.IoSupplier;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.Set;

/**
 * A resource generator that generates no resources. Useful for overriding other resource generators in lower priority
 * packs.
 */
public class DummyGenerator implements ResourceGenerator {
    public static final DummyGenerator INSTANCE = new DummyGenerator();
    public static final Codec<DummyGenerator> CODEC = Codec.unit(INSTANCE);

    private DummyGenerator() {}

    @Override
    public IoSupplier<InputStream> get(ResourceLocation outRl, ResourceGenerationContext context) {
        return null;
    }

    @Override
    public @NotNull Set<ResourceLocation> getLocations() {
        return Set.of();
    }

    @Override
    public Codec<? extends ResourceGenerator> codec() {
        return CODEC;
    }
}
