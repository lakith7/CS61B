package byow.TileEngine;

import java.util.Random;
import byow.Core.RandomUtils;

public class RandomWorldGenerator {

    RandomUtils pseudoRand = new RandomUtils();

    public TETile[][] worldMaker(int width, int height, int seed) {
        int usedSpaces = 0;
        TETile[][] output = new TETile[width][height];
        RoomInfoHolder allInfo;
        Random rand = new Random(seed);
        int[] prevRoom = new int[5];
        int[] newRoom = new int[5];
        /* Creates random rooms. */
        while (true) {
            if (usedSpaces > (0.3 * height * width)) {
                break;
            }
            int xlcoordinate = pseudoRand.uniform(rand, 2,  width - 5);
            int xrcoordinate = pseudoRand.uniform(rand, xlcoordinate + 2, width - 2);
            int ybcoordinate = pseudoRand.uniform(rand, 2, height - 5);
            int ytcoordinate = pseudoRand.uniform(rand, ybcoordinate + 2, height - 2);
            newRoom[0] = xlcoordinate;
            newRoom[1] = xrcoordinate;
            newRoom[2] = ybcoordinate;
            newRoom[3] = ytcoordinate;
            newRoom[4] = 5;
            allInfo = createRandRoom(xlcoordinate, xrcoordinate, ytcoordinate,
                    ybcoordinate, output, usedSpaces);
            usedSpaces = allInfo.getUsedSpaces();
            if (prevRoom[4] == 5 && (newRoom != prevRoom)) {
                allInfo = randConnectRoom(newRoom, prevRoom, output, allInfo.getUsedSpaces(), seed);
                usedSpaces = allInfo.getUsedSpaces();
            }
            usedSpaces = allInfo.getUsedSpaces();
            output = allInfo.getGrid();
            prevRoom[0] = xlcoordinate;
            prevRoom[1] = xrcoordinate;
            prevRoom[2] = ybcoordinate;
            prevRoom[3] = ytcoordinate;
            prevRoom[4] = 5;
        }
        return output;
    }

    /* int[] = xlcoordinate, xrcoordinate, ybcoordinate, ytcoordinate. */
    public RoomInfoHolder randConnectRoom(int[] firstRoom, int[] secondRoom,
                                           TETile[][] output, int usedSpaces, int seed) {
        int[] point1 = randPointOnRoom(firstRoom, seed);
        int[] point2 = randPointOnRoom(secondRoom, seed + 1);
        Infrastructure holder = new Infrastructure();
        RoomInfoHolder newHolder;
        newHolder = holder.createHallway(point1[0], point1[1],
                point2[0], point2[1], output, usedSpaces);
        if ((point1[0] != 0) && (output[point1[0] + 1][point1[1]] == Tileset.FLOOR)
                && (output[point1[0] - 1][point1[1]] == Tileset.FLOOR)) {
            output[point1[0]][point1[1]] = Tileset.FLOOR;
        }
        if ((point2[0] != 0) && (output[point2[0] + 1][point2[1]] == Tileset.FLOOR)
                && (output[point2[0] - 1][point2[1]] == Tileset.FLOOR)) {
            output[point2[0]][point2[1]] = Tileset.FLOOR;
        }
        if ((point1[1] != 0) && (output[point1[0]][point1[1] + 1] == Tileset.FLOOR)
                && (output[point1[0]][point1[1] - 1] == Tileset.FLOOR)) {
            output[point1[0]][point1[1]] = Tileset.FLOOR;
        }
        if ((point2[1] != 0) && (output[point2[0]][point2[1] + 1] == Tileset.FLOOR)
                && (output[point2[0]][point2[1] - 1] == Tileset.FLOOR)) {
            output[point2[0]][point2[1]] = Tileset.FLOOR;
        }
        return newHolder;
    }

    /* Point array has xpoint first, then ypoint */
    public int[] randPointOnRoom(int[] roomCoordinates, int seed) {
        int xl = roomCoordinates[0];
        int xr = roomCoordinates[1];
        int yb = roomCoordinates[2];
        int yt = roomCoordinates[3];
        Random rand = new Random(seed);
        int[] point = new int[2];
        int side = pseudoRand.uniform(rand, 4);
        if (side == 0) {
            point[0] = xl;
            point[1] = pseudoRand.uniform(rand, yb, yt);
        } else if (side == 1) {
            point[0] = pseudoRand.uniform(rand, xl, xr);
            point[1] = yt;
        } else if (side == 2) {
            point[0] = xr;
            point[1] = pseudoRand.uniform(rand, yb, yt);
        } else if (side == 3) {
            point[0] = pseudoRand.uniform(rand, xl, xr);
            point[1] = yb;
        }
        return point;
    }

    public RoomInfoHolder createRandRoom(int xlcoordinate, int xrcoordinate, int ytcoordinate,
                                  int ybcoordinate, TETile[][] input, int usedSpaces) {
        for (int y = ybcoordinate; y <= ytcoordinate; y++) {
            for (int i = xlcoordinate; i <= xrcoordinate; i++) {
                if (input[i][y] != null) {
                    RoomInfoHolder answer = new RoomInfoHolder(input, usedSpaces);
                    return answer;
                }
            }
        }
        Infrastructure holder = new Infrastructure();
        RoomInfoHolder answer  = holder.roomGenerator(xlcoordinate, xrcoordinate,
                ytcoordinate, ybcoordinate, input, usedSpaces);
        return answer;
    }

    /*
    public static void main(String args[]) {
        TERenderer example = new TERenderer();
        Infrastructure input = new Infrastructure();
        RandomWorldGenerator firstTry = new RandomWorldGenerator();
        example.initialize(50, 50);
        TETile[][] grid = new TETile[50][50];
        TETile[][] actualInput = firstTry.worldMaker(50, 50, 455857754);
        actualInput = input.fillNothing(actualInput, 50, 50);
        example.renderFrame(actualInput);
    }
    */

}
