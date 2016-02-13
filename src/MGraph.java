import java.util.ArrayList;
import java.util.HashMap;

public class MGraph {
	public HashMap<String, Place> places;
	private int numPlaces;
	private int numRoads;

	public static enum opType {
		time, distance, avgRating
	};

	private final static double earthRadius = 3961;

	/**
	 * Creates a graph of Places connected by Roads.
	 *
	 */
	public MGraph() {
		this.places = new HashMap<String, Place>();
		this.numPlaces = 0;
		this.numRoads = 0;
	}

	public boolean addPlace(String name, int rating, double x, double y) {
		if (name == null) {
			throw new IllegalArgumentException("A place must have a name.");
		}
		places.put(name, new Place(name, rating, x, y));
		this.numPlaces++;
		return true;
	}

	public boolean addRoad(Place name1, Place name2, double t, double d) {
		if (!places.containsKey(name1) && !places.containsKey(name2)) {
			return false;
		}
		places.get(name1).addRoad(name2);
		return true;
	}

	public ArrayList<Route> findRouteGivenConstraints(Place start, Place end, double min, double max,
			opType optimizationType) {
		if (optimizationType == opType.time) {
			return routeByTime(start, end, min, max);
		} else if (optimizationType == opType.distance) {
			return routeByDistance(start, end, min, max);
		} else {
			return routeByFun(start, end, min, max);
		}

	}

	private ArrayList<Route> routeByTime(Place start, Place end, double min, double max) {
		ArrayList<Route> toReturn = new ArrayList<Route>();
		// no staycations allowed
		if (max <= 0 || start.equals(end)) {
			throw new IllegalArgumentException();
		}
		generateRoutesByTime(start, start, end, 0, min, max, toReturn, new ArrayList<Road>());
		return toReturn;
	}
	
	private ArrayList<Route> routeByDistance(Place start, Place end, double min, double max) {
		ArrayList<Route> toReturn = new ArrayList<Route>();
		if (max <= 0 || start.equals(end)) {
			throw new IllegalArgumentException();
		}
		generateRoutesByDistance(start, start, end, 0, min, max, toReturn, new ArrayList<Road>());
		return toReturn;
	}

	private ArrayList<Route> routeByFun(Place start, Place end, double min, double max) {
		// TODO Auto-generated method stub.
		return null;
	}

	private void generateRoutesByTime(Place start, Place current, Place end, double currentCost, double min, double max, ArrayList<Route> toReturn,
			ArrayList<Road> path) {
		if (currentCost > max){
			return;
		}
		else if (current.equals(end) && currentCost >= min) {
			toReturn.add(new Route(start, path, end));
			return;
		}
		else {
			for (String r : current.neighbors.keySet()){
				Place temp = current.neighbors.get(r).dest;
				ArrayList<Road> newPath = new ArrayList<Road>();
				newPath.addAll(path);
				newPath.add(current.neighbors.get(r));
				currentCost += current.neighbors.get(r).timeCost;
				generateRoutesByTime(start, temp, end, currentCost, min, max, toReturn, newPath);		
			}
			
		}

	}

	private void generateRoutesByDistance(Place start, Place current, Place end, double currentCost, double min, double max, ArrayList<Route> toReturn,
			ArrayList<Road> path) {
		if (currentCost > max){
			return;
		}
		else if (current.equals(end) && currentCost >= min) {
			toReturn.add(new Route(start, path, end));
			return;
		}
		else {
			for (String r : current.neighbors.keySet()){
				Place temp = current.neighbors.get(r).dest;
				ArrayList<Road> newPath = new ArrayList<Road>();
				newPath.addAll(path);
				newPath.add(current.neighbors.get(r));
				currentCost += current.neighbors.get(r).distCost;
				generateRoutesByTime(start, temp, end, currentCost, min, max, toReturn, newPath);		
			}
			
		}

	}

	/**
	 * Represents a Place.
	 *
	 * @author roggeek. Created Feb 8, 2016.
	 */
	class Place {
		private String name;
		private HashMap<String, Road> neighbors;
		public int rating;
		public double x;
		public double y;

		// constructor
		public Place(String name, int rating, double x, double y) {
			this.name = name;
			neighbors = new HashMap<String, Road>();
			this.x = x;
			this.y = y;
			this.rating = rating;
		}

		public String toString() {
			return this.name;
		}

		public void addRoad(Place p) {
			double dist = this.findDist(p);
			double time = this.findTime(p);
			Road newRoad = new Road(p, time, dist, this.name + "_" + p.name);
			if (!this.neighbors.containsValue(newRoad)) {
				this.neighbors.put(p.toString(), newRoad);
				MGraph.this.numRoads++;
			}
		}

		public String getRoadName(String dest) {
			return this.neighbors.get(dest).name;
		}

		public String getAllRoadNames() {
			String r = "";
			for (String s : this.neighbors.keySet()) {
				r += this.neighbors.get(s).name + " ; ";
			}
			return r;
		}

		public Road getRoad(String dest) {
			return this.neighbors.get(dest);
		}

		public ArrayList<Road> getAllRoads() {
			ArrayList<Road> toReturn = new ArrayList<Road>();
			for (String r : this.neighbors.keySet()) {
				toReturn.add(this.neighbors.get(r));
			}
			return toReturn;
		}

		public double findDist(Place other) {
			// find difference in coordinates of this Place and other Place
			double diffX = this.x - other.x;
			double diffY = this.y - other.y;
			// calculate the distance in miles
			double a = Math.pow(Math.sin(diffX / 2), 2)
					+ Math.cos(other.x) * Math.cos(this.x) * Math.pow(Math.sin(diffY / 2), 2);
			double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
			double d = earthRadius * c;
			return d;
		}

		public double findTime(Place other) {
			// find difference in coordinates of this Place and other Place
			double diffX = this.x - other.x;
			double diffY = this.y - other.y;
			// calculate the distance in miles
			double a = Math.pow(Math.sin(diffX / 2), 2)
					+ Math.cos(this.x) * Math.cos(other.x) * Math.pow(Math.sin(diffY / 2), 2);
			double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
			double d = earthRadius * c;
			// introduce a random amount of travel time variation
			double r = Math.random();
			double randFactor = r + 1;
			// assume traveler is traveling ~ 60mph as the bird flies
			double time = d * randFactor / 60;
			return time;
		}

	}

	/**
	 * Represents an edge between two places.
	 *
	 * @author roggeek. Created Feb 8, 2016.
	 */
	class Road {
		private Place dest;
		public double timeCost;
		public double distCost;
		public String name;

		public Road(Place dest, double time, double dist, String name) {
			this.dest = dest;
			this.timeCost = time;
			this.distCost = dist;
			this.name = name;
		}
	}

	/**
	 * This class represents a Route, composed of Roads and Places, between a
	 * start and an end Place. Contains total time, total distance and average
	 * rating of Places on the Route.
	 *
	 * @author roggeek. Created Feb 11, 2016.
	 */
	public class Route {
		private Place start;
		private Place end;
		private ArrayList<Road> roads;
		private double totalTime;
		private double totalDist;
		private double avgRating;

		public Route(Place start, ArrayList<Road> roads, Place end) {
			this.start = start;
			this.roads = roads;
			this.end = end;
			this.totalTime = this.totalTime();
			this.totalDist = this.totalDist();
			this.avgRating = this.avgRating();
		}

		public double totalTime() {
			double total = 0;
			for (int i = 0; i < roads.size(); i++) {
				total += roads.get(i).timeCost;
			}
			return total;
		}

		public double totalDist() {
			double total = 0;
			for (int i = 0; i < roads.size(); i++) {
				total += roads.get(i).distCost;
			}
			return total;
		}

		public double avgRating() {
			double avg = start.rating;
			for (int i = 0; i < roads.size(); i++) {
				avg += roads.get(i).dest.rating;
			}
			return avg / roads.size();
		}
		
		public String toString(){
			String r = "";
			for (int i = 0; i < roads.size(); i++){
				r += roads.get(i).name + "_";
			}
			return r;
			
		}

	}

}
