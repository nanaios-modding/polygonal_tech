package com.nanaios.polygonal_tech.container;

import com.nanaios.polygonal_tech.capability.base.BaseProvider;
import com.nanaios.polygonal_tech.capability.interfaces.ICapabilityMarker;
import com.nanaios.polygonal_tech.container.base.BaseContainer;
import com.nanaios.polygonal_tech.util.Directions;

import net.minecraft.core.Direction;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/// BaseContainerをProviderに登録するためのBuilderクラス
public class ContainerBuilder<T extends ICapabilityMarker> {
    private List<Pair<BaseContainer<T>, Directions>> containers;

    public ContainerBuilder() {}

    /// Builderにコンテナを登録する
    public void add(BaseContainer<T> container, Directions directions) {
        Pair<BaseContainer<T>, Directions> pair = Pair.of(container, directions);
        containers.add(pair);
    }

    /// Builderに登録されたコンテナをProviderに登録する
    public void build(BaseProvider<T, ?> provider) {
        for(Pair<BaseContainer<T>, Directions> pair : containers) {
            BaseContainer<T> container = pair.getLeft();
            Directions directions = pair.getRight();

            if(directions.inputs != null) {
                for(Direction side : directions.inputs) {
                    provider.add(side,container, BaseContainer.IOType.INPUT);
                }
            }

            if(directions.outputs != null) {
                for(Direction side : directions.outputs) {
                    provider.add(side,container, BaseContainer.IOType.OUTPUT);
                }
            }
        }
    }
}
