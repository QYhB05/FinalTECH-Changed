package io.taraxacum.finaltech.core.dto;

import io.taraxacum.finaltech.core.helper.CargoFilter;
import io.taraxacum.finaltech.core.helper.CargoLimit;
import io.taraxacum.finaltech.core.helper.SlotSearchOrder;
import io.taraxacum.finaltech.core.helper.SlotSearchSize;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Final_ROOT
 * @see io.taraxacum.finaltech.util.CargoUtil#doCargo(CargoDTO, String)
 * @since 2.0
 */
public class CargoDTO {
    private JavaPlugin javaPlugin;

    /**
     * Source #{@link Location} of #{@link BlockMenu} or #{@link Inventory}
     */
    private Block inputBlock;

    /**
     * #{@link SlotSearchSize}
     */
    private String inputSize;

    /**
     * #{@link SlotSearchOrder}
     */
    private String inputOrder;

    /**
     * Target #{@link Location} of #{@link BlockMenu} or #{@link Inventory}
     */
    private Block outputBlock;

    /**
     * #{@link SlotSearchSize}
     */
    private String outputSize;

    /**
     * #{@link SlotSearchOrder}
     */
    private String outputOrder;

    /**
     * Number limited in one cargo action
     */
    private int cargoNumber;

    /**
     * #{@link CargoLimit}
     */
    private String cargoLimit;

    /**
     * #{@link CargoFilter}
     */
    private String cargoFilter;

    /**
     * #{@link Inventory} for #{@link CargoFilter} to use
     */
    private Inventory filterInv;

    /**
     * the slots of the filterInv to be used
     */
    private int[] filterSlots;

    public CargoDTO() {

    }

    public CargoDTO(JavaPlugin javaPlugin, Block inputBlock, String inputSize, String inputOrder, Block outputBlock, String outputSize, String outputOrder, int cargoNumber, String cargoLimit, String cargoFilter, Inventory filterInv, int[] filterSlots) {
        this.javaPlugin = javaPlugin;
        this.inputBlock = inputBlock;
        this.inputSize = inputSize;
        this.inputOrder = inputOrder;
        this.outputBlock = outputBlock;
        this.outputSize = outputSize;
        this.outputOrder = outputOrder;
        this.cargoNumber = cargoNumber;
        this.cargoLimit = cargoLimit;
        this.cargoFilter = cargoFilter;
        this.filterInv = filterInv;
        this.filterSlots = filterSlots;
    }

    public JavaPlugin getJavaPlugin() {
        return javaPlugin;
    }

    public void setJavaPlugin(JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    public Block getInputBlock() {
        return inputBlock;
    }

    public void setInputBlock(Block inputBlock) {
        this.inputBlock = inputBlock;
    }

    public String getInputSize() {
        return inputSize;
    }

    public void setInputSize(String inputSize) {
        this.inputSize = inputSize;
    }

    public String getInputOrder() {
        return inputOrder;
    }

    public void setInputOrder(String inputOrder) {
        this.inputOrder = inputOrder;
    }

    public Block getOutputBlock() {
        return outputBlock;
    }

    public void setOutputBlock(Block outputBlock) {
        this.outputBlock = outputBlock;
    }

    public String getOutputSize() {
        return outputSize;
    }

    public void setOutputSize(String outputSize) {
        this.outputSize = outputSize;
    }

    public String getOutputOrder() {
        return outputOrder;
    }

    public void setOutputOrder(String outputOrder) {
        this.outputOrder = outputOrder;
    }

    public int getCargoNumber() {
        return cargoNumber;
    }

    public void setCargoNumber(int cargoNumber) {
        this.cargoNumber = cargoNumber;
    }

    public String getCargoLimit() {
        return cargoLimit;
    }

    public void setCargoLimit(String cargoLimit) {
        this.cargoLimit = cargoLimit;
    }

    public String getCargoFilter() {
        return cargoFilter;
    }

    public void setCargoFilter(String cargoFilter) {
        this.cargoFilter = cargoFilter;
    }

    public Inventory getFilterInv() {
        return filterInv;
    }

    public void setFilterInv(Inventory filterInv) {
        this.filterInv = filterInv;
    }

    public int[] getFilterSlots() {
        return filterSlots;
    }

    public void setFilterSlots(int[] filterSlots) {
        this.filterSlots = filterSlots;
    }
}
