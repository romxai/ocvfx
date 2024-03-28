package ocv;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

public class DetectFace {

    static JFrame frame;
    static JLabel label;
    static ImageIcon icon;

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        CascadeClassifier faceClassifier = new CascadeClassifier("D:/Users/Fiona/Downloads/opencv/build/etc/haarcascades/haarcascade_frontalface_default.xml");
        CascadeClassifier eyeClassifier = new CascadeClassifier("D:/Users/Fiona/Downloads/opencv/build/etc/haarcascades/haarcascade_eye.xml");

        VideoCapture videoDevice = new VideoCapture();
        videoDevice.open(0);
        if (videoDevice.isOpened()) {
            while (true) {
                Mat frameCapture = new Mat();
                videoDevice.read(frameCapture);

                MatOfRect faces = new MatOfRect();
                faceClassifier.detectMultiScale(frameCapture, faces);
                for (Rect rect : faces.toArray()) {
                    Imgproc.putText(frameCapture, "Face", new Point(rect.x,rect.y-5), 1, 2, new Scalar(0,0,255));
                    Imgproc.rectangle(frameCapture, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                            new Scalar(0, 100, 0),3);
                }

                MatOfRect eyes = new MatOfRect();
                eyeClassifier.detectMultiScale(frameCapture, eyes);
                for (Rect rect : eyes.toArray()) {
                    Imgproc.putText(frameCapture, "Eye", new Point(rect.x,rect.y-5), 1, 2, new Scalar(0,0,255));
                    Imgproc.rectangle(frameCapture, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                            new Scalar(200, 200, 100),2);
                }

                displayImage(convertMatToImage(frameCapture));
                System.out.println(String.format("%s face(s) detected, %s eye(s) detected.", faces.toArray().length,eyes.toArray().length));
            }
        } else {
            System.out.println("Failed to connect to video device.");
            return;
        }
    }

    private static BufferedImage convertMatToImage(Mat mat) {
        MatOfByte byteMat = new MatOfByte();
        Imgcodecs.imencode(".jpg", mat, byteMat);
        byte[] byteArray = byteMat.toArray();
        BufferedImage image = null;
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            image = ImageIO.read(in);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return image;
    }

    public static void prepareWindow() {
        frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(700, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void displayImage(Image img) {
        if (frame == null)
            prepareWindow();
        if (label != null)
            frame.remove(label);
        icon = new ImageIcon(img);
        label = new JLabel();
        label.setIcon(icon);
        frame.add(label);
        frame.revalidate();
    }
}
