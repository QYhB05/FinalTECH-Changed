package io.taraxacum.libs.slimefun.dto;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class RecipeTypeRegistry {
    private static volatile RecipeTypeRegistry instance;
    boolean init = false;
    private Set<RecipeType> recipeTypeSet;
    private Map<RecipeType, List<SlimefunItem>> recipeSlimefunItemMap;

    private RecipeTypeRegistry() {
        this.init();
    }

    @Nonnull
    public static RecipeTypeRegistry getInstance() {
        if (instance == null) {
            synchronized (RecipeTypeRegistry.class) {
                if (instance == null) {
                    instance = new RecipeTypeRegistry();
                }
            }
        }
        return instance;
    }

    public synchronized void init() {
        if (this.init) {
            return;
        }

        this.reload();

        this.init = true;
    }

    public void reload() {
        Set<RecipeType> recipeTypeSet = new HashSet<>();
        Map<RecipeType, List<SlimefunItem>> recipeSlimefunItemMap = new HashMap<>();

        RecipeType recipeType;
        List<SlimefunItem> slimefunItemList;
        for (SlimefunItem slimefunItem : Slimefun.getRegistry().getEnabledSlimefunItems()) {
            recipeType = slimefunItem.getRecipeType();
            if (recipeSlimefunItemMap.containsKey(recipeType)) {
                slimefunItemList = recipeSlimefunItemMap.get(recipeType);
                slimefunItemList.add(slimefunItem);
            } else {
                slimefunItemList = new ArrayList<>();
                slimefunItemList.add(slimefunItem);
                recipeSlimefunItemMap.put(recipeType, slimefunItemList);
                recipeTypeSet.add(recipeType);
            }
        }

        this.recipeTypeSet = recipeTypeSet;
        this.recipeSlimefunItemMap = recipeSlimefunItemMap;
    }

    public void update() {
        if (this.init) {
            this.reload();
        } else {
            this.init();
        }
    }

    public Set<RecipeType> getRecipeTypeSet() {
        return this.recipeTypeSet;
    }

    public Map<RecipeType, List<SlimefunItem>> getRecipeSlimefunItemMap() {
        return recipeSlimefunItemMap;
    }

    @Nullable
    public RecipeType getRecipeTypeById(String id) {
        for (RecipeType recipeType : this.getRecipeTypeSet()) {
            if (recipeType.getKey().getKey().equals(id)) {
                return recipeType;
            }
        }
        return null;
    }

    @Nonnull
    public List<SlimefunItem> getByRecipeType(@Nonnull RecipeType recipeType) {
        if (this.recipeSlimefunItemMap.containsKey(recipeType)) {
            return this.recipeSlimefunItemMap.get(recipeType);
        } else {
            return new ArrayList<>();
        }
    }
}
