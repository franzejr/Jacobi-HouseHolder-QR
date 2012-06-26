package main;

import java.util.List;

import methods.QR;
import util.EigenValueVector;
import util.HouseHolder;
import util.Matrix;
import util.ReadFile;

/*
 * Similarity Transaformation
 * HomeWork 11 - Francisco Jose Lins Magalhaes
 * Universidade Federal do Ceara
 * @author franzejr
 * 
 * 
 */
public class Main {

	public static void main(String[] args) {
		//File where the matriz is in
		String matrixPath = "matrix.txt";
		//Initial Eigen Vector -- hint
		String initialEigenVectorPath = "initialEigenVector.txt";
		//Error rate
		double error = 0.000000000001;
		try {
			Matrix m = new Matrix(ReadFile.readMatrixFromFile(matrixPath));
			double[] initialEigenVector = Matrix.matrixToVector(ReadFile
					.readMatrixFromFile(initialEigenVectorPath));
			System.out.println("Matriz inicial:");
			m.print(4, 7);
			/*Here is possible to choose between the methods */
			
			/*Power Iteration - regular */
			// PowerIteration method = new RegularIteration();
			
			/*Inverse Iteration */
			// PowerIteration method = new InverseIteration();
			
			/*Shifted Iteration, you can choose the shifted rate at the class */
			//PowerIteration method = new ShiftedIteration();
			
			/* To call for HomeWork 10 - EigenValues (Iterations methods) */
//			//Call to solve method
//			EigenValueVector result = method
//					.solve(m, initialEigenVector, error);
//			method.printCommandLine(result);
			
			/* Homework 11 - Methods using similarities transformations*/
			/*Jacobi */
			//Jacobi method = new Jacobi();
			
			/*QR */
			QR method = new QR();
			
			/* To call for HomeWork 11 - Methods that use similarities transformations */
			System.out.println("Sem usar Matriz de HouseHolder");
			List<EigenValueVector> results = method.solve(m, error);
			method.printCommandLine(results);
			
			//HouseHolder transformation
			
			System.out.println("Matriz de House Holder");
			Matrix mHouseHolder = HouseHolder.execute(m);
			
			mHouseHolder.print(4, 7);
			
			System.out.println("Aplicando a matriz de HouseHolder");
			List<EigenValueVector> results2 = method.solve(mHouseHolder, error);
			method.printCommandLine(results2);
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
