
/**
 * TODO Put here a description of what this class does.
 *
 * @author roggeek. Created Feb 11, 2016.
 */
public class Testing {

	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		MGraph map = new MGraph();
		map.addPlace("Cincinnati", 5, 39.1000, 84.5167);
		map.addPlace("SanFrancisco", 5, 37.7833, 122.4167);
		map.addPlace("StLouis", 5, 38.6272, 90.1978);
		//System.out.println(map.places.get("Cincinnati"));
		map.places.get("Cincinnati").addRoad(map.places.get("SanFrancisco"));
		map.places.get("SanFrancisco").addRoad(map.places.get("Cincinnati"));
		map.places.get("SanFrancisco").addRoad(map.places.get("StLouis"));
		map.places.get("StLouis").addRoad(map.places.get("Cincinnati"));
		map.places.get("Cincinnati").addRoad(map.places.get("StLouis"));
		//System.out.println(map.places.get("Cincinnati").getRoadName("SanFrancisco"));
		String roadName = map.places.get("SanFrancisco").getRoadName("Cincinnati");
		//System.out.println(map.places.get("Cincinnati").getAllRoadNames());
		System.out.println(map.places.get("SanFrancisco").findDist(map.places.get("Cincinnati")));
		System.out.println(map.findRouteGivenConstraints(map.places.get("SanFrancisco"), map.places.get("Cincinnati"),0,500, MGraph.opType.distance));
	}
}
