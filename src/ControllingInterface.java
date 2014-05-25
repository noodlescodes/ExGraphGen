import javax.sound.sampled.LineUnavailableException;

import lpsolve.LpSolveException;

public class ControllingInterface {
	public static void main(String[] args) {
		int deltaL = 7;
		int deltaU = deltaL + 1;

		Model model;
		OutputParser op;
		CreateSquareConstraints csc;
		long time = System.currentTimeMillis();
		String date = new java.text.SimpleDateFormat("EEE dd/MM/yyyy HH:mm:ss.SSS").format(new java.util.Date(time));
		System.out.println("Start time: " + date + "\n");

		try {
			int iteration = 0;
			do {
				long timeIt = System.currentTimeMillis();
				date = new java.text.SimpleDateFormat("EEE dd/MM/yyyy HH:mm:ss.SSS").format(new java.util.Date(timeIt));
				System.out.println("Starting iteration #" + iteration + ". Time started: " + date);
				model = new Model("squares.dat", deltaL, deltaU, iteration, false);
				op = new OutputParser("models/ExSolution" + iteration + ".dat", deltaL, deltaU);
				csc = new CreateSquareConstraints("mat.dat", "squares.dat", deltaL, deltaU);
				int ret = model.execute();
				if (ret != 0) {
					System.out.println("No solution");
					break;
				}
				op.read();
				op.write("mat.dat");
				csc.createConstraints();
				System.out.println("Number of squares after iteration #" + iteration + ": " + csc.getNumberOfSquares());
				System.out.println("Time spent on iteration #" + (iteration++) + ": " + (System.currentTimeMillis() - timeIt));
				System.out.println("Total time so far: " + (System.currentTimeMillis() - time) + "\n");
				Tone.sound(7000, 100, 1.0);
			} while (csc.getNumberOfSquares() > 0);
		} catch (LpSolveException e) {
		} catch (IllegalArgumentException e) {
		} catch (LineUnavailableException e) {
		}
		System.out.println("Solution found.");
		System.out.println("Time taken: " + (System.currentTimeMillis() - time));
	}
}