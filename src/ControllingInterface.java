import lpsolve.LpSolveException;

public class ControllingInterface {
	public static void main(String[] args) {
		int deltaL = 6;
		int deltaU = deltaL + 1;

		Model model;
		OutputParser op;
		CreateSquareConstraints csc;
		long time = System.currentTimeMillis();
		String date = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date(time));
		System.out.println("Start time:" + date);

		try {
			int iteration = 0;
			do {
				long timeIt = System.currentTimeMillis();
				date = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date(timeIt));
				System.out.println("Starting iteration #" + iteration + ". Time started: " + date);
				model = new Model("squares.dat", deltaL, deltaU, iteration, false);
				op = new OutputParser("models/ExSolution" + iteration + ".dat", deltaL, deltaU);
				csc = new CreateSquareConstraints("mat.dat", "squares.dat", deltaL, deltaU);
				int ret = model.execute();
				if(ret != 0) {
					System.out.println("No solution");
					break;
				}
				op.read();
				op.write("mat.dat");
				csc.createConstraints();
				System.out.println("Number of squares after iteration #" + iteration + ": " + csc.getNumberOfSquares());
				System.out.println("Time spent on iteration #" + (iteration++) + ": " + (System.currentTimeMillis() - timeIt));
			} while(csc.getNumberOfSquares() > 0);
		}
		catch(LpSolveException e) {
		}
		System.out.println("Time taken: " + (System.currentTimeMillis() - time));
	}
}