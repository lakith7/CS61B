package byow.TileEngine;

public class Infrastructure {

    /* Contains the methods that create certain shapes that are called
     * on to make the world. */

    /* xl is the left x value, xr is the right x value,
    yt is the top y value, and yb is the bottom y value */
    /* Must be a rectangular shape */
    public RoomInfoHolder roomGenerator(int xlcoordinate, int xrcoordinate, int ytcoordinate,
                                           int ybcoordinate, TETile[][] input, int usedSpaces) {
        if (input == null) {
            throw new NullPointerException();
        }

        if (xlcoordinate > xrcoordinate || ytcoordinate < ybcoordinate) {
            System.out.println("Coordinate mismatch");
            return null;
        }

        /* Create the walls. */

        for (int i = xlcoordinate; i <= xrcoordinate; i++) {
            if (input[i][ytcoordinate] == null) {
                input[i][ytcoordinate] = Tileset.WALL;
            }
            if (input[i][ybcoordinate] == null) {
                input[i][ybcoordinate] = Tileset.WALL;
            }
        }

        for (int y = ybcoordinate; y <= ytcoordinate; y++) {
            if (input[xlcoordinate][y] == null) {
                input[xlcoordinate][y] = Tileset.WALL;
            }
            if (input[xrcoordinate][y] == null) {
                input[xrcoordinate][y] = Tileset.WALL;
            }
        }

        /* Create the floor of the room. */

        for (int y = ybcoordinate + 1; y < ytcoordinate; y++) {
            for (int i = xlcoordinate + 1; i < xrcoordinate; i++) {
                if (input[i][y] == null || input[i][y] == Tileset.WALL) {
                    input[i][y] = Tileset.FLOOR;
                }
            }
        }

        usedSpaces += ((xrcoordinate - xlcoordinate + 1) * (ytcoordinate - ybcoordinate + 1));

        RoomInfoHolder answer = new RoomInfoHolder(input, usedSpaces);

        return answer;
    }

    /* fills all empty spaces in the given grid with the Nothing tile. */
    public TETile[][] fillNothing(TETile[][] input, int horizSize, int vertSize) {
        for (int y = 0; y < vertSize; y++) {
            for (int i = 0; i < horizSize; i++) {
                if (input[i][y] == null) {
                    input[i][y] = Tileset.NOTHING;
                }
            }
        }
        return input;
    }

    public RoomInfoHolder cornerGenerator(int cornerX, int cornerY, int corner, int usedSpaces, TETile[][] input) {
        usedSpaces += 4;
        input[cornerX][cornerY] = Tileset.FLOOR;
        if (corner == 1) {
            input[cornerX + 1][cornerY + 1] = Tileset.WALL;
            input[cornerX + 1][cornerY] = Tileset.WALL;
            input[cornerX][cornerY + 1] = Tileset.WALL;
        } else if (corner == 2) {
            input[cornerX - 1][cornerY + 1] = Tileset.WALL;
            input[cornerX][cornerY + 1] = Tileset.WALL;
            input[cornerX - 1][cornerY] = Tileset.WALL;
        }
        RoomInfoHolder answer = new RoomInfoHolder(input, usedSpaces);
        return answer;
    }

    public RoomInfoHolder createHallway(int startXPoint, int startYPoint, int endXPoint, int endYPoint, TETile[][] output, int usedSpaces) {
        RoomInfoHolder solution = new RoomInfoHolder(output, usedSpaces);
        Infrastructure instigator = new Infrastructure();
        if (startYPoint > endYPoint) {
            /* Situation 1 */
            if (startXPoint < endXPoint) {
                solution = instigator.roomGenerator(startXPoint, endXPoint, startYPoint + 1,
                        startYPoint - 1, output, usedSpaces);
                solution = instigator.roomGenerator(endXPoint - 1, endXPoint + 1, startYPoint,
                        endYPoint, output, usedSpaces);
                solution = instigator.cornerGenerator(endXPoint, startYPoint,1 , usedSpaces, output);
            /* Situation 2 */
            } else if (endXPoint < startXPoint) {
                solution = instigator.roomGenerator(endXPoint - 1, endXPoint + 1, startYPoint,
                        endYPoint, output, usedSpaces);
                solution = instigator.roomGenerator(endXPoint, startXPoint, startYPoint + 1,
                        startYPoint - 1, output, usedSpaces);
                solution = instigator.cornerGenerator(endXPoint, startYPoint,2 , usedSpaces, output);
            } else if (endXPoint == startXPoint) {
                if (startYPoint > endYPoint) {
                    solution = instigator.roomGenerator(endXPoint - 1, endXPoint + 1, startYPoint ,
                            endYPoint, output, usedSpaces);
                } else if (startYPoint < endYPoint) {
                    solution = instigator.roomGenerator(endXPoint - 1, endXPoint + 1, endYPoint ,
                            startYPoint, output, usedSpaces);
                }
            }
        } else if (endYPoint > startYPoint) {
            /* Situation 3 */
            if (endXPoint < startXPoint) {
                solution = instigator.roomGenerator(startXPoint - 1, startXPoint + 1, endYPoint,
                        startYPoint, output, usedSpaces);
                solution = instigator.roomGenerator(endXPoint, startXPoint, endYPoint + 1,
                        endYPoint - 1, output, usedSpaces);
                solution = instigator.cornerGenerator(startXPoint, endYPoint,1 , usedSpaces, output);
            /* Situation 4 */
            } else if (endXPoint > startXPoint) {
                solution = instigator.roomGenerator(startXPoint - 1, startXPoint + 1, endYPoint,
                        startYPoint, output, usedSpaces);
                solution = instigator.roomGenerator(startXPoint, endXPoint, endYPoint + 1,
                        endYPoint - 1, output, usedSpaces);
                solution = instigator.cornerGenerator(startXPoint, endYPoint,2 , usedSpaces, output);
            }
        } else if (endYPoint == startYPoint) {
            if (startXPoint > endXPoint) {
                solution = instigator.roomGenerator(endXPoint, startXPoint, startYPoint + 1 ,
                        startYPoint - 1, output, usedSpaces);
            } else if (startXPoint < endXPoint) {
                solution = instigator.roomGenerator(startXPoint, endXPoint, endYPoint + 1 ,
                        endYPoint - 1, output, usedSpaces);
            }
        }
        return solution;
    }

    /* vertEnd must be greater than vertStart and horizEnd must be greater than horizStart. */
    public TETile[][] hallwayTurn(TETile[][] input, int horizStart, int horizEnd,
                                  int vertStart, int vertEnd, int vertCorner) {
        /* For a hallway turn with the shape F without the middle line. */
        if (vertCorner == vertEnd) {
            for (int i = horizStart; i <= horizEnd; i++) {
                input[i][vertEnd] = Tileset.FLOOR;
                input[i][vertEnd + 1] = Tileset.WALL;
                input[i][vertEnd + 1] = Tileset.WALL;
            }
            for (int y = vertStart; y <= vertEnd; y++) {
                input[horizStart][vertStart] = Tileset.FLOOR;
                input[horizStart + 1][vertStart] = Tileset.WALL;
                input[horizStart - 1][vertStart] = Tileset.WALL;
            }
        /* For a hallway turn with the shape _| */
        } else if (vertCorner == vertStart) {
            for (int i = horizStart; i <= horizEnd; i++) {
                input[i][vertStart] = Tileset.FLOOR;
                input[i][vertStart + 1] = Tileset.WALL;
                input[i][vertStart + 1] = Tileset.WALL;
            }
            for (int y = vertStart; y <= vertEnd; y++) {
                input[horizEnd][vertStart] = Tileset.FLOOR;
                input[horizEnd + 1][vertStart] = Tileset.WALL;
                input[horizEnd - 1][vertStart] = Tileset.WALL;
            }
        }
        return input;
    }

    public static void main(String args[]) {
        TERenderer example = new TERenderer();
        Infrastructure input = new Infrastructure();
        example.initialize(20, 20);
        TETile[][] grid = new TETile[20][20];
        RoomInfoHolder actualInput = input.createHallway(10, 5, 5, 8, grid, 0);
        TETile[][] answer;
        answer = input.fillNothing(actualInput.getGrid(), 20, 20);
        example.renderFrame(answer);
    }
}
