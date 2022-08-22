package cmp3004;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class NearestNeighborAlgorithm {
    private Set<Coordinate> coordinatesSet;
    private ArrayList<Coordinate> path;
    public NearestNeighborAlgorithm(List<Coordinate> coordinates) throws Exception {
        if(coordinates.isEmpty()) {
            throw new Exception("Coordinates can not be empty");
        }
        coordinatesSet = new HashSet<>(coordinates);
        coordinatesSet.remove(coordinates.get(0));
        path = new ArrayList<>() {
            {
                add(coordinates.get(0));
            }
        };
    }

    private Coordinate getNearestNeighbor(Coordinate coordinate) {
        AtomicReference<Coordinate> nearestNeighbor = new AtomicReference<>();
        AtomicReference<Double> coordinateMinDistance = new AtomicReference<>(Double.MAX_VALUE);
        coordinatesSet.forEach(coordinateItem -> {
            double distance = Utility.getDistance(coordinate, coordinateItem);
            if(distance < coordinateMinDistance.get()) {
                nearestNeighbor.set(coordinateItem);
                coordinateMinDistance.set(distance);
            }
        });
        return nearestNeighbor.get();
    }

    public NearestNeighborAlgorithm runAlgorithm() {
        var coordinate = path.get(0);
        while(!coordinatesSet.isEmpty()) {
            coordinate = getNearestNeighbor(coordinate);
            coordinatesSet.remove(coordinate);
            path.add(coordinate);
        }
        return this;
    }

    public ArrayList<Coordinate> getPath() {
        return path;
    }
}
