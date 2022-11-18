package kth.ag2411.mapalgebra;
import java.io.BufferedReader; 
import java.io.File; 
import java.io.FileReader; 
import java.io.FileWriter; 
import java.awt.image.*;
import java.util.*;

public class Layer { 
    // Attributes (This is complete) 
	public String name; // name of this layer 
	public int nRows; // number of rows 
	public int nCols; // number of columns 
	public double[] origin = new double[2]; // x,y-coordinates of lower-left corner 
	public double resolution; // cell size 
	public double[][] values; // data. Alternatively, public double[][] values; 
	public double nullValue; // value designated as "No data" 

	
	public Layer(String layerName, String fileName) { 
	
		try {
			
			File rFile = new File(fileName); 
			
			FileReader fReader = new FileReader(rFile); 
			
			BufferedReader bReader = new BufferedReader(fReader); 
			
			String text = bReader.readLine(); 
			String text1=text.replaceAll("ncols","");
			String text2=text1.replaceAll("\\s+", "");
			
			nCols=Integer.parseInt(text2);
			
			text = bReader.readLine(); 
			text1=text.replaceAll("nrows","");
			text2=text1.replaceAll("\\s+", "");
			nRows=Integer.parseInt(text2);
			
			text = bReader.readLine(); 
			text1=text.replaceAll("xllcorner","");
			text2=text1.replaceAll("\\s+", "");
			origin[0]=Double.parseDouble(text2);
			
			text = bReader.readLine(); 
			text1=text.replaceAll("yllcorner","");
			text2=text1.replaceAll("\\s+", "");
			origin[1]=Double.parseDouble(text2);
			
			text = bReader.readLine(); 
			text1=text.replaceAll("cellsize","");
			text2=text1.replaceAll("\\s+", "");
			resolution=Double.parseDouble(text2);
			
			text = bReader.readLine(); 
			text1=text.replaceAll("NODATA_value","");
			text2=text1.replaceAll("\\s+", "");
			nullValue=Double.parseDouble(text2);
			
			values= new double [nRows][nCols];
			String temp1 = null;
			String[] temp2=null;
			int i;
			int j;
			for(i =0; i< nRows;i++) {
				temp1= bReader.readLine();
				temp2 = temp1.split(" ");
				for(j=0; j<nCols; j++) {
					double temp=Double.parseDouble(temp2[j]);
					values[i][j]=temp;
				}
			}
			bReader.close();
			
		} catch (Exception e) {  
		e.printStackTrace(); 
		} 
	} 
	 
	public Layer(String outLayerName, int nRows, int nCols, double[] origin,
					double resolution, double nullValue) {
			this.name = outLayerName; 
			this.nRows = nRows; 
			this.nCols = nCols; 
			this.origin = origin;
			this.resolution = resolution;
			this.nullValue = nullValue;
			values = new double[nRows][nCols];
			}
	
	public void print(){ 
	   

		System.out.println("ncols         "+nCols); 
		System.out.println("nrows         "+nRows); 
		System.out.println("xllcorner     "+origin[0]); 
		System.out.println("yllcorner     "+origin[1]); 
		System.out.println("cellsize      "+resolution); 
	  	System.out.println("NODATA_value  " + nullValue); 
	   
	  	for (int i = 0; i < nRows; i++) { 
	  		for (int j = 0; j < nCols; j++) { 
	  			System.out.print(values[i][j]+" "); 
	  		} 
	  		System.out.println(); 
	  	}  
	} 
	 
	public void save(String outputFileName) { 
		 try {
		 File file = new File(outputFileName);  
		 FileWriter fWriter = new FileWriter(file); 
		 fWriter.write("ncols         "+nCols+"\r\n"); // "\n" represents a new line 
		 fWriter.write("nrows         "+nRows+"\r\n");
		 fWriter.write("xllcorner     "+origin[0]+"\r\n");
		 fWriter.write("yllcorner     "+origin[1]+"\r\n");
		 fWriter.write("cellsize      "+resolution+"\r\n");
		 fWriter.write("NODATA_value  "+nullValue+"\r\n");
		 for (int i = 0; i < nRows; i++) { 
		  		for (int j = 0; j < nCols; j++) { 
		  			fWriter.write(values[i][j]+" ");
		  		}
		  		fWriter.write("\r\n");
		  	}
		 fWriter.close();
        }
        catch (Exception e) {  
        	e.printStackTrace(); 
        } 
    }
	
    public BufferedImage toImage() {
    	BufferedImage image=new BufferedImage(nCols,nRows,BufferedImage.TYPE_INT_RGB);
    	WritableRaster raster = image.getRaster(); 
		double max =getMax();
		double min =getMin();
		double range=max-min;
		
		int[] color = new int[3]; 
        for(int i=0; i<nRows;i++) {
            for(int j=0; j<nCols;j++) {
                    double colour =(max-values[i][j])/range*255;
                    color[0] = (int) Math.round(colour); // Red 
                    color[1] = (int) Math.round(colour); // Green 
                    color[2] = (int) Math.round(colour); // Blue 
                    raster.setPixel(j,i,color);
            }
        }
		return image;
	}

    public BufferedImage toImage(double[] voi) {
		BufferedImage image=new BufferedImage(nCols,nRows,BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = image.getRaster(); 
		HashMap<Double,int[]> slumpfarg=new HashMap<Double,int[]>();
		Random rand= new Random();
		
		for(int i=0;i<voi.length;i++){
			int[] color =new int[3];	
			color[0]=rand.nextInt(255);
			color[1]=rand.nextInt(255);
			color[2]=rand.nextInt(255);
			slumpfarg.put(voi[i],color);
		}
		
		for(int i=0; i<nRows;i++) {
            for(int j=0; j<nCols;j++) {
				for(int k=0;k<voi.length;k++){
					if(values[i][j]==voi[k]){
						raster.setPixel(j,i,slumpfarg.get(voi[k]));
					}
				}
			}
		}
		return image;
	}
    
	private double getMax() { 
		double max = Double.NEGATIVE_INFINITY; 
		for (int i = 0; i < nRows; i++) { 
			for (int j = 0; j < nCols; j++) { 
				if (values[i][j] > max) { 
					max = values[i][j]; 
				} 
			} 
		} 
		return max; 
	}
	private double getMin() { 
		double min = Double.POSITIVE_INFINITY; 
		for (int i = 0; i < nRows; i++) { 
			for (int j = 0; j < nCols; j++) { 
				if (values[i][j] < min) { 
					min = values[i][j]; 
				} 
			} 
		} 
		return min; 
	}

	public Layer localSum(Layer inLayer, String outLayerName){
		Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
		for(int i=0; i<nRows; i++){
			for(int j=0; j<nCols; j++){
				outLayer.values[i][j] = values[i][j] + inLayer.values[i][j];
			}
		}
		return outLayer;
	}
	
	public Layer focalVariety(int r, boolean IsSquare, String outLayerName) {
		Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
		for(int i=0; i<nRows; i++){
			for(int j=0; j<nCols; j++){
				int[] arrayneighbor = getNeighborhood(i,j,r,IsSquare);
				double[] Array = new double [arrayneighbor.length];
				for (int k=0; k<arrayneighbor.length; k++){
					int m = arrayneighbor[k]/nCols; // the row number of cell i
					int n = arrayneighbor[k]%nCols; // the column number of cell i
					Array[k] = values[m][n];
				}
				//ArrayList<Double> valueslist= new ArrayList<Double>();
				//for(int k=0; k<arrayneighbor.length; k++){
					//int m = arrayneighbor[k]/nCols; // the row number of cell i
					//int n = arrayneighbor[k]%nCols; // the column number of cell i
					//double vaulesofmn = values[m][n];
					//valueslist.add(vaulesofmn);
				//}
				System.out.println(Arrays.toString(Array));
				//double[] Array = new double [valueslist.size()];
				//int counter = 0;
				//for (double vaulesofmn: valueslist) {
					//Array[counter] = vaulesofmn;
					//counter++;
				//}
				Arrays.sort(Array);
				int variety=1;
				 for (int t = 0; t < Array.length-1; t++) {
				        if(Array[t] !=Array[t+1]) {
				        	variety++;
				        }
				    }
				 outLayer.values[i][j]=variety;
			    }
			}
	return outLayer;
	}
	
	public Layer focalSlope(int cellsize, String outLayerName) {
		Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				if (i - 1 < 0 || i + 1 > nRows - 1 || j - 1 < 0 || j + 1 > nCols - 1) {
					// attention: the cells in the boundary rows or columns don't have enough
					// adjacent cells, so skip them
					continue;
				} else {
					// the slope along x coordination
					double slope_x = ((values[i - 1][j + 1] + 2 * values[i][j + 1] + values[i + 1][j + 1])
							- (values[i - 1][j - 1] + 2 * values[i][j - 1] + values[i + 1][j - 1])) / (8 * cellsize);
					// the slope along y coordination
					double slope_y = ((values[i + 1][j - 1] + 2 * values[i + 1][j] + values[i + 1][j + 1])
							- (values[i - 1][j - 1] + 2 * values[i - 1][j] + values[i - 1][j + 1])) / (8 * cellsize);
					//57.29578 means 180/¦Ð, it transfer a arc value into degree value
					double slope = Math.atan(Math.sqrt(Math.pow(slope_x, 2) + Math.pow(slope_y, 2))) * 57.29578;
					//the result doesn't contain any decimal, you can change it if you want
					outLayer.values[i][j] = Math.round(slope);
				}
			}
		}
		return outLayer;
	}

	public Layer focalAspect(String outLayerName) {
		Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				if (i - 1 < 0 || i + 1 > nRows - 1 || j - 1 < 0 || j + 1 > nCols - 1) {
					// attention: the cells in the boundary rows or columns don't have enough
					// adjacent cells, so skip them
					continue;
				} else {
					double slope_x = ((values[i - 1][j + 1] + 2 * values[i][j + 1] + values[i + 1][j + 1])
							- (values[i - 1][j - 1] + 2 * values[i][j - 1] + values[i + 1][j - 1])) / 8;
					double slope_y = ((values[i + 1][j - 1] + 2 * values[i + 1][j] + values[i + 1][j + 1])
							- (values[i - 1][j - 1] + 2 * values[i - 1][j] + values[i - 1][j + 1])) / 8;
					double aspect = Math.atan2(slope_y, -1 * slope_x) * 57.29578;
					// attention:the result range of atan2 function is [-¦Ð, ¦Ð], but the the range of
					// aspect is[0,360], which indicates direction changes from direct north to the
					// east, and back to north finally as the value grows
					if (slope_x == 0 && slope_y == 0) {
						outLayer.values[i][j] = -1;
					} else if (aspect < 0) {
						outLayer.values[i][j] = 90 - Math.round(aspect);
					} else if (aspect > 90) {
						outLayer.values[i][j] = 450 - Math.round(aspect);
					} else {
						outLayer.values[i][j] = 90 - Math.round(aspect);
					}
					// attention: when slope_x and slope_y are 0, this cell doesn't
					// have any aspect, so I make them be -1.0
				}
			}
		}
		return outLayer;
	}

	public Layer zonalMinimum(Layer zoneLayer, String outLayerName) {
		Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
		HashMap<Double, Double> hmzone = new HashMap<Double, Double>();
		for (int i = 0; i < (nRows); i++) {
			for (int j = 0; j < (nCols); j++) {
				if (!hmzone.containsKey(zoneLayer.values[i][j])) {
					hmzone.put(zoneLayer.values[i][j], values[i][j]);
				} 
				else {
					if (values[i][j] < hmzone.get(zoneLayer.values[i][j])) {
						hmzone.put(zoneLayer.values[i][j], values[i][j]);
					}
				}
			}
		} 
		for (int i = 0; i < (nRows); i++) {
			for (int j = 0; j < (nCols); j++) {
				outLayer.values[i][j] = hmzone.get(zoneLayer.values[i][j]);
			}
		}
		return outLayer;
	}
	
	private int[] getNeighborhood(int i, int j, int r, boolean isSquare) {
		ArrayList<Integer> l = new ArrayList<Integer>();
			for (int dx = -r; dx <= r; dx++) {
				for (int dy = -r; dy <= r; dy++) {
					int nx = i + dx;
					int ny = j + dy;
					if (nx < 0 || nx > nRows - 1 || ny < 0 || ny > nCols - 1) {
						continue;
					} else {
						int neighborRC = nx*nCols+ny;
						l.add(neighborRC);
					}
				}
			}
			int[] intObjArray = new int[l.size()];
			int counter = 0;
			for (int intObj : l) {
				intObjArray[counter] = intObj;
				counter++;
			}
			return intObjArray;
		}
	}

