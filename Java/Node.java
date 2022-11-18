package kth.ag2411.mapalgebra;
import java.util.LinkedList;

public class Node {
	
	//Defining Attributes: 
	public String name; //The name attribute is a String representing the name of this Node.
	public double value; //The value attribute is a double representing the weight of this Node.
	public LinkedList<Arc> outArcs; //The outArcs attribute is a LinkedList representing a set of all Arcs that emanate from this Node.
	public Node prev;
	
	// Defining methods: 
	public Node (String name) {
		this.name = name;
		value = Double.POSITIVE_INFINITY;
		outArcs = new LinkedList<Arc>();
		prev = null;
	}

}
