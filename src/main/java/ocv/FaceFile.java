package ocv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class FaceFile {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java FaceFile <input_image_path>");
            return;
        }

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        String inputFilePath = args[0];
        CascadeClassifier faceDetector = new CascadeClassifier("D:/Users/Fiona/Downloads/opencv/build/etc/haarcascades/haarcascade_frontalface_default.xml");
        Mat image = Imgcodecs.imread(inputFilePath);
        if (image.empty()) {
            System.out.println("Error: Unable to load image");
            return;
        }

        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);

        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(image, new Point(rect.x, rect.y),
                    new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0), 2);

            // Adding text "Face" below the rectangle
            Imgproc.putText(image, "Face", new Point(rect.x, rect.y + rect.height + 20),
                    Imgproc.FONT_HERSHEY_SIMPLEX, 0.5, new Scalar(0, 255, 0), 2);
        }

        String outputPath = "C:\\Users\\Fiona\\IdeaProjects\\ocvfx\\src\\main\\java\\ocv\\" + args[0].substring(args[0].lastIndexOf("\\") + 1, args[0].lastIndexOf(".")) + "_face.jpg";
        Imgcodecs.imwrite(outputPath, image);

        System.out.println("Face Detected. Output saved as: " + outputPath);
    }
}
