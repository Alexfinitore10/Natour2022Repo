package com.INGSW.NaTour.Extra;

import com.INGSW.NaTour.Model.MapPoint;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.ticofab.androidgpxparser.parser.GPXParser;
import io.ticofab.androidgpxparser.parser.domain.Gpx;
import io.ticofab.androidgpxparser.parser.domain.Route;
import io.ticofab.androidgpxparser.parser.domain.RoutePoint;
import io.ticofab.androidgpxparser.parser.domain.TrackPoint;
import io.ticofab.androidgpxparser.parser.domain.WayPoint;

public class GpxManager {

    private static Gpx gpx = null;
    private static GPXParser parser = new GPXParser();

    public static List<MapPoint> getTrack(InputStream in) {
        try {
            gpx = parser.parse(in); // consider using a background thread
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }

        List<MapPoint> points = new ArrayList<>();

        if (gpx == null) {
            // error parsing track
        } else {

            //Log.d("GpxParserRoute: ", parsedGpx.getRoutes().toString());
            //Log.d("GpxParserTrack: ", parsedGpx.getTracks().toString());
            //Log.d("GpxParserWayPointes: ", parsedGpx.getWayPoints().toString());

            //List<TrackPoint> trackPointsList = null;
            List<Route> routeList;
            List<TrackPoint> trackList;
            List<WayPoint> waypointList;


            if (gpx.getRoutes() != null && gpx.getRoutes().size() != 0) {
                routeList = gpx.getRoutes();
                System.out.println(routeList);
                List<RoutePoint> routePoints = routeList.get(0).getRoutePoints();
                for (RoutePoint r : routePoints) {
                    points.add(new MapPoint(r.getLongitude(), r.getLatitude()));
                }
            } else if (gpx.getTracks() != null && gpx.getTracks().size() != 0) {
                trackList = gpx.getTracks().get(0).getTrackSegments().get(0).getTrackPoints();
                for (TrackPoint t : trackList) {
                    points.add(new MapPoint(t.getLongitude(), t.getLatitude()));
                }
            } else if (gpx.getWayPoints() != null && gpx.getWayPoints().size() != 0) {
                waypointList = gpx.getWayPoints();
                for (WayPoint w : waypointList) {
                    points.add(new MapPoint(w.getLongitude(), w.getLatitude()));
                }
            }

        }
        return points;
    }
}
