package com.nanaios.polygonal_tech.util.save;

import com.mojang.datafixers.util.Pair;
import com.nanaios.polygonal_tech.PolygonalTech;
import com.nanaios.polygonal_tech.util.AnnotationScanner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveToNBTMap {
    @SuppressWarnings("rawtypes")
    public static final Map<Class, List<Pair<Field, String>>> saveToNBTFields = new HashMap<>();
    private static final AnnotationScanner annotationScanner = new AnnotationScanner(SaveToNBT.class);

    @SuppressWarnings("rawtypes")
    public static void scanSaveToNBTAnnotation(String modId) {
        // 一時的なマップを作成して、スキャン中にフィールドと保存キーのペアを保存
        Map<Class, List<Pair<Field, String>>> tempMap = new HashMap<>();

        annotationScanner.scan(modId, data -> {
            try {
                Class clazz = Class.forName(data.clazz().getClassName(), false, Thread.currentThread().getContextClassLoader());
                Field field = clazz.getDeclaredField(data.memberName());

                // アノテーションのvalue()から保存キーを取得。指定されていない場合はフィールド名を使用
                String key = (String) data.annotationData().get("value");
                if(key == null || key.isEmpty()) {
                    key = field.getName();
                }

                // フィールドと保存キーのペアを保存
                Pair<Field, String> pair = Pair.of(field, key);
                tempMap.computeIfAbsent(clazz, c -> new ArrayList<>()).add(pair);
                PolygonalTech.LOGGER.debug("Registered SaveToNBT field: {}(save key : {}) in class {}", data.memberName(),key, data.clazz().getClassName());
            } catch (ClassNotFoundException e) {
                PolygonalTech.LOGGER.error("Failed to load class for SaveToNBT: {}", data.clazz().getClassName(), e);
            } catch (NoSuchFieldException e) {
                PolygonalTech.LOGGER.error("Failed to find field for SaveToNBT: {} in class {}", data.memberName(), data.clazz().getClassName(), e);
            }
        });
        // 継承階層を遡って、スーパークラスのフィールドも含める
        tempMap.forEach((clazz, pairs) -> {
            Class superClass = clazz;
            while ((superClass = superClass.getSuperclass()) != null) {

                // スーパークラスのフィールドと保存キーのペアを取得してListに追加
                List<Pair<Field, String>> superPairs = tempMap.get(superClass);
                if (superPairs != null) {
                    pairs.addAll(superPairs);
                }

                // スーパークラスのフィールドと保存キーのペアを取得してListに追加
                superPairs  = saveToNBTFields.get(superClass);
                if (superPairs != null) {
                    pairs.addAll(superPairs);
                }
            }
        });

        // スキャンが完了したら、最終的なマップを保存
        saveToNBTFields.putAll(tempMap);
    }
}
