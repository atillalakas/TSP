package cmp3004;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class GreedyAlgorithm {
    private HashMap<Integer, List<Coordinate>> coordinateHashMap;
    private List<Coordinate> path;
    private List<Coordinate> coordinates;
    public GreedyAlgorithm(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
        coordinateHashMap = new HashMap<>();
        path = new ArrayList<>();
        for (int i = 0; i < coordinates.size(); i++) {
            var coordinate = coordinates.get(i);
            coordinateHashMap.put(coordinate.getIndex(), new ArrayList<>() {
                {
                    add(coordinate);
                }
            });
        }
    }

    private List<Coordinate> joinData(Coordinate a, Coordinate b) {
        var firstSegment = coordinateHashMap.get(a.getIndex());
        var secondSegment = coordinateHashMap.get(b.getIndex());
        if(firstSegment.get(firstSegment.size() - 1) != a)
            Collections.reverse(firstSegment);
        if(secondSegment.get(0) != b)
            Collections.reverse(secondSegment);

        firstSegment.addAll(secondSegment);

        coordinateHashMap.remove(a.getIndex());
        coordinateHashMap.remove(b.getIndex());
        coordinateHashMap.put(firstSegment.get(0).getIndex(), firstSegment);
        coordinateHashMap.put(firstSegment.get(firstSegment.size() - 1).getIndex(), firstSegment);
        return firstSegment;
    }

    private List<CoordinatePair> getShortestDistanceCoordinates() {
        var data = Utility.combinationOfTwo(coordinates);
        data.sort((coordinatePairOne, coordinatePairTwo) -> (int) (Utility.getDistance(coordinatePairOne.a, coordinatePairOne.b) -
                            Utility.getDistance(coordinatePairTwo.a, coordinatePairTwo.b)));
        return data;
    }

    public GreedyAlgorithm runAlgorithm() {
        var shortestDistanceCoordinates = getShortestDistanceCoordinates();
        for(int i = 0; i < shortestDistanceCoordinates.size(); i++) {
            var data = shortestDistanceCoordinates.get(i);
            if(coordinateHashMap.containsKey(data.a.getIndex())) {
                if(coordinateHashMap.containsKey(data.b.getIndex())) {
                    if(coordinateHashMap.get(data.a.getIndex()) != coordinateHashMap.get(data.b.getIndex())) {
                        var joinedData = joinData(data.a, data.b);
                        if(joinedData.size() == coordinates.size()) {
                            path = joinedData;
                            return this;
                        }
                    }
                }
            }
        }
        return this;
    }

    public List<Coordinate> getPath() {
        return path;
    }
}
