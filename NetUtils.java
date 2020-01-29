package net;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NetUtils {

    public static List<Point> deserialisePoints(String data) {
        return Arrays.stream(data.split(";"))
                .map(NetUtils::deserialisePoint)
                .collect(Collectors.toList());
    }

    public static String serialisePoints(List<Point> locations) {
        return String.join(";",
                locations
                        .stream()
                        .map(NetUtils::serialisePoint)
                        .collect(Collectors.toList()));
    }

    public static String serialisePoint(Point location) {
        return (location == null) ? "null" : location.x + "," + location.y;
    }

    public static Point deserialisePoint(String data) {
        if (data.equals("null")) {
            return null;
        }
        String[] x = data.split(",");
        return new Point(Integer.parseInt(x[0]), Integer.parseInt(x[1]));
    }
}
