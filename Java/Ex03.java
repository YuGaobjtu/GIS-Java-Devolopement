package kth.ag2411.mapalgebra;


public class Ex03 {
	public static void main(String[] args) {
		String operation = args[0];
		if (operation.equals("localSum")) {
			Layer inLayer1 = new Layer("", args[1]);
			Layer inLayer2 = new Layer("", args[2]);
			Layer outLayer = inLayer1.localSum(inLayer2, "");
			outLayer.save("./data/LocalSum.txt");;
		}
		else if (operation.equals("focalVariety")) {
			Layer valueLayer = new Layer("", args[1]);
			int r=Integer.parseInt(args[2]);
			boolean IsSquare = Boolean.getBoolean(args[3]);
			Layer outLayer = valueLayer.focalVariety(r,IsSquare, "");
			outLayer.save("./data/focalVariety.txt");;
		}
		else if (operation.equals("focalSlope")) {
			Layer valueLayer = new Layer("", args[1]);
			int cellsize=Integer.parseInt(args[2]);
			Layer outLayer = valueLayer.focalSlope(cellsize, "");
			outLayer.save("./data/focalSlope.txt");;
		}
		else if (operation.equals("focalAspect")) {
			Layer valueLayer = new Layer("", args[1]);
			Layer outLayer = valueLayer.focalAspect( "");
			outLayer.save("./data/focalAspect.txt");;
		}
		else if (operation.equals("zonalMinimum")) {
			Layer valueLayer = new Layer("", args[1]);
			Layer zoneLayer = new Layer("", args[2]);
			Layer outLayer = valueLayer.zonalMinimum(zoneLayer, "");
			outLayer.save("./data/zonalMinimum.txt");;
		}
		else {
			System.out.println(operation + " is not a valid operation.");
		}
	}
}
