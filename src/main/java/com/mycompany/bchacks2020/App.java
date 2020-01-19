package com.mycompany.bchacks2020;


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
    public static void main(String args[])
    {
        try
	   {
            File parking_lot = new File("C:/Users/Ryan Lam/Desktop/hackathon/parking_lot2.png");
            BufferedImage bi = ImageIO.read(parking_lot);
            width = bi.getWidth(); // image width
            height = bi.getHeight(); //image height
            int y=0; // initiate iterator
            int x=0; // initiate iterator 
            boolean done = false; // initiate done
            binaryLot = new int[width][height]; // initiate 2d binary array
            int[] lotDim = {0,0}; // initiate second coordinates of first point 
            ArrayList<Lot> lots_List = new ArrayList<Lot>();         
            int divider=0;
            
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
            y=0;

            /////////////////////////////////////////////////////////////////////////////////////
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
                for(x = firstX ; x < width ; x++)
                {
                    if (binaryLot[x][y] == 0 && !done)
                    {
                        lotDim = getDim(x,y); 
                        done = true;
                        lotX = lotDim[0];
                        lotY = lotDim[1];
                    }

                    if ((y + lotY < height) && (x + lotX < width) && binaryLot[x + lotX][y] == 1 && binaryLot[x-1][y] != 0) // if jump and is black
                    {
                        if((binaryLot[x+1][y+lotY+1]==0 && binaryLot[x+1][y-2]==0)) // if jump and is black, and if top and bottom are blank
                        {
                            x+=lotX;
                            if(x+lotX+1 > width) // if top and bottom are blank and need to jump vertically down
                            {
                                boolean lotOnLevel = false;
                                        int z = lots_List.size()-1;
                                        int[][] check = lots_List.get(z).getDaCoo();//only get the latest lot in the lot list
                                        if(y==check[0][1]) // if top and bottom are blank and there is lot on same level, jump y+=lotY
                                        {
                                            y+=lotY+1;
                                            x = getNextZero(0,y);
                                            lotOnLevel=true;
                                        }
                                       if(lotOnLevel==false) // if top and bottom are blank and there is no lot on same level, jump y++ 
                                        {
                                            y++;
                                            x = getNextZero(0,y);
                                        }
                                    
                               
                            }
                            else // jump and is black, top and bottom are blank, but not at end, jump to next black and get next zero
                            {
                                x = getNextZero(x,y);
                            }
                          
                        }
                     else // if jump and is black, top and bottom are blank, then it must be a lot
                     {
                        Lot lot = new Lot(x, y, lotX, lotY);
                        lots_List.add(lot);
                        System.out.println(lot.count + ": " + x + ", " + y);
                        x += lotX; // JUMP
                        if(x+lotX+1 > width) 
                        {
                            y += lotY + 1;
                            x=getNextZero(0,y);
                        }
                            x = getNextZero(x, y);
                     }
                     
                    }
                    else if(x+lotX+1 >= width) // if jump and is not black, (ie blank row) and at end, jump one row down
                    {
                        x=getNextZero(0,y);
                        break;
                    }
                }
                ++y; 
            }
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

        int[] toReturn = {lotX, lotY};

        return toReturn;
    } 

    public static int getNextZero(int x, int y) 
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
}
