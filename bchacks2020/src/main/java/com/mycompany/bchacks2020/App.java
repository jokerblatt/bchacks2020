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
            int[][] binaryLot = new int[height][width]; // initiate 2d binary array
            int[] lotDim; // initiate second coordinates of first point 
            ArrayList<Lot> lots_List = new ArrayList<Lots>();
            
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
                    }
                    elseif((x+1+lotDim[0])==1)
                     {
                        int currentX = x;
                        int currentY = y;
                        Lot lot = new Lot(currentX,currentY,LotX,LotY);
                        lots_List.add(lot);
                        x = x+LotX+1;
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
    
}
