package byow.TileEngine;

public class RoomInfoHolder {

    private TETile[][] grid;

    private int usedSpaces;

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
