
public class NBody {
    public static double readRadius(String fileName){
        In in = new In(fileName);
        in.readInt();
        return in.readDouble();
    }
    public static Planet[] readPlanets(String fileName){
        In in = new In(fileName);
        int numOfPlanets = in.readInt();
        Planet[] planets = new Planet[numOfPlanets];
        in.readDouble();
        for (int i = 0; i < numOfPlanets; ++i)
            planets[i] = new Planet(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readString());
        return planets;
    }
    public static void main(String[] args){
        double T = Double.parseDouble(args[0]), dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radiusOfUniverse = NBody.readRadius(filename);
        Planet[] planets = NBody.readPlanets(filename);
//        equalDouble(T, 3.14);
//        equalDouble(dt, 6.28);
        StdDraw.setScale(-radiusOfUniverse, radiusOfUniverse);
        StdDraw.clear();
        StdDraw.enableDoubleBuffering();
//        StdDraw.picture(0, 0, "images/starfield.jpg");
//        for (Planet p: planets)
//            p.draw();
//        StdDraw.show();
        double time = 0.0;
        while (time < T){
            double[] xForces = new double[planets.length], yForces = new double[planets.length];
            for (int i = 0; i < xForces.length; ++i){
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            for (int i = 0; i < xForces.length; ++i)
                planets[i].update(dt, xForces[i], yForces[i]);
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for (Planet p: planets)
                p.draw();
            StdDraw.show();
            StdDraw.pause(10);
            time += dt;
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radiusOfUniverse);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
//    public static void equalDouble(double d, double s){
//        if (d == s) System.out.print("Pass: ");
//        else System.out.print("Fail: ");
//        System.out.println("Expect: " + d + ", and you gave: " + s);
//    }
}
