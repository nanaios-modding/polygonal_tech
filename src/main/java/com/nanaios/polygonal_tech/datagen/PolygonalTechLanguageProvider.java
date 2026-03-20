package com.nanaios.polygonal_tech.datagen;

import com.nanaios.polygonal_tech.PolygonalTech;
import com.nanaios.polygonal_tech.lang.ILang;
import com.nanaios.polygonal_tech.lang.PolygonalTechToolTipLang;
import com.nanaios.polygonal_tech.registries.PolygonalTechBlockRegister;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class PolygonalTechLanguageProvider extends LanguageProvider {
    public PolygonalTechLanguageProvider(PackOutput output) {
        super(output, PolygonalTech.MODID, "ja_jp");
    }

    @Override
    protected void addTranslations() {
        add(PolygonalTechBlockRegister.PHOTOLYSIS_MACHINE_MK1.get(), "光分解機MK.1");
        add("fluid_type.polygonal_tech.third_flow", "3次流元");
        add(PolygonalTechToolTipLang.FLUID_TANK_EMPTY, "空");
        add(PolygonalTechToolTipLang.FLUID_TANK_AMOUNT, "%s / %s mB");
    }

    private void add(ILang lang, String translation) {
        add(lang.getKey(), translation);
    }
}
