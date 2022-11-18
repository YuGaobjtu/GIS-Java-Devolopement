package kth.ag2411.mapalgebra;

public class Arc {
    public String name;
    Node head;
    Node tail;
    double weight;

    public Arc(String name, Node head, Node tail, double weight){
        this.name = name;
        this.head = head;
        this.tail = tail;
        this.weight = weight;
    }
}
