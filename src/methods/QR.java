package methods;

import java.util.ArrayList;
import java.util.List;

import util.EigenValueVector;
import util.Matrix;

/*
 * QR Method
 */
public class QR implements Similarities {

	@Override
	public List<EigenValueVector> solve(Matrix m, double erro) {
		int ordem = m.getRowDimension();
		Matrix diagMatrix = null;
		Matrix r = m.copy();
		Matrix EVVMatrix = Matrix.identity(ordem, ordem);
		
		double normaM;
		do {
			Matrix qT = Matrix.identity(ordem, ordem);
			for (int j = 0; j < ordem - 1; j++) {
				for (int i = j + 1; i < ordem; i++) {
					//Build pT (P transposed): the rotation orthonormal matrix that will to zero M[i][j] which i != j  
					
					Matrix pT;
					{
						//Find the theta angle
						double teta;
						if (r.get(j, j) == 0) {
							teta = Math.PI / 2.0;
						} else {
							
							teta = Math.atan(r.get(i, j) / r.get(j, j));
						}
						double senTeta = Math.sin(teta);
						double cosTeta = Math.cos(teta);
						pT = Matrix.identity(ordem, ordem);
						pT.set(j, j, cosTeta);
						pT.set(i, i, cosTeta);
						pT.set(i, j, - senTeta);
						pT.set(j, i, senTeta);
					}
					r = pT.times(r);
					qT = pT.times(qT);
				}
			}

			//If the R matrix doesnt be a upper triangled, we must redo the algorithm
			if (!Matrix.isUpperTriangular(r, erro)) {
				normaM = erro + 1;
				continue;
			}
			Matrix q = qT.transpose();

			diagMatrix = r.times(q);
			EVVMatrix = EVVMatrix.times(q);
			
			r = diagMatrix; //Prepare the matrix for the next iteration
			normaM = Matrix.sumExceptDiagonal(diagMatrix);
		} while (normaM > erro);
		
		List<EigenValueVector> eigenValueVectors = new ArrayList<EigenValueVector>(ordem);
		for (int j = 0; j < ordem; j++) {
			double[] eigenvector = new double[ordem];
			for (int i = 0; i < ordem; i++) {
				eigenvector[i] = EVVMatrix.get(i, j);
			}
			EigenValueVector autovalorAutovetor = new EigenValueVector();
			autovalorAutovetor.eigenValue = diagMatrix.get(j, j);
			autovalorAutovetor.eigenVector = eigenvector;
			eigenValueVectors.add(autovalorAutovetor);
		}
		return eigenValueVectors;
	}

	@Override
	public void printCommandLine(List<EigenValueVector> ev) {
		System.out.println("QR	 Method.");
		for (EigenValueVector eigenValueVector : ev) {
			System.out.println("Eigen Value:" + eigenValueVector.eigenValue);
			System.out.println("Eigen Vector:");
			Matrix.vectorToMatrix(eigenValueVector.eigenVector).transpose()
					.print(4, 7);

		}

	}

}
