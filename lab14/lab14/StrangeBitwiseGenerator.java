package lab14;

import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator {
    protected int period;
    protected int state;
    public StrangeBitwiseGenerator(int period) {
        this.period = period;
        state = 0;
    }
    @Override
    public double next() {
        state += 1;
        return normalize(state & (state >>> 3) % period);
    }
    protected double normalize(int value) {
        return (double)value / (double)period * 2 - 1.0;
    }
}
