package com.nanaios.polygonal_tech.util.sync;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyncFieldRegistry {
    private static final Map<String, List<String>> DATA = new HashMap<>();

    public static void register(String className, String fieldName) {
        DATA.computeIfAbsent(className, k -> new ArrayList<>()).add(fieldName);
    }

    public static Map<String, List<String>> getAll() {
        return DATA;
    }

    public static List<String> getFields(String className) {
        return DATA.getOrDefault(className, List.of());
    }
}
