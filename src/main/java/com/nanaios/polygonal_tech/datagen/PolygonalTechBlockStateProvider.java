package com.nanaios.polygonal_tech.datagen;

import com.nanaios.polygonal_tech.PolygonalTech;
import com.nanaios.polygonal_tech.registries.PolygonalTechBlockRegister;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class PolygonalTechBlockStateProvider extends BlockStateProvider {
    public PolygonalTechBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, PolygonalTech.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for(RegistryObject<Block> block : PolygonalTechBlockRegister.MACHINE_BLOCKS.getEntries()) {
            ResourceLocation location = block.getId();

            // 機械ブロックは回転させる必要があるため、horizontalBlockを使用してモデルを生成する。
            horizontalBlock(
                    block.get(),
                    models().getExistingFile(location)
            );

            // アイテムモデルも同様に生成する。
            simpleBlockItem(
                    block.get(),
                    models().getExistingFile(location)
            );
        }
    }
}
