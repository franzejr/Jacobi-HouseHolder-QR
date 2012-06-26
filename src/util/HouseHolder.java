package util;

public class HouseHolder {
	/*
	 * Algorithm:
	 * Build the HouseHolder Matrix
	 * Do each similarity transformation
	 * 
	 * Objective: Transform a simple matrix into a tridiagonalized matrix.
	 * @return Matrix
	 *  
	 *  */
	public static Matrix execute(Matrix matrix){
		//Getting the matrix
		Matrix tridiagonalMatrix = matrix.copy();
		int order = matrix.getRowDimension();
		for (int i = 0; i < order - 2 ; i++){
			Matrix subColumnI = tridiagonalMatrix.getMatrix(0,order - 1, i, i);
			//Setting 0 in the elements
			for(int j = 0; j <= i; j++){
				subColumnI.set(j,0,0);
			}
			//Calculate the norm of this subColumn
			double normSubColumnI = Matrix.vectorMagnitude(subColumnI);
			
			//Create a new column matrix with the elements like 0, except the element from the line i + 1
			Matrix columnILine = new Matrix(order, 1, 0);
			int alpha = (int) Math.signum(tridiagonalMatrix.get(i,i));
			columnILine.set(i+1,0,alpha * normSubColumnI);
			
			//Calculate the n_k vector = columnI - columnILine
			Matrix n_kVector = subColumnI.minus(columnILine);
			
			//Normalize the n_kVector 
			n_kVector = Matrix.normalize(n_kVector);
			
			//Calculate the HouseHolder Matrix H_k = I - 2  * n_kVector * T(n_k)
			Matrix identityMatrix = Matrix.identity(order, order);
			Matrix h_kMatrix = identityMatrix.minus(n_kVector.times(n_kVector.transpose().times(2.0)));
			
			//Do the similarity transformation
			tridiagonalMatrix = h_kMatrix.times(tridiagonalMatrix).times(h_kMatrix.transpose());
			
			
		}
		return tridiagonalMatrix;
		
	}
}
