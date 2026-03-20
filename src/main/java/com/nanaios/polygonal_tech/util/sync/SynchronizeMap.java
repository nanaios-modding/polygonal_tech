package com.nanaios.polygonal_tech.util.sync;

import com.nanaios.polygonal_tech.PolygonalTech;
import com.nanaios.polygonal_tech.util.AnnotationScanner;
import net.minecraftforge.fml.loading.moddiscovery.ModAnnotation;

import java.lang.reflect.Field;
import java.util.*;

public class SynchronizeMap {
    @SuppressWarnings("rawtypes")
    public static final Map<Class, List<Field>> alwaysSynchronizedFields = new HashMap<>();
    @SuppressWarnings("rawtypes")
    public static final Map<Class, List<Field>> inGuiSynchronizedFields = new HashMap<>();

    private static final AnnotationScanner annotationScanner = new AnnotationScanner(Synchronize.class);

    @SuppressWarnings("rawtypes")
    public static void scanSynchronizeAnnotation(String modId) {
        Map<Class, List<Field>> tempAlwaysSynchronizedFields = new HashMap<>();
        Map<Class, List<Field>> tempInGuiSynchronizedFields = new HashMap<>();

        annotationScanner.scan(modId,data -> {
            ModAnnotation.EnumHolder holder = (ModAnnotation.EnumHolder)data.annotationData().get("value");
            Synchronize.Type syncKind = Synchronize.Type.valueOf(holder.getValue());
            try {
                Class clazz = Class.forName(data.clazz().getClassName(),false,Thread.currentThread().getContextClassLoader());
                Field field = clazz.getDeclaredField(data.memberName());

                List<Field> list = switch (syncKind) {
                    case ALWAYS -> tempAlwaysSynchronizedFields.computeIfAbsent(clazz, c -> new ArrayList<>());
                    case IN_GUI -> tempInGuiSynchronizedFields.computeIfAbsent(clazz, c -> new ArrayList<>());
                };
                list.add(field);
                PolygonalTech.LOGGER.debug("Registered synchronized field: {} in class {} with sync type {}", data.memberName(), data.clazz().getClassName(), syncKind);
            } catch (ClassNotFoundException e) {
                PolygonalTech.LOGGER.error("Failed to load class for synchronization: {}", data.clazz().getClassName(), e);
            } catch (NoSuchFieldException e) {
                PolygonalTech.LOGGER.error("Failed to find field for synchronization: {} in class {}", data.memberName(), data.clazz().getClassName(), e);
            }
        });

        // 継承階層を遡って、スーパークラスのフィールドも含める
        tempAlwaysSynchronizedFields.forEach((clazz, fields) -> {
            Class superClass = clazz;
            while ((superClass = superClass.getSuperclass()) != null) {
                List<Field> superFields = tempAlwaysSynchronizedFields.get(superClass);
                if (superFields != null) {
                    fields.addAll(superFields);
                }
                superFields = alwaysSynchronizedFields.get(superClass);
                if (superFields != null) {
                    fields.addAll(superFields);
                }
            }
        });

        tempInGuiSynchronizedFields.forEach((clazz, fields) -> {
            Class superClass = clazz;
            while ((superClass = superClass.getSuperclass()) != null) {
                List<Field> superFields = tempInGuiSynchronizedFields.get(superClass);
                if (superFields != null) {
                    fields.addAll(superFields);
                }
                superFields = inGuiSynchronizedFields.get(superClass);
                if (superFields != null) {
                    fields.addAll(superFields);
                }
            }
        });
    }
}
