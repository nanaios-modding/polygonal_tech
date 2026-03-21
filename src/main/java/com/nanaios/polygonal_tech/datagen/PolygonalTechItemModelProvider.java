package com.nanaios.polygonal_tech.datagen;

import com.nanaios.polygonal_tech.PolygonalTech;
import com.nanaios.polygonal_tech.registries.PolygonalTechItemRegister;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.loaders.DynamicFluidContainerModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class PolygonalTechItemModelProvider extends ItemModelProvider {
    public PolygonalTechItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PolygonalTech.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (RegistryObject<Item> bucket : PolygonalTechItemRegister.BUCKETS.getEntries()) {
            ResourceLocation location = bucket.getId();
            if(location == null) continue;

            Item item = bucket.get();
            if(!(item instanceof BucketItem bucketItem)) continue;
            Fluid fluid = bucketItem.getFluid();

            // バケットアイテムは、既存のバケットモデルを使用して生成する。
            withExistingParent(
                    location.getPath(),
                    "forge:item/bucket"
            ).customLoader(DynamicFluidContainerModelBuilder::begin).fluid(fluid).end();
        }
    }
}
