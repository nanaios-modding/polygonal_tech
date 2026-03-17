package com.nanaios.polygonal_tech.launch;

import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

import java.util.*;

public class SyncFieldRegistry implements ILaunchPluginService {
    private static final String SYNC_DESC = "Lcom/nanaios/polygonal_tech/util/sync/Synchronize;";
    private static final Map<String, List<String>> DATA = new HashMap<>();

    void register(String className, String fieldName) {
        DATA.computeIfAbsent(className, k -> new ArrayList<>()).add(fieldName);
    }

    public static Map<String, List<String>> getAll() {
        return Collections.unmodifiableMap(DATA);
    }

    @Override
    public String name() {
        return "polygonal_tech_sync_field_registry";
    }

    @Override
    public EnumSet<Phase> handlesClass(Type classType, boolean isEmpty, String reason) {
        if (isEmpty) return EnumSet.noneOf(Phase.class);
        return EnumSet.of(Phase.BEFORE);
    }

    @Override
    public EnumSet<Phase> handlesClass(Type classType, boolean isEmpty) {
        return null;
    }

    @Override
    public int processClassWithFlags(
            Phase phase,
            ClassNode classNode,
            Type classType,
            String reason
    ) {
        if (classNode.fields == null) return ComputeFlags.NO_REWRITE;

        for (FieldNode field : classNode.fields) {
            if (field.visibleAnnotations == null) continue;

            for (AnnotationNode ann : field.visibleAnnotations) {
                if (!SYNC_DESC.equals(ann.desc)) continue;
                register(classType.getClassName(), field.name);

                break; // 1フィールドにつき @Sync は1つ
            }
        }

        return ComputeFlags.NO_REWRITE; // バイトコード変更なし
    }
}
