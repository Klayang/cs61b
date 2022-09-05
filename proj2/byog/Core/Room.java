package byog.Core;

public final class Room implements Comparable {
    protected final int startX;
    protected final int startY;
    protected final int width;
    protected final int height;
    protected final int centerX;
    protected final int centerY;

    public Room(int x, int y, int w, int h) {
        startX = x;
        startY = y;
        width = w;
        height = h;
        centerX = startX + width / 2;
        centerY = startY + height / 2;
    }

    @Override
    public int compareTo(Object o) {
        Room anotherRoom = (Room) o;
        if (this.startX != anotherRoom.startX) return this.startX - anotherRoom.startX;
        return this.startY - anotherRoom.startY;
    }

    /* Given another possible room, test if this room overlaps the given one */
    public boolean overlap(int smallX, int smallY, int largeX, int largeY) {
        return overlapHelper(smallX, largeX, startX, startX + width - 1) &&
                overlapHelper(smallY, largeY, startY, startY + height - 1);
    }

    private boolean overlapHelper(int small, int large, int startXOrY, int endXOrY) {
        if (small < startXOrY) return large >= startXOrY;
        else if (small > startXOrY) return endXOrY >= small;
        else return true;
    }
}
