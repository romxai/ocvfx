package ocv;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

// Main class
public class Sharpness {

    // Main driver method
    public static void main(String[] args)
    {
        // Check if the input file path is provided as command-line argument
        if (args.length == 0) {
            System.out.println("Usage: java Sharpness <input_image_path>");
            return;
        }

        // Try block to check for exceptions
        try {

            // For proper execution of native libraries
            // Core.NATIVE_LIBRARY_NAME must be loaded
            // before calling any of the opencv methods
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

            // Reading the input image from local directory
            // by creating object of Mat class
            Mat source = Imgcodecs.imread(args[0], Imgcodecs.IMREAD_ANYCOLOR);

            Mat destination
                    = new Mat(source.rows(), source.cols(),
                    source.type());

            // Filtering
            Imgproc.GaussianBlur(source, destination,
                    new Size(0, 0), 10);
            Core.addWeighted(source, 1.5, destination, -0.5,
                    0, destination);

            // Define the output file path
            String outputPath = "C:\\Users\\Fiona\\IdeaProjects\\ocvfx\\src\\main\\java\\ocv\\"
                    + args[0].substring(args[0].lastIndexOf("\\") + 1, args[0].lastIndexOf(".")) + "_sharpness.jpg";

            // Writing output image to directory in the local
            // system
            Imgcodecs.imwrite(outputPath, destination);

            // Display message for successful execution
            System.out.println("Sharpness enhanced. Output saved as: " + outputPath);
        }

        // Catch block to handle exceptions
        catch (Exception e) {

            // Display message when exception occurs
            System.out.print("Exception/s Occurred");
        }
    }
}
