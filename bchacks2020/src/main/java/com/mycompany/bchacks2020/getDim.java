int[] firstCoordinate, secondCoordinate, thirdCoordinate, fourthCoordinate = new Array(2); // creates new 2-element arrays for every coordinate

// Store x- and y- values in the first coordinate
firstCoordinate[0] = x - 1;
firstCoordinate[1] = y;

while (!binaryLot[x][y]) // while the pixel is white (0)
	x++; // keep iterating over each pixel in the ROW

// Store x- and y- values in the second coordinate
secondCoordinate[0] = x;
secondCoordinate[1] = y;

while (!binaryLot[x][y])
	y++; // keep iterating over each pixel in the COLUMN

// Store x- and y- values in the second coordinate
thirdCoordinate[0] = x;
thirdCoordinate[1] = y - 1;

// --- At this point, we know the dimension of one parking lot ---
lotX = secondCoordinate[0] - firstCoordinate[0] // X2 - X1; delta X
lotY = thirdCoordinate[1] - secondCoordinate[1] // Y3 - Y2; delta Y

done = true;

int[] toReturn = new Array(2);
toReturn[0] = lotX;
toReturn[1] = lotY;

return toReturn;

