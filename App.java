
import java.io.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class App
{
    public static int lotX; // lot dimensions
    public static int lotY; // lot dimensions
    public static int[][] binaryLot;
    public static int width, height;
    public static int divider;
    public static boolean lotAddedInAColumn;
    public static boolean lotAddedInARow;
    public static int lotCount;
    
    public static void main(String args[])
    {
        try
        {
            File parking_lot = new File("parking_lot2.png");
            BufferedImage bi = ImageIO.read(parking_lot);
            width = bi.getWidth(); // image width
            height = bi.getHeight(); //image height
            int y=0; // initiate iterator
            int x=0; // initiate iterator 
            boolean done = false; // initiate done
            binaryLot = new int[width][height]; // initiate 2d binary array
            int[] lotDim = {0,0}; // initiate second coordinates of first point 
            ArrayList<Lot> lots_List = new ArrayList<Lot>();            
            
            for(y=0;y<height;y++)
            {
                for(x=0;x<width;x++)
                {
                    int pixel = bi.getRGB(x,y); //pixel is stacked int of ARGB values, need to convert to color object to get individual rgb
                    Color color = new Color(pixel,true); //creates new color object to get color
                    if(color.getRed()==0) //color.getRed() will be 0 if pixel is black, 255 if pixel is white
                    {
                        binaryLot[x][y] = 1;
                    }
                    else
                    {
                        binaryLot[x][y] = 0;
                    }
                }
            }

            y = 0;

            /////////////////////////////////////////////////////////////////////////////////////
            ///////////////////             VERTICAL LOT CHECK             //////////////////////
            /////////////////////////////////////////////////////////////////////////////////////

            int firstX = 0;
            int firstY = 0;
            boolean foundTheZero = false;

            for(int firsty = 0; firsty < height && !foundTheZero ; firsty++)
            {
                for (int firstx = 0; firstx < width && !foundTheZero ; firstx++ )
                {
                    if (binaryLot[firstx][firsty] == 0) {
                        foundTheZero = true;
                        firstX = firstx;
                        firstY = firsty;
                    }
                }
            }

            y = firstY;

            System.out.println("first " + firstX + ", " + firstY);
            

            while(y < height)
            {   
                lotAddedInARow = false;
                for(x = firstX ; x < width ; x++)
                {
                    if (binaryLot[x][y] == 0 && !done)
                    {
                        lotDim = getDim(x,y); 
                        done = true;
                    }

                    if ((y + lotDim[1] < height) && (x + lotDim[0] < width) && binaryLot[x + lotDim[0]][y] == 1 && binaryLot[x-1][y] != 0) // Check doesn't jump
                    {
                        int lotX = lotDim[0];
                        int lotY = lotDim[1];

                        if ((binaryLot[x][y - 1] != 0) || (binaryLot[x][y + lotDim[1]] != 0)) // if the bottom is a 1
                        {
                            Lot lot = new Lot(x + 1, y, lotX, lotY);
                            lotAddedInARow = true;
                            System.out.println(lot.count + ": VERTICAL | " + x + ", " + y);
                            lotCount = lot.count;
                        }
                        x += lotX; // JUMP

                        if(x+lotX+1 > width) {
                            if (lotAddedInARow)
                                y += lotY + 1;
                            break;
                        }
                        x = getNextZero_verticalCheck(x, y);
                    }
                }
                y++; 
            }

            System.out.println("\nTOTAL PARKING LOTS: " + lotCount);

            /////////////////////////////////////////////////////////////////////////////////////
            /////////////////             HORIZONTAL LOT CHECK               ////////////////////
            /////////////////////////////////////////////////////////////////////////////////////
            /*
            while(x < width)
            {   
                lotAddedInAColumn = false;
                for(y = firstY ; y < height ; y++)
                {
                    if (binaryLot[x][y] == 0 && !done)
                    {
                        lotDim = getDim(x,y); 
                        done = true;
                    }

                    if ((y + lotDim[0] < height) && (x + lotDim[1] < width) && binaryLot[x][y + lotDim[0]] == 1 && binaryLot[x][y - 1] != 0) // Check doesn't jump
                    {
                        int lotX = lotDim[1]; // flipped from lotDim in vertical check
                        int lotY = lotDim[0];

                        if ((binaryLot[x - 1][y] != 0) || (binaryLot[x + lotDim[1]][y] != 0)) // if the bottom is a 1
                        {
                            Lot lot = new Lot(x, y + 1, lotX, lotY);
                            lotAddedInAColumn = true;
                            System.out.println(lot.count + ": HORIZONTAL | " + x + ", " + y);
                        }
                        y += lotY; // JUMP Vertically downwards

                        if(y+lotY+1 > height) {
                            if (lotAddedInAColumn)
                                x += lotX + 1;
                            break;
                        }
                        y = getNextZero_horizontalCheck(x, y);
                    }
                }
                x++; 
            }*/
	   }

    	catch(Exception e)
    	{
            e.printStackTrace();
    	}
    }
    
    public static int[] getDim(int x, int y)
    {
        int divider = 0;
        int[] firstCoordinate = {x - 1, y}; // creates new 2-element arrays for every coordinate
        int[] secondCoordinate = {0,0}; // creates new 2-element arrays for every coordinate
        int[] thirdCoordinate = {0,0}; // creates new 2-element arrays for every coordinate
        int[] fourthCoordinate = {0,0}; // creates new 2-element arrays for every coordinate

        while (x < width && binaryLot[x][y]==0) // while the pixel is white (0)
                ++x; // keep iterating over each pixel in the ROW

        // Store x- and y- values in the second coordinate
        secondCoordinate[0] = x - 1;
        secondCoordinate[1] = y;

        System.out.println("sec coo: " + x + ", " + y);

       // y++;
        while (y < height && binaryLot[x][y]==1)
        {
             ++y; // keep iterating over each pixel in the COLUMN
             //System.out.println(binaryLot[x][y]);
        }    

        // Store x- and y- values in the second coordinate
        thirdCoordinate[0] = x;
        thirdCoordinate[1] = y - 1;

        // --- At this point, we know the dimension of one parking lot ---
        lotX = secondCoordinate[0] - firstCoordinate[0]; // X2 - X1; delta X
        lotY = thirdCoordinate[1] - secondCoordinate[1]; // Y3 - Y2; delta Y

        System.out.println("LotX: " + lotX + " | LotY: " + lotY);

        int[] toReturn = {lotX, lotY, divider};

        return toReturn;
    } 

    public static int getNextZero_verticalCheck(int x, int y) 
    {   
        boolean foundTheZero = false;

        for (int i = x ; i < width && !foundTheZero ; i++) {
            if (binaryLot[i][y] == 0) {
                foundTheZero = true;
                return i - 1;
            }
        }
        return width - 1;
    } 

    public static int getNextZero_horizontalCheck(int x, int y) 
    {   
        boolean foundTheZero = false;

        for (int j = y ; j < height && !foundTheZero ; j++) {
            if (binaryLot[x][j] == 0) {
                foundTheZero = true;
                return j - 1;
            }
        }
        return height - 1;
    }   
}
