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
        return state & (state >>> 3) % period;
    }
}
