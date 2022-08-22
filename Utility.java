package cmp3004;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Utility {
    public static double getDistance(Coordinate a, Coordinate b) {
        return Math.hypot(a.getX() - b.getX(), a.getY() - b.getY());
    }
    public static double getTourLength(List<Coordinate> coordinates) {
        double total = .0;
        for(int i = 0; i < coordinates.size() - 1; ++i)
            total += getDistance(coordinates.get(i), coordinates.get(i + 1));

        return total;
    }
    private static boolean reverseSegmentIfImprovement(List<Coordinate> coordinates, int i, int j) {

        var A = coordinates.get((i - 1) < 0 ? (i - 1 + coordinates.size()) : (i - 1));
        var B = coordinates.get(i);
        var C = coordinates.get((j - 1) < 0 ? (j - 1 + coordinates.size()) : (j - 1));
        var D = coordinates.get(j % coordinates.size());
        if(getDistance(A, B) + getDistance(C, D) > getDistance(A, C) + getDistance(B, D)) {
            var tempCoordinates = new ArrayList<Coordinate>();
            for(int k = i; k < j - i;++k) {
                tempCoordinates.add(coordinates.get(k));
            }
            for(int k = i, n = tempCoordinates.size() - 1; k < j - i; ++k, --n) {
                coordinates.set(k, tempCoordinates.get(n));
            }
            return true;
        }
        return false;
    }
    private static ArrayList<NumberPair> subsegments(int N) {
        var pairs = new ArrayList<NumberPair>();
        for(int length = N - 1; length >= 2; --length) {
            for(int i = N - length; i >= 0; --i) {
                pairs.add(new NumberPair(i, i+length));
            }
        }
        return pairs;
    }
    public static List<Coordinate> improveTour(List<Coordinate> coordinates) {
        var data = subsegments(coordinates.size());
        while(true) {
            HashSet<Boolean> improvements = new HashSet<>();
            for(var item : data) {
                improvements.add(reverseSegmentIfImprovement(coordinates,item.a, item.b));
            }
            if(!improvements.contains(true)) {
                return coordinates;
            }
            System.gc();
        }
    }
    public static List<CoordinatePair> combinationOfTwo(List<Coordinate> coordinates) {
        var pairs = new ArrayList<CoordinatePair>();
        for (int i = 0; i < coordinates.size(); ++i)
            for(int k = i; k < coordinates.size(); ++k)
                if(k != i)
                    pairs.add(new CoordinatePair(coordinates.get(i), coordinates.get(k)));

        return pairs;
    }
}
