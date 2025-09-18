package io.github.lukebemish.dynamic_asset_generator.fabric;

import io.github.lukebemish.dynamic_asset_generator.platform.services.IPlatform;
import dev.lukebemish.dynamicassetgenerator.impl.platform.services.Platform;
import com.google.auto.service.AutoService;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

/*@AutoService(IPlatform.class)
public class PlatformImpl implements IPlatform {
    public Path getConfigFolder() {
        return FabricLoader.getInstance().getConfigDir();
    }
    public boolean isDev() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }
}*/

@AutoService(Platform.class)
public class PlatformImpl implements Platform {
    public Path getConfigFolder() {
        return QuiltLoader.getConfigDir();
    }

    @Override
    public Path getModDataFolder() {
        return QuiltLoader.getGameDir().resolve("mod_data/"+ DynamicAssetGenerator.MOD_ID);
    }

}
