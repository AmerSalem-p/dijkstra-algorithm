package Project3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Graph {

	int numOfVertex;
	int numOfEdges;
	Vertex[] vertex;
	PriorityQueue<Vertex> queue = new PriorityQueue<>();
	public void initilizeTable(int start) {
	
	
		for (int i = 0; i < numOfVertex; i++) {

			vertex[i].table = new tableEntry(null, false, Integer.MAX_VALUE);
		}
		vertex[start].table = new tableEntry(null, false, 0);
	    queue.add(vertex[start]);
	}

	public void CalculateDistance() {
        
		while (!queue.isEmpty()) {
			Vertex x = queue.poll();
		
			x.table.setVisited(true);
			
			for (int j = 0; j < x.adjacent.size(); j++) { // for each adjacent
				int index = findIndex(x.adjacent.get(j).getVertex());

				if (!vertex[index].table.isVisited()) {
					if (x.adjacent.get(j).getDistance() + x.table.getWeight() < vertex[index].table.getWeight()) {
						vertex[index].table.setWeight(x.adjacent.get(j).getDistance() + x.table.getWeight());
						vertex[index].table.setPath(x);
						queue.add(vertex[index]);

					}
				}
			}
		}

	}

	public void readFromFile(String path) {

		File file1 = new File(path);
		Scanner scan;
		try {
			scan = new Scanner(file1);

			String line = scan.nextLine();
			String str[] = line.split(" ");

			numOfVertex = Integer.parseInt(str[0]);
			numOfEdges = Integer.parseInt(str[1]);
			vertex = new Vertex[numOfVertex];

			int Tracker = 0;

			while (Tracker < numOfVertex) {

				line = scan.nextLine();
				str = line.split(" ");
				vertex[Tracker] = new Vertex(str[0], Integer.parseInt(str[1]), Integer.parseInt(str[2]));
				Tracker++;

			}

			for (int i = 0; i < numOfVertex; i++)
				vertex[i].adjacent = new LinkedList<Adjacent>();

			Tracker = 0;

			while (Tracker < numOfEdges) {

				line = scan.nextLine();
				str = line.split(" ");

				Vertex temp1 = findVertex(str[0]);
				Vertex temp2 = findVertex(str[1]);
				int index1 = findIndex(temp1);
				int index2 = findIndex(temp2);	
			
				vertex[index1].adjacent.add(new Adjacent(findVertex(str[1]), Integer.parseInt(str[2])));
				vertex[index2].adjacent.add(new Adjacent(findVertex(str[0]), Integer.parseInt(str[2])));
				Tracker++;
			}

			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void printPath(Vertex v) {

		if (v.table.getPath() != null) {
			printPath(v.table.getPath());
			System.out.print("-->");
		}

		System.out.println(v.getName());

	}

	public Vertex findVertex(String name) {

		for (int i = 0; i < numOfVertex; i++) {

			if (vertex[i].getName().compareTo(name) == 0) {
				return vertex[i];
			}

		}
		return null;

	}

	public int index(String name) {

		for (int i = 0; i < numOfVertex; i++) {

			if (vertex[i].getName().compareTo(name) == 0) {
				return i;
			}

		}
		return -1;

	}

	private int findIndex(Vertex v) {
		int index = -1;
		for (int i = 0; i < numOfVertex; i++) {

			if (v.getName().compareTo(vertex[i].getName()) == 0) {
				index = i;
				break;
			}

		}
		return index;
	}

}
