package io.taraxacum.finaltech.core.menu.limit;

import io.taraxacum.finaltech.core.item.machine.AbstractMachine;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class BasicFrameMachineMenu extends AbstractLimitMachineMenu {
    private static final int[] BORDER = new int[]{4, 22, 31, 40, 49};
    private static final int[] INPUT_BORDER = new int[]{3, 12, 21, 30, 39, 48};
    private static final int[] OUTPUT_BORDER = new int[]{5, 14, 23, 32, 41, 50};
    private static final int[] INPUT_SLOT = new int[]{0, 1, 2, 9, 10, 11, 18, 19, 20, 27, 28, 29, 36, 37, 38, 45, 46, 47};
    private static final int[] OUTPUT_SLOT = new int[]{6, 7, 8, 15, 16, 17, 24, 25, 26, 33, 34, 35, 42, 43, 44, 51, 52, 53};

    public BasicFrameMachineMenu(@Nonnull AbstractMachine abstractMachine) {
        super(abstractMachine);
    }

    @Override
    protected int[] getBorder() {
        return BORDER;
    }

    @Override
    protected int[] getInputBorder() {
        return INPUT_BORDER;
    }

    @Override
    protected int[] getOutputBorder() {
        return OUTPUT_BORDER;
    }

    @Override
    public int[] getInputSlot() {
        return INPUT_SLOT;
    }

    @Override
    public int[] getOutputSlot() {
        return OUTPUT_SLOT;
    }
}
