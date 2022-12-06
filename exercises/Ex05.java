package Exercises;
import ag2411.network.*;

public class Ex05 {

    //run with:
	//java Exercises/Ex04.java data/test/smallnetwork.txt data/output/networkOutput.txt 1
    //or
    //java Exercises/Ex04.java data/test/smallnetwork.txt data/output/networkOutput.txt 1 4

    public static void main(String[] args){

        Network testNetwork = new Network("test",args[0]);

        System.out.println("\nnumber of inputs: "+args.length);

        if (args.length < 3){
            System.out.println("Too few arguments");
        } else if (args.length == 3){
            Node startNode = testNetwork.nodeMap.get(args[2]);
            testNetwork.djikstra(startNode);
        } else if (args.length == 4){
            Node startNode = testNetwork.nodeMap.get(args[2]);
            Node endNode = testNetwork.nodeMap.get(args[3]);

            testNetwork.djikstra(startNode, endNode);
        }
		testNetwork.printNodes();
		System.out.println();
		testNetwork.printArcs();
		testNetwork.save(args[1]);

    }
}
