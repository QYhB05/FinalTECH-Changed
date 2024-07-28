package io.taraxacum.finaltech.core.item.unusable;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.interfaces.DigitalItem;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.interfaces.UnCopiableItem;
import io.taraxacum.finaltech.util.RecipeUtil;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class DigitalNumber extends UnusableSlimefunItem implements RecipeItem, DigitalItem, UnCopiableItem {
    private static final Map<Integer, DigitalNumber> DIGIT_MAP = new HashMap<>(16);

    private final int digit;

    public DigitalNumber(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int digit) {
        super(itemGroup, item, recipeType, recipe);
        this.digit = digit;
        if (DIGIT_MAP.containsKey(this.getDigit())) {
            throw new IllegalArgumentException("duplicated digit while registering " + this.getId());
        }
        DIGIT_MAP.put(this.getDigit(), this);
    }

    @Nullable
    public static DigitalNumber getByDigit(int digit) {
        return DIGIT_MAP.get(digit);
    }

    @Nonnull
    public static List<SlimefunItem> getAll() {
        List<SlimefunItem> result = new ArrayList<>(DIGIT_MAP.size());
        for (int digit : DIGIT_MAP.keySet().stream().sorted(Comparator.comparingInt(o -> o)).collect(Collectors.toList())) {
            result.add(DIGIT_MAP.get(digit));
        }

        return result;
    }

    @Override
    public int getDigit() {
        return this.digit;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTechChanged.getLanguageManager(), this);
    }
}
