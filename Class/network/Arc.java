package kth.ag2411.project.network;

public class Arc {
    public String name;
    public Node head;
    public Node tail;
    public double weight;
    public int inPath = -1; 

    public Arc(String name, Node head, Node tail, double weight){
        this.name = name;
        this.head = head;
        this.tail = tail;
        this.weight = weight;
    }

    public Boolean hasPath(){
        if (inPath == -1){
            return false;
        } else {
            return true;
        }
    }
}
