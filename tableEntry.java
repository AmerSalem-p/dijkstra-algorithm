package Project3;

public class tableEntry {
	
		private boolean isVisited;
		private int weight;
		private Vertex path;
		
		
		public tableEntry() {
			this.isVisited = false;
			this.weight = 0;
			this.path = null;
		}
		
		public tableEntry(Vertex path , boolean isVisited , int weight) {
			this.isVisited = isVisited;
			this.path = path;
			this.weight=weight;
		}

		public boolean isVisited() {
			return isVisited;
		}

		public void setVisited(boolean isVisited) {
			this.isVisited = isVisited;
		}

		public int getWeight() {
			return weight;
		}

		public void setWeight(int weight) {
			this.weight = weight;
		}

		public Vertex getPath() {
			return path;
		}

		public void setPath(Vertex path) {
			this.path = path;
		}

}