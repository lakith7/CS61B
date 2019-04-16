package bearmaps.proj2c.server.handler.impl;

import bearmaps.proj2c.AugmentedStreetMapGraph;
import bearmaps.proj2c.server.handler.APIRouteHandler;
import spark.Request;
import spark.Response;
import bearmaps.proj2c.utils.Constants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import static bearmaps.proj2c.utils.Constants.*;

/**
 * Handles requests from the web browser for map images. These images
 * will be rastered into one large image to be displayed to the user.
 * @author rahul, Josh Hug, _________
 */
public class RasterAPIHandler extends APIRouteHandler<Map<String, Double>, Map<String, Object>> {

    /**
     * Each raster request to the server will have the following parameters
     * as keys in the params map accessible by,
     * i.e., params.get("ullat") inside RasterAPIHandler.processRequest(). <br>
     * ullat : upper left corner latitude, <br> ullon : upper left corner longitude, <br>
     * lrlat : lower right corner latitude,<br> lrlon : lower right corner longitude <br>
     * w : user viewport window width in pixels,<br> h : user viewport height in pixels.
     **/
    private static final String[] REQUIRED_RASTER_REQUEST_PARAMS = {"ullat", "ullon", "lrlat",
            "lrlon", "w", "h"};

    /**
     * The result of rastering must be a map containing all of the
     * fields listed in the comments for RasterAPIHandler.processRequest.
     **/
    private static final String[] REQUIRED_RASTER_RESULT_PARAMS = {"render_grid", "raster_ul_lon",
            "raster_ul_lat", "raster_lr_lon", "raster_lr_lat", "depth", "query_success"};


    @Override
    protected Map<String, Double> parseRequestParams(Request request) {
        return getRequestParams(request, REQUIRED_RASTER_REQUEST_PARAMS);
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param requestParams Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @param response : Not used by this function. You may ignore.
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image;
     *                    can also be interpreted as the length of the numbers in the image
     *                    string. <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    @Override
    public Map<String, Object> processRequest(Map<String, Double> requestParams, Response response) {
        /* Start by retrieving all the data given. */
        double lrlon = requestParams.get("lrlon");
        double lrlat = requestParams.get("lrlat");
        double ullon = requestParams.get("ullon");
        double ullat = requestParams.get("ullat");
        double width = requestParams.get("w");
        double height = requestParams.get("h");

        double actualLonDPP = (lrlon - ullon)/width;

        /* Change to false if the query is unsuccessful. */
        boolean query_success = true;

        /* If it is not possible to find images to cover the whole query box, just
        return the data that is available. */

        /* If the user provides a query box that is completely outside of the root long/lat or
         * the data doesn't make sense (ullon, ullat is to the right of lrlon, lrlat) then set
         * query_success to false. */



        /* Finds the appropriate level of depth. Breaks if depth goes above 7. */

        double estimatedRootLRLon = ROOT_LRLON;
        double estimatedRootULLon = ROOT_ULLON;
        double estimatedLonDPP = (estimatedRootLRLon - estimatedRootULLon)/256;
        int depth = 0;

        while (estimatedLonDPP > actualLonDPP) {
            estimatedRootLRLon = estimatedRootLRLon + (estimatedRootULLon - estimatedRootLRLon)/2;
            estimatedLonDPP = (estimatedRootLRLon - estimatedRootULLon)/256;
            depth += 1;
            if (depth == 7) {
                break;
            }
        }


        /* Find the position of the image files to return. Pos. 0,0, 0,1 , etc.
         * Position 0, 0 returns the image in the top left. 0 is the first column and
         * the first row. 1 is the second column and the second row. */

        double numberOfBoxesLong = Math.pow(2, depth);
        double totalLength = ROOT_ULLAT - ROOT_LRLAT;
        double totalHeight = ROOT_LRLON - ROOT_ULLON;
        double lengthPerSquare = totalLength/numberOfBoxesLong;
        double heightPerSquare = totalHeight/numberOfBoxesLong;

        ArrayList<Integer> xValues = new ArrayList<>();
        ArrayList<Integer> yValues = new ArrayList<>();

        double startULLat = ROOT_ULLAT;
        int startX = 0;

        /* Finding startX. This works. */
        while (true) {
            if ((startULLat >= ullat) && ((startULLat - lengthPerSquare) <= ullat)) {
                break;
            }
            startX += 1;
            startULLat -= lengthPerSquare;
        }

        double raster_ul_lat = startULLat;
        int endX = startX;
        double endULLat = startULLat;

        /* Finding endX. */
        while (true) {
            if ((endULLat >= lrlat) && ((endULLat - lengthPerSquare) <= lrlat)) {
                break;
            }
            endX += 1;
            endULLat -= lengthPerSquare;
        }
        endX += 1;

        double raster_lr_lat = endULLat - lengthPerSquare;

        double startULLon = ROOT_ULLON;
        int startY = 0;

        /* Finding startY. */
        while (true) {
            if ((startULLon <=  ullon) && ((startULLon + heightPerSquare) >= ullon)) {
                break;
            }
            startY += 1;
            startULLon += heightPerSquare;
        }

        double raster_ul_lon = startULLon;
        int endY = startY;
        double endULLon = startULLon;

        /* Finding endY. */
        while (true) {
            if ((endULLon <= lrlon) && ((endULLon + heightPerSquare) >= lrlon)) {
                break;
            }
            endY += 1;
            endULLon += heightPerSquare;
        }

        endY += 1;
        double raster_lr_lon = endULLon + heightPerSquare;

        /* Inputting into the 2d render_grid. i is used for the x variable,
        j for the y variable. Missing one row. Above is correct.
        Just add the exceptions and fix this part. */
        String[][] render_grid = new String[endY - startY][endX - startX];
        int i = 0;
        int j = 0;
        while (i < (endY - startY)) {
            while (j < (endX - startX)) {
                render_grid[i][j] = "d" + depth + "_x" + (startY + j) + "_y" + (startX + i) + ".png";
                j += 1;
            }
            i += 1;
            j = 0;
        }

        /* Inputting the information into the HashMap that is returned. */

        Map<String, Object> results = new HashMap<>();
        results.put("depth", depth);
        results.put("query_success", query_success);
        results.put("render_grid", render_grid);
        results.put("raster_ul_lon", raster_ul_lon);
        results.put("raster_lr_lon", raster_lr_lon);
        results.put("raster_ul_lat", raster_ul_lat);
        results.put("raster_lr_lat", raster_lr_lat);

        return results;
    }

    public double testDepth() {
        double actualLonDPP = 0.00008630532;
        double estimatedRootLRLon = ROOT_LRLON;
        double estimatedRootULLon = ROOT_ULLON;
        double estimatedLonDPP = (estimatedRootLRLon - estimatedRootULLon)/256;
        double depth = 0;

        while (estimatedLonDPP > actualLonDPP) {
            estimatedRootLRLon = estimatedRootLRLon + (estimatedRootULLon - estimatedRootLRLon)/2;
            estimatedLonDPP = (estimatedRootLRLon - estimatedRootULLon)/256;
            depth += 1;
        }

        return depth;
    }

    @Override
    protected Object buildJsonResponse(Map<String, Object> result) {
        boolean rasterSuccess = validateRasteredImgParams(result);

        if (rasterSuccess) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            writeImagesToOutputStream(result, os);
            String encodedImage = Base64.getEncoder().encodeToString(os.toByteArray());
            result.put("b64_encoded_image_data", encodedImage);
        }
        return super.buildJsonResponse(result);
    }

    private Map<String, Object> queryFail() {
        Map<String, Object> results = new HashMap<>();
        results.put("render_grid", null);
        results.put("raster_ul_lon", 0);
        results.put("raster_ul_lat", 0);
        results.put("raster_lr_lon", 0);
        results.put("raster_lr_lat", 0);
        results.put("depth", 0);
        results.put("query_success", false);
        return results;
    }

    /**
     * Validates that Rasterer has returned a result that can be rendered.
     * @param rip : Parameters provided by the rasterer
     */
    private boolean validateRasteredImgParams(Map<String, Object> rip) {
        for (String p : REQUIRED_RASTER_RESULT_PARAMS) {
            if (!rip.containsKey(p)) {
                System.out.println("Your rastering result is missing the " + p + " field.");
                return false;
            }
        }
        if (rip.containsKey("query_success")) {
            boolean success = (boolean) rip.get("query_success");
            if (!success) {
                System.out.println("query_success was reported as a failure");
                return false;
            }
        }
        return true;
    }

    /**
     * Writes the images corresponding to rasteredImgParams to the output stream.
     * In Spring 2016, students had to do this on their own, but in 2017,
     * we made this into provided code since it was just a bit too low level.
     */
    private void writeImagesToOutputStream(Map<String, Object> rasteredImageParams,
                                                  ByteArrayOutputStream os) {
        String[][] renderGrid = (String[][]) rasteredImageParams.get("render_grid");
        int numVertTiles = renderGrid.length;
        int numHorizTiles = renderGrid[0].length;

        BufferedImage img = new BufferedImage(numHorizTiles * Constants.TILE_SIZE,
                numVertTiles * Constants.TILE_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics graphic = img.getGraphics();
        int x = 0, y = 0;

        for (int r = 0; r < numVertTiles; r += 1) {
            for (int c = 0; c < numHorizTiles; c += 1) {
                graphic.drawImage(getImage(Constants.IMG_ROOT + renderGrid[r][c]), x, y, null);
                x += Constants.TILE_SIZE;
                if (x >= img.getWidth()) {
                    x = 0;
                    y += Constants.TILE_SIZE;
                }
            }
        }

        /* If there is a route, draw it. */
        double ullon = (double) rasteredImageParams.get("raster_ul_lon"); //tiles.get(0).ulp;
        double ullat = (double) rasteredImageParams.get("raster_ul_lat"); //tiles.get(0).ulp;
        double lrlon = (double) rasteredImageParams.get("raster_lr_lon"); //tiles.get(0).ulp;
        double lrlat = (double) rasteredImageParams.get("raster_lr_lat"); //tiles.get(0).ulp;

        final double wdpp = (lrlon - ullon) / img.getWidth();
        final double hdpp = (ullat - lrlat) / img.getHeight();
        AugmentedStreetMapGraph graph = SEMANTIC_STREET_GRAPH;
        List<Long> route = ROUTE_LIST;

        if (route != null && !route.isEmpty()) {
            Graphics2D g2d = (Graphics2D) graphic;
            g2d.setColor(Constants.ROUTE_STROKE_COLOR);
            g2d.setStroke(new BasicStroke(Constants.ROUTE_STROKE_WIDTH_PX,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            route.stream().reduce((v, w) -> {
                g2d.drawLine((int) ((graph.lon(v) - ullon) * (1 / wdpp)),
                        (int) ((ullat - graph.lat(v)) * (1 / hdpp)),
                        (int) ((graph.lon(w) - ullon) * (1 / wdpp)),
                        (int) ((ullat - graph.lat(w)) * (1 / hdpp)));
                return w;
            });
        }

        rasteredImageParams.put("raster_width", img.getWidth());
        rasteredImageParams.put("raster_height", img.getHeight());

        try {
            ImageIO.write(img, "png", os);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private BufferedImage getImage(String imgPath) {
        BufferedImage tileImg = null;
        if (tileImg == null) {
            try {
                File in = new File(imgPath);
                tileImg = ImageIO.read(in);
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
        return tileImg;
    }

    public static void main(String args[]) {
        double actualLonDPP = 0.00008630532;
        double estimatedRootLRLon = ROOT_LRLON;
        double estimatedRootULLon = ROOT_ULLON;
        double estimatedLonDPP = (estimatedRootLRLon - estimatedRootULLon)/256;
        int depth = 0;

        while (estimatedLonDPP > actualLonDPP) {
            estimatedRootLRLon = estimatedRootLRLon + (estimatedRootULLon - estimatedRootLRLon)/2;
            estimatedLonDPP = (estimatedRootLRLon - estimatedRootULLon)/256;
            depth += 1;
        }
        System.out.println(depth);
    }
}
