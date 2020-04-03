package main;

import foilp1.FOILP1;
import foilp2.FOILP2;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class Main {

	private static final String DATA_FOLDER = "data/";

	public static void main(String[] args) throws IOException {

		String filename = verifArguments(args);

		if(args.length == 3) {
			// verify oth can be converted to float
			float entropyThreshold = verifyFloat(args[1]);
			float minimumInstances = verifyFloat(args[2]);

			// then make the modification
			FOILP2.setEntropyThreshold(entropyThreshold);
			FOILP2.setMinimumInstances(minimumInstances);
		}
		new FOILP2(filename).execute();
		new FOILP1(filename).execute();
	}

	/**
	 * verify that the first argument is an existing arff file.
	 * @param args
	 * @return
	 */
	private static String verifArguments(String[] args) {

		// verify we have at least one argument
		if(args.length < 1){
			throw new IllegalArgumentException("Please use an arff file as argument !");
		}

		// verify we have an arff file
		String extension = Optional.ofNullable(args[0])
				.filter(f -> f.contains("."))
				.map(f -> f.substring(args[0].lastIndexOf(".") + 1))
				.get();
		if(!"arff".equals(extension))
			throw new IllegalArgumentException("Unknown extension " + extension + ". Please use an arff file !");


		String filename = DATA_FOLDER + args[0];

		// verify this file exists
		if (!new File(filename).exists())
			throw new IllegalArgumentException("File not found. Please files from the data folder (example : coup_de_soleil.arff !");

		return filename;
	}

	private static float verifyFloat(String str) {
		float f = Float.parseFloat(str);

		if(f > 1.0f || f < 0.0f) {
			throw  new IllegalArgumentException("The argument should be a float with : 0.0 < N < 1.0.");
		}

		return f;
	}
} 


