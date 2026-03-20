package com.nanaios.polygonal_tech.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class PolygonalTechAtlasProvider implements DataProvider {
    private static final String PROVIDER_NAME = "PolygonalTech Atlases";
    private final Path outputPath;

    public PolygonalTechAtlasProvider(PackOutput output) {
        this.outputPath = output.getOutputFolder(PackOutput.Target.RESOURCE_PACK)
                .resolve("assets/minecraft/atlases/blocks.json");
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cachedOutput) {
        JsonObject rootObject = new JsonObject();
        JsonArray sourceArray = new JsonArray();

        JsonObject directoryObject = new JsonObject();
        directoryObject.addProperty("type", "directory");
        directoryObject.addProperty("source", "fluid");
        directoryObject.addProperty("prefix", "fluid/");
        sourceArray.add(directoryObject);

        rootObject.add("sources", sourceArray);

        return DataProvider.saveStable(cachedOutput, rootObject, this.outputPath);
    }

    @Override
    public @NotNull String getName() {
        return PROVIDER_NAME;
    }
}

