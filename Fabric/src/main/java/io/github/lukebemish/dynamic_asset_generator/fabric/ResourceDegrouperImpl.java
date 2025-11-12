package io.github.lukebemish.dynamic_asset_generator.fabric;

import com.google.auto.service.AutoService;
//import io.github.lukebemish.dynamic_asset_generator.platform.services.IResourceDegrouper;
//import net.fabricmc.fabric.impl.resource.loader.GroupResourcePack;

import net.minecraft.server.packs.PackResources;

import java.util.ArrayList;
import java.util.List;

/*@AutoService(IResourceDegrouper.class)
public class ResourceDegrouper implements IResourceDegrouper {
    public List<? extends PackResources> unpackPacks(List<? extends PackResources> packs) {
        if (packs.stream().noneMatch(pack->pack instanceof GroupResourcePack)) {
            return packs;
        }
        ArrayList<PackResources> outPacks = new ArrayList<>();
        for (var pack : packs) {
            if (pack instanceof GroupResourcePack groupResourcePack) {
                outPacks.addAll(unpackPacks(((IGroupResourcePackMixin)groupResourcePack).getPacks()));
            } else {
                outPacks.add(pack);
            }
        }
        return outPacks;
    }
}*/

@AutoService(io.github.lukebemish.dynamic_asset_generator.impl.platform.services.ResourceDegrouper.class)
public class ResourceDegrouperImpl implements io.github.lukebemish.dynamic_asset_generator.impl.platform.services.ResourceDegrouper {
    public List<? extends PackResources> unpackPacks(List<? extends PackResources> packs) {
        ArrayList<PackResources> packsOut = new ArrayList<>();
        packs.forEach(pack -> {
            System.out.println("TESTEE PRINT: pack instanceof MyGroupResourcePack: " + (pack instanceof MyGroupResourcePack));
           //TODO   NOT SURE IF THIS IS ACTUALLY FINISHED
            if (pack instanceof MyGroupResourcePack groupResourcePack) {
                packsOut.addAll(groupResourcePack.getPacks());
            } else packsOut.add(pack);
        });
        return packsOut;
    }

/*@AutoService(io.github.lukebemish.dynamic_asset_generator.impl.platform.services.ResourceDegrouper.class)
public class ResourceDegrouperImpl implements io.github.lukebemish.dynamic_asset_generator.impl.platform.services.ResourceDegrouper {
    public List<? extends PackResources> unpackPacks(List<? extends PackResources> packs) {
        ArrayList<PackResources> packsOut = new ArrayList<>();
        packs.forEach(pack -> {
            if (pack instanceof GroupResourcePack groupResourcePack) {
                packsOut.addAll(groupResourcePack.getPacks());
            } else packsOut.add(pack);
        });
        return packsOut;
    }*/
}
