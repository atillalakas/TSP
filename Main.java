package cmp3004;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        File file = new File("ca4663.tsp");

        if(!(file.exists() && file.canRead())) {
            System.out.println("File couldn't open.");
            return;
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            List<Coordinate> cities = new ArrayList<>();

            reader.lines().skip(7).forEach(line -> {
                var data = line.split(" ");
                if(data.length >= 3) {
                    var index = Integer.parseInt(data[0]);
                    var xCoord = Double.parseDouble(data[1]);
                    var yCoord = Double.parseDouble(data[2]);
                    cities.add(new Coordinate(xCoord, yCoord, index));
                }
            });
            var nearestNeighbor = new NearestNeighborAlgorithm(cities);
            var path = nearestNeighbor.runAlgorithm().getPath();
            System.out.println("Nearest Neighbor Algorithm Results:");
            System.out.println("Total Tour Length : " + Utility.getTourLength(path));
            System.out.println("City Number : " + path.size());
            var greedyAlg = new GreedyAlgorithm(cities);
            var path2 = greedyAlg.runAlgorithm().getPath();
            System.out.println("Greedy Algorithm Results:");
            System.out.println("Total Tour Length : " + Utility.getTourLength(path2));
            System.out.println("City Number : " + path2.size());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
