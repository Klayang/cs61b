package lab14;

import lab14lib.Generator;

public class SawToothGenerator implements Generator {
    protected int period;
    protected int state;
    public SawToothGenerator(int period) {
        this.period = period;
        state = 0;
    }
    @Override
    public double next() {
        state += 1;
        return normalize(state % period);
    }
    protected double normalize(int value) {
        return (double)value / (double)period * 2 - 1.0;
    }
}
