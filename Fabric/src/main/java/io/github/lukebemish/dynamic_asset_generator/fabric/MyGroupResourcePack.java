package io.github.lukebemish.dynamic_asset_generator.fabric;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Nullable;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.IoSupplier; // mapping: net.minecraft.server.packs.resources.IoSupplier
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackResources.ResourceOutput;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;

/**
 * Fabric-compatible replacement for Quilt's GroupResourcePack.
 * Implements PackResources by delegating to a list of child PackResources.
 */
public class MyGroupResourcePack implements PackResources {
    private final String id;
    private final List<PackResources> packs;

    public MyGroupResourcePack(String id, List<PackResources> packs) {
        this.id = id;
        this.packs = List.copyOf(packs);
    }

    public List<PackResources> getPacks() {
        return packs;
    }

    // --- PackResources methods ---

    @Override
    public String packId() {
        return id;
    }

    @Override
    public Set<String> getNamespaces(PackType type) {
        return packs.stream()
                .flatMap(p -> p.getNamespaces(type).stream())
                .collect(Collectors.toSet());
    }

    /**
     * Delegate listResources to each child pack.
     */
    @Override
    public void listResources(PackType type, String namespace, String path, ResourceOutput output) {
        for (PackResources pack : packs) {
            pack.listResources(type, namespace, path, output);
        }
    }

    /*
      Delegate getResource: returns the first non-null IoSupplier<InputStream> from children.
     */
    @Override
    @Nullable
    public IoSupplier<InputStream> getResource(PackType type, ResourceLocation id) {
        for (PackResources pack : packs) {
            IoSupplier<InputStream> io = pack.getResource(type, id);
            if (io != null) return io;
        }
        return null;
    }

    /**
     * Some PackResources also expose a 'getRootResource' helper; delegate if present.
     */
    @Override
    @Nullable
    public IoSupplier<InputStream> getRootResource(String... paths) {
        for (PackResources pack : packs) {
            IoSupplier<InputStream> io = pack.getRootResource(paths);
            if (io != null) return io;
        }
        return null;
    }

    @Override
    public <T> T getMetadataSection(MetadataSectionSerializer<T> deserializer) throws IOException {
        for (PackResources pack : packs) {
            T section = pack.getMetadataSection(deserializer);
            if (section != null) return section;
        }
        return null;
    }

    @Override
    public void close() {
        for (PackResources pack : packs) {
            try {
                pack.close();
            } catch (Exception e) {
                // Log or ignore — PackResources#close does not declare checked exceptions.
                e.printStackTrace();
            }
        }
    }

    // ---- Compatibility helper: emulate old getResources(...) behavior ----
    // Many older codebases called: getResources(type, namespace, path, depth, filter)
    // We provide a helper that uses listResources(...) and applies the filter.
    public Collection<ResourceLocation> getResources(PackType type,
                                                     String namespace,
                                                     String path,
                                                     int depth,
                                                     Predicate<String> filter) {
        List<ResourceLocation> out = new ArrayList<>();
        ResourceOutput output = (resourceLocation, supplier) -> {
            // resourceLocation.getPath() is the path part (e.g. "textures/foo/bar.png")
            String resourcePath = resourceLocation.getPath();
            // apply the user's filter (older code often filters on file name or path)
            if (filter == null || filter.test(resourcePath)) {
                // we don't faithfully enforce 'depth' here in all edge cases — if you need strict depth,
                // compute relative depth based on `path` and resourcePath (can be done if required).
                out.add(resourceLocation);
            }
        };
        this.listResources(type, namespace, path, output);
        return out;
    }
}



/*import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.resources.IoSupplier;
import org.jetbrains.annotations.Nullable;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

*//**
 * Fabric replacement for Quilt's GroupResourcePack.
 * This "virtual pack" just holds multiple child PackResources.
 *//*
public class MyGroupResourcePack implements PackResources, Closeable {
    private final List<PackResources> packs;

    public MyGroupResourcePack(List<PackResources> packs) {
        this.packs = packs;
    }

    public List<PackResources> getPacks() {
        return packs;
    }

    @Override
    public void close() {
        for (PackResources pack : packs) {
            try {
                pack.close();
            } catch (Exception e) {
                // log or ignore depending on your needs
                e.printStackTrace();
            }
        }
    }

    // PackResources requires these methods.
    // You can either no-op them or delegate to children depending on your use case.

    @Override
    public String packId() {
        return "group:" + packs.stream()
                .map(PackResources::packId)
                .reduce((a, b) -> a + "+" + b)
                .orElse("empty");
    }

*//*    @Override
    public net.minecraft.server.packs.PackType packType() {
        // If your packs are mixed types, pick the one that makes sense
        return packs.isEmpty() ? null : packs.get(0).packType();
    }*//*

public net.minecraft.server.packs.PackType packType() {
    return net.minecraft.server.packs.PackType.SERVER_DATA;
}

    @Override
    public @Nullable IoSupplier<InputStream> getRootResource(String... elements) {
        return null;
    }

    @Override
    public @Nullable IoSupplier<InputStream> getResource(PackType packType, ResourceLocation location) {
        return null;
    }

    @Override
    public void listResources(PackType packType, String namespace, String path, ResourceOutput resourceOutput) {

    }

    @Override
    public java.util.Set<String> getNamespaces(net.minecraft.server.packs.PackType type) {
        return packs.stream()
                .flatMap(p -> p.getNamespaces(type).stream())
                .collect(java.util.stream.Collectors.toSet());
    }

    @Override
    public @Nullable <T> T getMetadataSection(MetadataSectionSerializer<T> deserializer) throws IOException {
        return null;
    }

*//*    @Override
    public @org.jetbrains.annotations.Nullable net.minecraft.server.packs.resources.ResourceProvider open(net.minecraft.server.packs.PackType type, net.minecraft.resources.ResourceLocation id) throws IOException {
        for (PackResources pack : packs) {
            var provider = pack.open(type, id);
            if (provider != null) {
                return provider;
            }
        }
        return null;
    }*//*

    @Override
    @org.jetbrains.annotations.Nullable
    public net.minecraft.server.packs.resources.ResourceProvider open(
            net.minecraft.server.packs.PackType type,
            net.minecraft.resources.ResourceLocation id
    ) {
        for (PackResources pack : packs) {
            try {
                InputStream stream = pack.getResource(type, id);
                if (stream != null) {
                    // Wrap the InputStream in a ResourceProvider
                    return (resourceId) -> stream; // minimal provider example
                }
            } catch (IOException ignored) {
            }
        }
        return null;
    }

    @Override
    public java.util.Collection<net.minecraft.resources.ResourceLocation> getResources(net.minecraft.server.packs.PackType type, String namespace, String path, int depth, java.util.function.Predicate<String> filter) {
        return packs.stream()
                .flatMap(p -> p.getResources(type, namespace, path, depth, filter).stream())
                .toList();
    }

    @Override
    public boolean isHidden() {
        return false;
    }
}*/

