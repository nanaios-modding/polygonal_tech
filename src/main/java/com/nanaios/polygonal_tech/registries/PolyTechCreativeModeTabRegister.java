package com.nanaios.polygonal_tech.registries;

import com.nanaios.polygonal_tech.PolygonalTechLang;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;

public class PolyTechCreativeModeTabRegister {
    public static final MultipleRegister<CreativeModeTab> CREATIVE_TABS = new MultipleRegister<>(Registries.CREATIVE_MODE_TAB);
    public static final DeferredRegister<CreativeModeTab> MAIN_TAB = CREATIVE_TABS.create();

    static {
            MAIN_TAB.register("main_tab", () -> CreativeModeTab.builder()
                    .title(PolygonalTechLang.MAIN_TAB.get())
                    .icon(() ->new ItemStack(PolyTechItemRegister.ELEMENT_3.get()))
                    .build());
    }
}
