package com.nanaios.polygonal_tech.main.util;

import com.nanaios.polygonal_tech.main.PolygonalTech;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class AnnotationScanner {
    private final List<String> scannedMods = new ArrayList<>();
    private final Type annotationType;

    public AnnotationScanner(Class<?> annotationClass) {
        this.annotationType = Type.getType(annotationClass);
    }

    public void scan(String modId, Consumer<ModFileScanData.AnnotationData> annotationConsumer) {
        if(scannedMods.contains(modId)) {
            PolygonalTech.LOGGER.warn("Mod {} has already been scanned for synchronization annotations. Skipping.", modId);
            return;
        }
        scannedMods.add(modId);

        IModFileInfo info = ModList.get().getModFileById(modId);
        ModFileScanData scanData = info.getFile().getScanResult();
        Set<ModFileScanData.AnnotationData> annotationData = scanData.getAnnotations();
        for (ModFileScanData.AnnotationData data : annotationData) {
            if(!data.annotationType().equals(annotationType)) continue;
            annotationConsumer.accept(data);
        }
    }
}
