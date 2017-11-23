package gui;
import javafx.animation.AnimationTimer;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Card extends ImageView {
    Image back = new Image("assets/img/cardBack.png");
    Image front;

    static Point recSize;
    int row = 0;

    boolean showing;

    public Card(String img) {
        front = new Image(img);
        this.setImage(back);

    }

    static void animate(Node n, double w, double h, String makro) {
        makro = Paths.get(makro).toAbsolutePath().toString();
        ArrayList<Point> p = fileToData(fileRead(makro));
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (p.isEmpty()) {
                    stop();
                    return;
                }
                n.setLayoutX((p.get(0).getX() / recSize.getX()) * w);
                //(p.get(0).getX() / recSize.getX()) *
                n.setLayoutY((p.get(0).getY() / recSize.getY()) * h);
                p.remove(0);
            }
        }.start();
    }

    void animate(double w, double h, String makro) {
        animate(this, w, h, makro);
    }

    void show() {
        if (showing) return;
        BufferedImage bFront = SwingFXUtils.fromFXImage(front, null);
        BufferedImage bHind = SwingFXUtils.fromFXImage(back, null);

        AnimationTimer b = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (int i = 0; i < bHind.getWidth(); i++) {
                    bHind.setRGB(i, row, SwingFXUtils.fromFXImage(back, null).getRGB(i, row));
                    setImage(SwingFXUtils.toFXImage(bHind, null));
                }
                if (row > 0) row--;
                else {
                    stop();
                    showing = false;
                }
            }
        };

        AnimationTimer a = new AnimationTimer() {
            @Override
            public void handle(long now) {
                showing = true;
                for (int i = 0; i < bHind.getWidth(); i++) {
                    bHind.setRGB(i, row, bFront.getRGB(i, row));
                    setImage(SwingFXUtils.toFXImage(bHind, null));
                }
                if (row < 75) row++;
                else {
                    stop();
                    b.start();
                }
            }
        };
        a.start();
    }

    public static ArrayList<Point> fileToData(String[] file) {
        ArrayList<Point> points = new ArrayList<>();
        recSize = new Point((int) Double.parseDouble(file[0].substring(0, file[0].indexOf("|"))), (int) Double.parseDouble(file[0].substring(file[0].indexOf("|") + 1)));
        file[0] = "";
        file[1] = "";
        for (String s : file) {
            if (s.length() == 0) continue;
            ArrayList<Integer> al = new ArrayList<>();
            for (String ss : s.substring(s.indexOf("/") + 1).split(",")) {
                try {
                    al.add(Integer.parseInt(ss));
                } catch (Exception ignored) {}
            }
            points.add(new Point(Integer.parseInt(s.substring(0, s.indexOf("|"))), Integer.parseInt(s.substring(s.indexOf("|") + 1, s.indexOf(":")))));
        }
        return points;
    }

    public static String[] fileRead(String file) {
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            int lines = 0;
            while (br.readLine() != null) {
                lines++;
            }
            br.close();
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String[] storeInto = new String[lines];
            String current;
            int i=0;
            while ((current = br.readLine()) != null && i<storeInto.length) {
                storeInto[i]=current;
                i++;
            }
            return storeInto;
        }
        catch (IOException e) {
            System.err.println("Read: Le Errör in findings of file!");
            return null;
        }
    }
}
