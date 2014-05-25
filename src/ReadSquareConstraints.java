import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadSquareConstraints {
	private int numberOfConstraints;
	private int[][][] constraints;

	public ReadSquareConstraints(String file) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			line = br.readLine();
			numberOfConstraints = Integer.parseInt(line);
			constraints = new int[numberOfConstraints][4][2];
			int count = 0;
			while((line = br.readLine()) != null) {
				if(line.length() > 0) {
					String[] splitline = line.split(":");
					for(int i = 0; i < splitline.length; i++) {
						constraints[count][i][0] = Integer.parseInt(splitline[i].split(";")[0]);
						constraints[count][i][1] = Integer.parseInt(splitline[i].split(";")[1]);
					}
					count++;
				}
			}
			br.close();
		}
		catch(FileNotFoundException e) {
		}
		catch(IOException e) {
		}
	}
	
	public int[][][] getConstraints() {
		return constraints;
	}
}
