package com.mycompany.bchacks2020;


public class Lot {
	
	private int id;
	private boolean vacant;
	static int count;
	private int[][] daCoo = new int[4][2];
	
        public Lot()
        {
            System.out.println("Hello");
        }
            
	public Lot(int x, int y, int width, int height){
		count++;
		setTo(0,0,x);
		setTo(0,1,y);
		setTo(1,0,x+width);
		setTo(1,1,y);
		setTo(2,0,x+width);
		setTo(2,1,y+height);
		setTo(3,0,x);
		setTo(3,1,y+height);
	}
	
	void setTo(int i, int j, int x) {
		//if (i > 0 && j > 0 && x > 0)
			daCoo[i][j] = x;
	}
	void setVacant(boolean vacant) {
		//if???
		this.vacant = vacant;
	}
	
	int getId() {
		return this.id;
	}
	boolean getVacant() {
		return this.vacant;
	}
	int[][] getDaCoo() {
		return this.daCoo;
	}

}
