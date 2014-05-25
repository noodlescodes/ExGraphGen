// class that reads the output of the model and gets the identity matrix from it.

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class OutputParser {
	private String readFile;
	private int sol[][];

	public OutputParser(String readFile, int deltaL, int deltaU) {
		sol = new int[deltaL * deltaU + 1][deltaL * deltaU + 1];
		this.readFile = readFile;
	}
	
	public void read() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(readFile));
			String line;
			while((line = br.readLine()) != null) {
				if(line.length() > 0) {
					if(line.charAt(0) == 'B') {
						line = line.replaceAll("\\s+", "");
						line = line.substring(1);
						int i = Integer.parseInt(line.split("E")[0].split(";")[0]) - 1;
						int j = Integer.parseInt(line.split("E")[0].split(";")[1]) - 1;
						sol[i][j] = Integer.parseInt(line.split("E")[1]);
					}
				}
			}
			br.close();
		}
		catch(FileNotFoundException e) {
		}
		catch(IOException e) {
		}
	}
	
	public void write(String writeFile) {
		try {
			PrintWriter w = new PrintWriter(new BufferedWriter(new FileWriter(writeFile)));
			for(int i = 0; i < sol.length; i++) {
				for(int j = 0; j < sol[i].length; j++) {
					if(j + 1 < sol.length) {
						w.print(sol[i][j] + ",");
					} else {
						w.print(sol[i][j]);
					}
				}
				w.println("");
			}
			w.close();
		} catch(IOException e) {
		}
	}
}
