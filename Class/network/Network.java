//package kth.ag2411.network;
package kth.ag2411.project.network;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;
import java.util.PriorityQueue;


public class Network {
    String name; 
    public HashMap<String,Node> nodeMap;
    public HashMap<String,Node> usedArcs;

    // Constructor
    public Network(String name, String inputFileName) {
        // Initialize theattributes
        this.name= name;
        this.nodeMap= new HashMap<String, Node>();
        
        // You MAY need these local variables to store values or objects 
        // temporarily while constructing a new Network object
        String line,arcID,tailName,headName;
        Node tail,head;
        double weight;
        Arc forwardArc, backwardArc;
        try{
            // Get access to the contents of an ASCIIfile
            File file = new File(inputFileName);
            FileReader fReader = new FileReader(file);
            BufferedReader bReader = new BufferedReader(fReader);
            
            // Read the first line, and do nothing to skip it
            line = bReader.readLine();
            
            // Read the second line, which represents the first 
            // (undirected) arc stored in the file
            line = bReader.readLine();
            // Store each element of the network in forward star.

            // Do the following.
            // Check if nodeMap contains a Node whose name is tailName or headName.
            // If not, create it, assign it to tail or head, and add it to nodeMap.
            // Otherwise, retrieve it from nodeMap.
            // Then, create two Arcs:
            // one from tail to head, to be added to outArc of tail
            // one from head to tail, to be added to outArc of head.
            // Read the next line
            while(line != null) {
                // Split each line into an array of 4 Strings
                //using ,as separator.
                String[] tokens = line.split(",");
                arcID = tokens[0];
                tailName = tokens[1];
                headName = tokens[2];
                weight = Double.parseDouble(tokens[3]);

                //creating or retrieving head and tail nodes. 
                if (nodeMap.containsKey(tailName)) {
                    tail = nodeMap.get(tailName);
                    if (nodeMap.containsKey(headName)) {
                        head = nodeMap.get(headName);
                    } else {
                        head = new Node(headName);
                        nodeMap.put(headName,head);
                    }
                } else if(nodeMap.containsKey(headName)){
                    head = nodeMap.get(headName);
                    tail = new Node(tailName);
                    nodeMap.put(tailName,tail);

                } else {
                    head = new Node(headName);
                    nodeMap.put(headName,head);

                    tail = new Node(tailName);
                    nodeMap.put(tailName,tail);
                }

                forwardArc = new Arc(arcID+"_forward",head,tail,weight);
                backwardArc = new Arc(arcID+"_backward",tail,head,weight);

                head.outArcs.add(backwardArc);
                tail.outArcs.add(forwardArc);
                line = bReader.readLine();
            }
            bReader.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
// Other methods
    public void save(String outputFileName) {
        try {
            File writeFile = new File(outputFileName);
            if (writeFile.createNewFile()) {
              System.out.println("File created: " + writeFile.getName());
            } else {
              System.out.println("File already exists. Owerwriting existing file");
            }
            FileWriter writer = new FileWriter(outputFileName);
            writer.write("TLID,NAME,WEIGHT\n");

            Node node;
            for (String nodeName: nodeMap.keySet()) {
                node = nodeMap.get(nodeName);
                for (Arc arc: node.outArcs) {
                    String tlid;
                    if(arc.name.substring(arc.name.length()-7,arc.name.length()).equals("forward")){ //appendix is "_forward"
                        tlid = arc.name.substring(0,arc.name.length()-8);
                    } else { //appendix is "_backward"
                        tlid = arc.name.substring(0,arc.name.length()-9);
                    }
                    writer.write(tlid+","+arc.name+","+arc.weight+"\n");
                }
            }
            writer.close();
        } catch(IOException e) {
                e.printStackTrace();
            }
        // Do something
    }
    
    public void printNodes(){
        System.out.println("\tNODE NAME\tVALUE");
        Node node;
        for(String nodeName: nodeMap.keySet()) {
            // loop thru nodeMap 
            node = nodeMap.get(nodeName);
            System.out.print("\t"+ node.name);
            // \t represents tab space
            System.out.print("\t\t"+ node.value);
            System.out.println();
        }
    }
    
    public void printArcs(){
        System.out.println("\tARC NAME\tTAIL NAME\tHEAD NAME\tWEIGHT");
        Node node;
        for (String nodeName: nodeMap.keySet()) {
            node = nodeMap.get(nodeName);
            for (Arc arc: node.outArcs) {
                System.out.println("\t"+arc.name+"\t"+arc.tail.name+"\t\t"+arc.head.name+"\t\t"+arc.weight);
            }
        }
    }
    
    public void djikstra(Node fromNode){

        // Set the value(representing shortest path distance) of origin to 0
        fromNode.value= 0;
        // Create a set of nodes, called tempNodes,whose shortest path distances 
        // are not permanently determined. Initially, this set contains all nodes.
        
        //Faster implementation. 
        PriorityQueue<Node> tempNodes = new PriorityQueue<Node>(new NodeComparator());
        for(String nodeName: nodeMap.keySet()) {
            tempNodes.add(nodeMap.get(nodeName));
        }
        // Perform Dijkstra
        while(!tempNodes.isEmpty()) { 
            // repeat until all nodes become permanent

            // Find a node with minimum value in tempNodes
            // Remove the minimum-value node from tempNodes
            Node smallestNode = tempNodes.poll();
            
            // Update the value of each node that is adjacent to the minimum-weight node
            for (Arc outArc : smallestNode.outArcs){
                Double testValue = smallestNode.value + outArc.weight;
                if (outArc.head.value > testValue){
                    outArc.head.value = testValue;
                    outArc.head.prevNode = smallestNode;
                    outArc.head.prevArc = outArc;
                    //Update priorityQueue
                    tempNodes.remove(outArc.head);
                    tempNodes.add(outArc.head);
                }
                if (outArc.head.prevNode != null){
                    outArc.weight = 1;
                } else {
                    outArc.weight = 0;
                }
            }
        }

        // Assign1 to all arcs in the shortest path treeand0 to all otherarcs.
    }

    public void djikstra(Node fromNode, Node toNode){
        System.out.println("Djikstra with two inputs");
        djikstra(fromNode);
        clearArcWeights();
        trace(toNode);   
    }

    public List<Node> getPathAsNodes(Node fromNode, Node toNode){
        djikstra(fromNode);
        List<Node> path = new ArrayList<Node>();
        Node currentNode = toNode;
        while(currentNode != null){
            path.add(currentNode);
            currentNode = currentNode.prevNode;
        }
        return path;
    }

    public List<Arc> getPathAsArcs(Node fromNode, Node toNode){
        djikstra(fromNode);

        List<Arc> path = new ArrayList<Arc>();

        Node currentNode = toNode;
        while(currentNode != null){
            path.add(currentNode.prevArc);
            currentNode = currentNode.prevNode;
        }

        return path;
    }

//Removes all weights from all nodes in the network. 
    private void clearArcWeights(){
        Node tempNode;
        for (String nodeName : nodeMap.keySet()){
            tempNode = nodeMap.get(nodeName);
            for (Arc outArc : tempNode.outArcs){
                outArc.weight = 0;
            }
        }
    }

    private void trace(Node n) {
        Arc a = n.prevArc;
        while(a != null) {
            a.weight = 1;
            a = a.tail.prevArc;
        }
    }

}

class NodeComparator implements Comparator<Node>{

    @Override
    public int compare(Node n1, Node n2){
        if(n1.value < n2.value){
            return -1;
        } else if (n1.value < n2.value){
            return 1;
        } else {
            return 0;
        }
    }
}
