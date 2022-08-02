public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    public static final double G = 6.67e-11;
    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }
    public Planet(Planet p){
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.mass = p.mass;
        this.imgFileName = p.imgFileName;
    }
    public double calcDistance(Planet p){
        return Math.sqrt((this.xxPos - p.xxPos) * (this.xxPos - p.xxPos) + (this.yyPos - p.yyPos) * (this.yyPos - p.yyPos));
    }
    public double calcForceExertedBy(Planet p){
        return Planet.G * this.mass * p.mass / (this.calcDistance(p) * this.calcDistance(p));
    }
    public double calcForceExertedByX(Planet p){
        return this.calcForceExertedBy(p) * (p.xxPos - this.xxPos) / this.calcDistance(p);
    }
    public double calcForceExertedByY(Planet p){
        return this.calcForceExertedBy(p) * (p.yyPos - this.yyPos) / this.calcDistance(p);
    }
    public void update(double dt, double fX, double fY){
        double ax = fX / mass, ay = fY / mass;
        xxVel += ax * dt;
        yyVel += ay * dt;
        xxPos += xxVel * dt;
        yyPos += yyVel * dt;
    }
    public void draw(){
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
    public double calcNetForceExertedByX(Planet[] planets) {
        double res = 0.0;
        for (Planet p: planets)
            if (!this.equals(p))
                res += this.calcForceExertedByX(p);
        return res;
    }
    public double calcNetForceExertedByY(Planet[] planets) {
        double res = 0.0;
        for (Planet p: planets)
            if (!this.equals(p))
                res += this.calcForceExertedByY(p);
        return res;
    }
}
