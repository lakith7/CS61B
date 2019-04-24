package byow.TileEngine;

public class RoomInfoHolder {

    public TETile[][] grid;

    public int usedSpaces;

    public RoomInfoHolder(TETile[][] graph, int ratio) {
        grid = graph;
        usedSpaces = ratio;
    }

    public TETile[][] getGrid() {
        return grid;
    }

    public int getUsedSpaces() {
        return usedSpaces;
    }

}
