package io.taraxacum.finaltech.core.helper;

import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.libs.slimefun.dto.BlockStorageHelper;
import io.taraxacum.libs.slimefun.dto.BlockStorageLoreHelper;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Final_ROOT
 */
public final class CargoNumberMode {
    public static final String KEY = "ccm";
    public static final String KEY_INPUT = "ccmi";
    public static final String KEY_OUTPUT = "ccmo";

    public static final String VALUE_UNIVERSAL = "u";
    public static final String VALUE_STANDALONE = "s";

    private static final String UNIVERSAL_LORE = FinalTechChanged.getLanguageString("helper", "CARGO_NUMBER_MODE", "universal", "lore1");
    private static final String STANDALONE_LORE = FinalTechChanged.getLanguageString("helper", "CARGO_NUMBER_MODE", "standalone", "lore1");

    public static final BlockStorageLoreHelper HELPER = BlockStorageLoreHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY, 1, new LinkedHashMap<>() {{
        this.put(VALUE_UNIVERSAL, List.of(UNIVERSAL_LORE));
        this.put(VALUE_STANDALONE, List.of(STANDALONE_LORE));
    }});

    public static final BlockStorageLoreHelper INPUT_HELPER = BlockStorageLoreHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY_INPUT, 1, new LinkedHashMap<>() {{
        this.put(VALUE_UNIVERSAL, List.of(UNIVERSAL_LORE));
        this.put(VALUE_STANDALONE, List.of(STANDALONE_LORE));
    }});

    public static final BlockStorageLoreHelper OUTPUT_HELPER = BlockStorageLoreHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY_OUTPUT, 1, new LinkedHashMap<>() {{
        this.put(VALUE_UNIVERSAL, List.of(UNIVERSAL_LORE));
        this.put(VALUE_STANDALONE, List.of(STANDALONE_LORE));
    }});
}
