package methods;

import java.util.List;

import util.EigenValueVector;
import util.Matrix;
/*
 * Methods that use Similarities transformations:
 * ----- QR
 * ----- Jacobi
 * 
 * It can be used with Householder transformation
 * 
 */
public interface Similarities {
	public List<EigenValueVector> solve(Matrix m, double error);
	public void printCommandLine(List<EigenValueVector> ev);
}
