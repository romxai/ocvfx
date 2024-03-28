package ocv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.util.Scanner;

// Main class
public class Brightness {

    // Main driver method
    public static void main(String[] args) {
        // Check if the correct number of arguments is provided
        if (args.length != 1) {
            System.out.println("Usage: java Brightness <input_image_path>");
            return;
        }

        // Try block to check for exceptions
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

            Mat source = Imgcodecs.imread(args[0], Imgcodecs.IMREAD_ANYCOLOR);

            // Check if the image is loaded successfully
            if (source.empty()) {
                System.out.println("Error: Unable to load image");
                return;
            }

            // Define brightness enhancement parameters
            double alpha = getUserInput("Enter contrast value (1.0-3.0): ");
            double beta = getUserInput("Enter brightness value (0-100): ");

            Mat destination = new Mat();

            // Applying brightness enhancement
            source.convertTo(destination, -1, alpha, beta);

            // Output image
            String outputPath = "C:\\Users\\Fiona\\IdeaProjects\\ocvfx\\src\\main\\java\\ocv\\" + args[0].substring(args[0].lastIndexOf("\\") + 1, args[0].lastIndexOf(".")) + "_brightness.jpg";
            Imgcodecs.imwrite(outputPath, destination);
            System.out.println("Brightness enhanced image saved as: " + outputPath);
        } catch (Exception e) {
            // Print the exception on console
            // using getMessage() method
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Method to get user input for contrast and brightness
    private static double getUserInput(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(message);
        return scanner.nextDouble();
    }
}
