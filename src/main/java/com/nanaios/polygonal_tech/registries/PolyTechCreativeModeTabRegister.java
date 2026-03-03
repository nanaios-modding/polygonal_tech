package com.nanaios.polygonal_tech.registries;

import com.nanaios.polygonal_tech.PolygonalTechLang;
import com.nanaios.polygonal_tech.registries.base.MultipleRegister;
import com.nanaios.polygonal_tech.registries.base.WrapperDeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.UnknownNullability;

public class PolyTechCreativeModeTabRegister {
    public static final MultipleRegister<CreativeModeTab> CREATIVE_TABS = new MultipleRegister<>(Registries.CREATIVE_MODE_TAB);
    public static final WrapperDeferredRegister<CreativeModeTab> MAIN_TAB = CREATIVE_TABS.create();

    static {
        MAIN_TAB.register("main_tab", () -> CreativeModeTab.builder()
                .title(PolygonalTechLang.MAIN_TAB.get())
                .icon(() -> new ItemStack(PolyTechItemRegister.ELEMENT_3.get()))
                .displayItems((parameters, output) -> {
                    displayItem(output, PolyTechItemRegister.ELEMENTS);
                })
                .build());
    }

    static public void displayItem(CreativeModeTab.Output output, @UnknownNullability WrapperDeferredRegister<Item> itemRegister) {
        itemRegister.getEntries().forEach(entry -> output.accept(entry.get()));
    }
}
