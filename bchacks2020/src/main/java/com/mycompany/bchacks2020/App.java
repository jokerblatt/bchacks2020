package com.mycompany.bchacks2020;
import java.io.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class App
{
    public static void main(String args[])
    {
        try
	{
            File parking_lot = new File("C:/Users/Ryan Lam/Desktop/hackathon/parking_lot2.PNG");
            BufferedImage bi = ImageIO.read(parking_lot);
            int width = bi.getWidth();
             int height = bi.getHeight();
            File binaryPic = new File("C:/Users/Ryan Lam/Desktop/hackathon/binary.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(binaryPic));
            for (int x=0;x<height;x++)
            {
                for(int y=0;y<width;y++)
                {
                    int pixel = bi.getRGB(y,x);
                    Color color = new Color(pixel,true);
                    if(color.getRed()==0)
                    {
                        bw.write("1");
                    }
                    else
                    {
                        bw.write("0");
                    }
                 
                }
                bw.write("\n");
            }
	}
	catch(Exception e)
	{
            e.printStackTrace();
	}
    }
    
}
