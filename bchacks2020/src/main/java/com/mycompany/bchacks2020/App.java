package com.mycompany.bchacks2020;
import java.io.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class App
{
    public static int LotX; // lot dimensions
    public static int LotY; // lot dimensions
    public static int[][] binaryLot;
    public static void main(String args[])
    {
        try
	{
            File parking_lot = new File("C:/Users/Ryan Lam/Desktop/hackathon/parking_lot2.PNG");
            BufferedImage bi = ImageIO.read(parking_lot);
            int width = bi.getWidth(); // image width
            int height = bi.getHeight(); //image height
            int y=0; // initiate iterator
            int x=0; // initiate iterator 
            boolean done = false; // initiate done
            binaryLot = new int[height][width]; // initiate 2d binary array
            int[] lotDim = {0,0}; // initiate second coordinates of first point 
            System.out.println("Hello");
            ArrayList<Lot> lots_List = new ArrayList<Lot>();
            
            for(y=0;y<height;y++)
            {
                for(x=0;x<width;x++)
                {
                    int pixel = bi.getRGB(x,y); //pixel is stacked int of ARGB values, need to convert to color object to get individual rgb
                    Color color = new Color(pixel,true); //creates new color object to get color
                    if(color.getRed()==0) //color.getRed() will be 0 if pixel is black, 255 if pixel is white
                    {
                        binaryLot[y][x] = 1;
                    }
                    else
                    {
                        binaryLot[y][x] = 0;
                    }
                }
            }
            while(y<height)
            {
                for(x = 0;x<width;x++)
                {
                    
                    if(binaryLot[y][x]==0 && !done) 
                    {
                        lotDim = getDim(x,y); 
                        done = true;
                    }
                    else if((x+1+lotDim[0])==1)
                    {
                        int currentX = x;
                        int currentY = y;
                        Lot lot = new Lot(currentX,currentY,LotX,LotY);
                        lots_List.add(lot);
                        x = x+LotX+1;
                        System.out.println(lots_List.toArray().length);
                        
                    }
                    else
                    {
                        y = y+LotY+1;
                    }
                }
            }
	}
	catch(Exception e)
	{
            e.printStackTrace();
	}
    }
    
    public static int[] getDim(int x, int y)
    {
        int[] firstCoordinate = {0,0}; // creates new 2-element arrays for every coordinate
        int[] secondCoordinate = {0,0}; // creates new 2-element arrays for every coordinate
        int[] thirdCoordinate = {0,0}; // creates new 2-element arrays for every coordinate
        int[] fourthCoordinate = {0,0}; // creates new 2-element arrays for every coordinate
         

        // Store x- and y- values in the first coordinate
        firstCoordinate[0] = x - 1;
        firstCoordinate[1] = y;

        while (binaryLot[x][y]==0) // while the pixel is white (0)
                x++; // keep iterating over each pixel in the ROW

        // Store x- and y- values in the second coordinate
        secondCoordinate[0] = x;
        secondCoordinate[1] = y;

        while (binaryLot[x][y]==1)
        {
             y++; // keep iterating over each pixel in the COLUMN
        }
            
               

        // Store x- and y- values in the second coordinate
        thirdCoordinate[0] = x;
        thirdCoordinate[1] = y - 1;

        // --- At this point, we know the dimension of one parking lot ---
        LotX = secondCoordinate[0] - firstCoordinate[0]; // X2 - X1; delta X
        LotY = thirdCoordinate[1] - secondCoordinate[1]; // Y3 - Y2; delta Y


        int[] toReturn = {LotX,LotY};

        return toReturn;


    }
        
    
}
