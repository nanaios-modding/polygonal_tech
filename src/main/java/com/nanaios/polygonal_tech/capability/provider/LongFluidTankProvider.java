package com.nanaios.polygonal_tech.capability.provider;

import com.nanaios.polygonal_tech.capability.base.BaseProvider;
import com.nanaios.polygonal_tech.capability.interfaces.ILongFluidTank;
import com.nanaios.polygonal_tech.container.impl.CombinedLongFluidContainer;

public class LongFluidTankProvider extends BaseProvider<ILongFluidTank, CombinedLongFluidContainer> {
    public static String NBT_KEY = "long_fluid_tank";

    public LongFluidTankProvider() {
        super(CombinedLongFluidContainer::new);
    }
}
