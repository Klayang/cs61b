package byog.Core;

public final class Hallway {
    public final int startX;
    public final int startY;
    public final int endX;
    public final int endY;
    public final int intersectionX;
    public Hallway(int startX, int startY, int endX, int endY, int intersectionX) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.intersectionX = intersectionX;
    }
}
