package es.udc.vvs.model.util.servidorutil;

import java.util.Random;

public class GenerarToken {
	
	private final static int A_ASCII_CODE = 65;
	private final static int Z_ASCII_CODE = 90;
	private final static int NUMBER_OF_LETTERS = Z_ASCII_CODE - A_ASCII_CODE + 1;
	
	
	private final static String generateRandomToken() {

		Random randomGenerator = new Random();
		byte[] saltAsByteArray = new byte[8];
		String salt;

		saltAsByteArray[0] = (byte) (randomGenerator.nextInt(NUMBER_OF_LETTERS) + A_ASCII_CODE);
		saltAsByteArray[1] = (byte) (randomGenerator.nextInt(NUMBER_OF_LETTERS) + A_ASCII_CODE);
		saltAsByteArray[2] = (byte) (randomGenerator.nextInt(NUMBER_OF_LETTERS) + A_ASCII_CODE);
		saltAsByteArray[3] = (byte) (randomGenerator.nextInt(NUMBER_OF_LETTERS) + A_ASCII_CODE);
		saltAsByteArray[4] = (byte) (randomGenerator.nextInt(NUMBER_OF_LETTERS) + A_ASCII_CODE);
		saltAsByteArray[5] = (byte) (randomGenerator.nextInt(NUMBER_OF_LETTERS) + A_ASCII_CODE);
		saltAsByteArray[6] = (byte) (randomGenerator.nextInt(NUMBER_OF_LETTERS) + A_ASCII_CODE);
		saltAsByteArray[7] = (byte) (randomGenerator.nextInt(NUMBER_OF_LETTERS) + A_ASCII_CODE);
		
		salt = new String(saltAsByteArray);

		return salt;

	}
	
	public final static String generateToken() {

		return generateRandomToken();

	}

}
