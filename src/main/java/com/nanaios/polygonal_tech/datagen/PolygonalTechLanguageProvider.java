package com.nanaios.polygonal_tech.datagen;

import com.nanaios.polygonal_tech.PolygonalTech;
import com.nanaios.polygonal_tech.registries.PolygonalTechBlockRegister;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class PolygonalTechLanguageProvider extends LanguageProvider {
    public PolygonalTechLanguageProvider(PackOutput output) {
        super(output, PolygonalTech.MODID, "ja_jp");
    }

    @Override
    protected void addTranslations() {
        add(PolygonalTechBlockRegister.PHOTOLYSIS_MACHINE_MK1.get(),"光分解機MK.1");
    }
}
