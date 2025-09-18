/*
 * Copyright (C) 2023 Luke Bemish and contributors
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */

package io.github.lukebemish.dynamicassetgenerator.impl.client.platform;

import io.github.lukebemish.dynamicassetgenerator.impl.platform.Services;

public class ClientServices {
    public static final PlatformClient PLATFORM_CLIENT = Services.load(PlatformClient.class);
}
