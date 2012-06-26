package methods;

import java.util.ArrayList;
import java.util.List;

import util.EigenValueVector;
import util.Matrix;

/*
 * 
 */
public class Jacobi implements Similarities {

	@Override
	public List<EigenValueVector> solve(Matrix m, double error) {
		int order = m.getRowDimension();
		Matrix diagMatrix = m.copy();
		Matrix EVMatrix = Matrix.identity(order, order);
		double normM;
		do {
			for (int j = 0; j < order - 1; j++) {
				for (int i = j + 1; i < order; i++) {
					// Construct rotation orthonormal
					Matrix p;
					{
						// Find the theta angle
						double theta;
						if (diagMatrix.get(j, j) == diagMatrix.get(i, i)) {
							theta = Math.PI / 4.0;
						} else {
							theta = 0.5 * Math.atan(2
									* diagMatrix.get(i, j)
									/ (diagMatrix.get(j, j) - diagMatrix.get(i,
											i)));
						}
						p = Matrix.identity(order, order);
						p.set(j, j, Math.cos(theta));
						p.set(i, i, Math.cos(theta));
						p.set(i, j, Math.sin(theta));
						p.set(j, i, -Math.sin(theta));

					}
					Matrix pT = p.transpose();

					diagMatrix = pT.times(diagMatrix).times(p);
					EVMatrix = EVMatrix.times(p);
				}

			}
			// TODO
			normM = Matrix.sumExceptDiagonal(diagMatrix);
		} while (normM > error);

		List<EigenValueVector> eigenValuesVectors = new ArrayList<EigenValueVector>(
				order);
		for (int j = 0; j < order; j++) {
			double[] eigenVector = new double[order];
			for (int i = 0; i < order; i++) {
				eigenVector[i] = EVMatrix.get(i, j);
			}
			EigenValueVector eigenValueVector = new EigenValueVector();
			eigenValueVector.eigenValue = diagMatrix.get(j, j);
			eigenValueVector.eigenVector = eigenVector;
			eigenValuesVectors.add(eigenValueVector);
		}

		return eigenValuesVectors;
	}

	@Override
	public void printCommandLine(List<EigenValueVector> ev) {
		System.out.println("Jacobi Method.");
		for (EigenValueVector eigenValueVector : ev) {
			System.out.println("Eigen Value:" + eigenValueVector.eigenValue);
			System.out.println("Eigen Vector:");
			Matrix.vectorToMatrix(eigenValueVector.eigenVector).transpose()
					.print(4, 7);

		}
	}

}
