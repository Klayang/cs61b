package lab14;

import lab14lib.Generator;

//public class AcceleratingSawToothGenerator extends SawToothGenerator {
//    private double factor;
//    public AcceleratingSawToothGenerator(int period, double factor) {
//        super(period);
//        this.factor = factor;
//    }
//    public double next() {
//        double res = super.next();
//        if (state % period == 0) {
//            period *= factor;
//            state *= factor;
//        }
//        return res;
//    }
//}

public class AcceleratingSawToothGenerator implements Generator {
    protected int period;
    protected int state;
    private double factor;
    public AcceleratingSawToothGenerator(int period, double factor) {
        this.period = period;
        state = 0;
        this.factor = factor;
    }
    public double next() {
        state += 1;
        double res = normalize(state % period);
        if (state % period == 0) {
            period *= factor;
            state *= factor;
        }
        return res;
    }
    protected double normalize(int value) {
        return (double)value / (double)period * 2 - 1.0;
    }
}
