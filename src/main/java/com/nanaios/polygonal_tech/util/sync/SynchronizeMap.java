package com.nanaios.polygonal_tech.util.sync;

import com.nanaios.polygonal_tech.PolygonalTech;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.moddiscovery.ModAnnotation;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.objectweb.asm.Type;

import java.lang.reflect.Field;
import java.util.*;

public class SynchronizeMap {
    @SuppressWarnings("rawtypes")
    public static final Map<Class, List<Field>> alwaysSynchronizedFields = new HashMap<>();
    @SuppressWarnings("rawtypes")
    public static final Map<Class, List<Field>> inGuiSynchronizedFields = new HashMap<>();

    private static final List<String> scannedMods = new ArrayList<>();

    private static final Type syncType = Type.getType("Lcom/nanaios/polygonal_tech/util/sync/Synchronize;");

    @SuppressWarnings("rawtypes")
    public static void scanSynchronizeAnnotation(String modId) {
        if(scannedMods.contains(modId)) {
            PolygonalTech.LOGGER.warn("Mod {} has already been scanned for synchronization annotations. Skipping.", modId);
            return;
        }
        scannedMods.add(modId);

        IModFileInfo info = ModList.get().getModFileById(modId);
        ModFileScanData scanData = info.getFile().getScanResult();
        Set<ModFileScanData.AnnotationData> annotationData = scanData.getAnnotations();
        for (ModFileScanData.AnnotationData data : annotationData) {
            if(!data.annotationType().equals(syncType)) continue;

            ModAnnotation.EnumHolder holder = (ModAnnotation.EnumHolder)data.annotationData().get("value");
            Synchronize.Type syncKind = Synchronize.Type.valueOf(holder.getValue());
            try {
                Class clazz = Class.forName(data.clazz().getClassName(),false,Thread.currentThread().getContextClassLoader());
                Field field = clazz.getDeclaredField(data.memberName());

                List<Field> list = switch (syncKind) {
                    case ALWAYS -> alwaysSynchronizedFields.computeIfAbsent(clazz, c -> new ArrayList<>());
                    case IN_GUI -> inGuiSynchronizedFields.computeIfAbsent(clazz, c -> new ArrayList<>());
                };
                list.add(field);
                PolygonalTech.LOGGER.debug("Registered synchronized field: {} in class {} with sync type {}", data.memberName(), data.clazz().getClassName(), syncKind);
            } catch (ClassNotFoundException e) {
                PolygonalTech.LOGGER.error("Failed to load class for synchronization: {}", data.clazz().getClassName(), e);
            } catch (NoSuchFieldException e) {
                PolygonalTech.LOGGER.error("Failed to find field for synchronization: {} in class {}", data.memberName(), data.clazz().getClassName(), e);
            }
        }


    }
}
