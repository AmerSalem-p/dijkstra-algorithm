package Project3;

public class Adjacent {

	private Vertex vertex;
	private int distance;
	
	public Adjacent() {
		
	}
	
	public Adjacent(Vertex vertex , int distance) {
		this.vertex = vertex;
		this.distance = distance;
	}


	public Vertex getVertex() {
		return vertex;
	}


	public void setVertex(Vertex vertex) {
		this.vertex = vertex;
	}


	public int getDistance() {
		return distance;
	}


	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	
}
