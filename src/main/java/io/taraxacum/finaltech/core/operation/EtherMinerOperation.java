package io.taraxacum.finaltech.core.operation;

import io.github.thebusybiscuit.slimefun4.core.machines.MachineOperation;

/**
 * @author Final_ROOT
 * @since 2.4
 */
public class EtherMinerOperation implements MachineOperation {
    private final int etherAmount;
    private final int totalTicks;
    private int tick;

    public EtherMinerOperation(int totalTicks, int etherAmount) {
        this.totalTicks = totalTicks;
        this.etherAmount = etherAmount;
    }

    public int getEtherAmount() {
        return etherAmount;
    }

    @Override
    public void addProgress(int ticks) {
        this.tick += ticks;
    }

    @Override
    public int getProgress() {
        return this.tick;
    }

    @Override
    public int getTotalTicks() {
        return this.totalTicks;
    }
}
