
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class MasterMild {

    public final String SECRET_CODE_LOCATION = "~/secret.code"; //path to .code file with secret code.
    public final int DIGITS = 6; //number of digits in secret code
    public final int MAX_RETRY = 10; //number of attempts

    public final String USER_MESSAGE_GUESS_CODE = ">> Guess the %d-digit secret code (%d):";
    public final String USER_MESSAGE_INVALID_INPUT = ">> Yikes! Invalid input";
    public final String USER_MESSAGE_WRONG_GUESS = ">> Wrong! SVD=%d, HD=%d";
    public final String USER_MESSAGE_CORRECT_GUESS = ">> Correct - You won!";
    public final String USER_MESSAGE_GAME_OVER = ">> Wrong - Game over!";

    public static void main(String[] args) throws Exception {
        MasterMild mm = new MasterMild();
        mm.startGame();

    }

    public void startGame() {
        Scanner sc = new Scanner(System.in);
        try {

            Path p1 = Paths.get(SECRET_CODE_LOCATION);
            String secretCode = Files.readAllLines(p1).get(0);
            int sumofSecret = 0;

            if (DIGITS == secretCode.length()) {

            } else if (DIGITS < secretCode.length()) {

                secretCode = secretCode.substring(secretCode.length() - DIGITS);
            } else {
                for (int a = 0; a <= DIGITS - secretCode.length(); a++) {
                    secretCode = "0" + secretCode;
                }
            }
            int[] secret = new int[DIGITS];
            for (int i = 0; i < DIGITS; i++) {
                secret[i] = Integer.parseInt(String.valueOf(secretCode.charAt(i)));
                sumofSecret += secret[i];
            }

//	GUESSES
            for (int count = MAX_RETRY; count > 0; count--) {

                System.out.printf("\n" + USER_MESSAGE_GUESS_CODE, DIGITS, count);
                String guess = sc.nextLine();
                guess = guess.trim();
                int[] guesSecret = new int[DIGITS];
                int guessSum = 0;

                try {
                    if (guess.length() > DIGITS) {
                        System.out.printf("\n" + USER_MESSAGE_INVALID_INPUT);
                        count++;
                        continue;
                    }

                    for (int i = 0; i < DIGITS; i++) {
                        guesSecret[i] = Integer.parseInt(String.valueOf(guess.charAt(i)));
                        guessSum += guesSecret[i];

                    }
                } catch (Exception ex) {
                    System.out.printf("\n" + USER_MESSAGE_INVALID_INPUT);
                    count++;
                    continue;
                }
//	WIN
                if (secretCode.equals(guess)) {
                    System.out.println("\n" + USER_MESSAGE_CORRECT_GUESS);
                    System.exit(0);
                } else {
                    int hamminDistance = calculateHamming(secret, guesSecret);
                    System.out.printf("\n" + USER_MESSAGE_WRONG_GUESS, (sumofSecret - guessSum), hamminDistance);
                }
            }
            System.out.println("\n\n" + USER_MESSAGE_GAME_OVER);
            System.out.println("The secret code was: " + secretCode);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static int calculateHamming(int secretArr[], int inputArr[]) {

        int distance = 0;

        for (int i = 0; i < secretArr.length; i++) {
            if (secretArr[i] != inputArr[i]) {
                distance++;
            }
        }

        return distance;

    }
}
