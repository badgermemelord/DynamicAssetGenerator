/*
 * Copyright (C) 2023 Luke Bemish and contributors
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */

package io.github.lukebemish.dynamic_asset_generator.impl;

import io.github.lukebemish.dynamic_asset_generator.api.ResourceGenerationContext;
import io.github.lukebemish.dynamic_asset_generator.impl.client.OldClientGenerationContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import org.jetbrains.annotations.NotNull;

public abstract class OldResourceGenerationContext extends ResourceGenerationContext {
    private final ResourceLocation cacheName;

    protected OldResourceGenerationContext(ResourceLocation cacheName) {
        this.cacheName = cacheName;
    }

    @Override
    public @NotNull ResourceLocation getCacheName() {
        return cacheName;
    }

    public static ResourceGenerationContext make(ResourceLocation cacheName, PackType type) {
        if (type == PackType.SERVER_DATA)
            return new OldServerGenerationContext(cacheName);
        return new OldClientGenerationContext(cacheName);
    }
}
