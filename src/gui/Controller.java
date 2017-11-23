package gui;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Controller {

    @FXML
    Pane scene;
    @FXML
    ImageView bg;
    @FXML
    ImageView table;
    @FXML
    Button bR;
    @FXML
    Button bC;
    @FXML
    Button bF;
    @FXML
    Slider slider;
    @FXML
    ImageView slidOr;
    @FXML
    ImageView slidOl;
    @FXML
    ImageView deck;
    @FXML
    ImageView sb;
    @FXML
    ImageView bb;
    ArrayList<Point2D> blindcoords = new ArrayList<>();
    ArrayList<Point2D> pcoords = new ArrayList<>();
    ArrayList<ImageView> pbs = new ArrayList<>();

    HashSet<Card> pcards = new HashSet<>();

    double w, h;
    int bbat = 0, sbat = 5;

    private DoubleProperty fontSize = new SimpleDoubleProperty(10);

    @FXML
    public void initialize() {
        init();
        scene.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        table.autosize();
        scene.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> resize(newSceneWidth, scene.getHeight(), oldSceneWidth, scene.getHeight()));
        scene.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) -> resize(scene.getWidth(), newSceneHeight, scene.getWidth(), oldSceneHeight));
        table.setFitWidth(100);
        table.setFitHeight(100);
        scene.setCache(true);
        addCards();
        fontSize.bind(scene.widthProperty().add(scene.heightProperty()).divide(150));
        bF.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));
        bR.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));
        bC.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));
        slider.setMin(1);
        slider.setMax(1000);
        resize(scene.getWidth(), scene.getHeight(), scene.getWidth(), scene.getHeight());

        //scene.getChildren().addAll(cc);
        bgmusic();
    }

    private void bgmusic() {
        String path = "C:\\Users\\egidi\\IdeaProjects\\javafxTest\\src\\assets\\music\\Slavonic, String Quartet No. 10, 2nd Mvt. - elegant, sublime, Dvořák (192kbit_AAC).m4a";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.play();
    }

    private void init() {
        blindcoords.add(new Point2D(0.5, 0.7)); //Player this
        blindcoords.add(new Point2D(0.7, 0.6)); //right 1
        blindcoords.add(new Point2D(0.7, 0.3)); //right 2
        blindcoords.add(new Point2D(0.5, 0.2)); //right 3
        blindcoords.add(new Point2D(0.3, 0.3)); //right 4
        blindcoords.add(new Point2D(0.3, 0.6)); //right 5
        pcoords.add(new Point2D(0.4790625, 0.7537037037037037)); //Player this
        pcoords.add(new Point2D(0.7869791666666667, 0.6166666666666667)); //right 1
        pcoords.add(new Point2D(0.7869791666666667, 0.1962962962962963)); //right 2
        pcoords.add(new Point2D(0.4790625, 0.0712962962962963)); //right 3
        pcoords.add(new Point2D(0.14125, 0.1962962962962963)); //right 4
        pcoords.add(new Point2D(0.14125, 0.6166666666666667)); //right 5
        for (Point2D p : pcoords) {
            ImageView c = new ImageView();
            c.setImage(new Image("assets/img/pb.png"));
            c.setEffect(new DropShadow());
            pbs.add(c);
            scene.getChildren().addAll(c);
        }
    }

    private void addCards() {
        Card c = new Card("assets/img/qp.png");
        c.setLayoutY(deck.getLayoutY());
        c.setLayoutX(deck.getLayoutX());
        c.setEffect(new DropShadow());
        c.setOnMouseEntered(event -> c.show());
        c.setPreserveRatio(false);
        scene.getChildren().addAll(c);
        pcards.add(c);
    }

    private void resize(Number width, Number height, Number owidth, Number oheight) {
        w = width.doubleValue();
        h = height.doubleValue();
        bg.setFitWidth((Double) width);
        bg.setFitHeight((Double) height);
        table.setX(0.1 * width.doubleValue());
        table.setY(0.0 * height.doubleValue());
        table.setFitWidth(0.8 * width.doubleValue());
        table.setFitHeight(0.9 * height.doubleValue());

        bR.setPrefSize(width.doubleValue() * 0.05, height.doubleValue() * 0.05);
        bF.setPrefSize(width.doubleValue() * 0.05, height.doubleValue() * 0.05);
        bC.setPrefSize(width.doubleValue() * 0.05, height.doubleValue() * 0.05);

        bR.setLayoutY(height.doubleValue() - bR.getHeight() - (bR.getHeight() * 0.2));
        bF.setLayoutY(height.doubleValue() - bF.getHeight() - (bF.getHeight() * 0.2));
        bC.setLayoutY(height.doubleValue() - bC.getHeight() - (bC.getHeight() * 0.2));

        bR.setLayoutX(width.doubleValue() - (1 * bR.getWidth()) -  1 * (bR.getWidth() * 0.2));
        bF.setLayoutX(width.doubleValue() - (2 * bF.getWidth()) -  2 * (bF.getWidth() * 0.2));
        bC.setLayoutX(width.doubleValue() - (3 * bC.getWidth()) -  3 * (bC.getWidth() * 0.2));

        slider.setPrefSize(width.doubleValue() * 0.5, height.doubleValue() * 0.005);
        slider.setLayoutX(width.doubleValue() * 0.1);
        slider.setLayoutY(height.doubleValue() - bR.getHeight() - 10);

        slidOl.setFitHeight(height.doubleValue() * 0.09);
        slidOl.setFitWidth(width.doubleValue() * 0.09);
        slidOr.setFitHeight(height.doubleValue() * 0.09);
        slidOr.setFitWidth(width.doubleValue() * 0.09);

        slidOl.setLayoutY(slider.getLayoutY() - slidOl.getFitHeight() * 0.5);
        slidOr.setLayoutY(slider.getLayoutY() - slidOr.getFitHeight() * 0.5);

        slidOl.setLayoutX(slider.getLayoutX() - slidOl.getFitWidth());
        slidOr.setLayoutX(slider.getLayoutX() + slider.getWidth());

        deck.setFitHeight(height.doubleValue() * 0.1);
        deck.setFitWidth(width.doubleValue() * 0.04);
        deck.setLayoutX(table.getX() + width.doubleValue() * 0.2);
        deck.setLayoutY(table.getY() + height.doubleValue() * 0.2);

        bb.setFitHeight(height.doubleValue() * 0.05);
        bb.setFitWidth(width.doubleValue() * 0.029);
        sb.setFitHeight(height.doubleValue() * 0.05);
        sb.setFitWidth(width.doubleValue() * 0.029);

        bb.setLayoutX(blindcoords.get(bbat).getX() * width.doubleValue());
        bb.setLayoutY(blindcoords.get(bbat).getY() * height.doubleValue());
        sb.setLayoutX(blindcoords.get(sbat).getX() * width.doubleValue());
        sb.setLayoutY(blindcoords.get(sbat).getY() * height.doubleValue());

        for (Card c : pcards) {
            c.setFitHeight(height.doubleValue() * 0.1);
            c.setFitWidth(width.doubleValue() * 0.04);
            c.setLayoutX(table.getX() + width.doubleValue() * 0.2);
            c.setLayoutY(table.getY() + height.doubleValue() * 0.2);
        }

        for (int i = 0; i < pcoords.size(); i++) {
            pbs.get(i).setLayoutX(pcoords.get(i).getX() * width.doubleValue());
            pbs.get(i).setLayoutY(pcoords.get(i).getY() * height.doubleValue());
            pbs.get(i).setFitHeight(height.doubleValue() * 0.12);
            pbs.get(i).setFitWidth(width.doubleValue() * 0.14);
        }
        handleSomeOtherStuff();
    }

    private void handleSomeOtherStuff() {
        StackPane thumb = (StackPane) slider.lookup(".thumb");
        if (thumb == null) return;
        Label label = new Label();
        label.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));
        //label.styleProperty().bind(Bindings.concat("-fx-font-size: ", (fontSize).asString(), ";"));
        label.textProperty().bind(slider.valueProperty().asString("%.1f$"));
        thumb.getChildren().add(label);
    }

    @FXML
    private void raise() {
        moveBlinds();
    }

    private void moveBlinds() {
        bbat++;
        if (bbat > 5) bbat = 0;
        sbat++;
        if (sbat > 5) sbat = 0;
        Card.animate(bb, w, h, "src/assets/makros/movTo" + bbat + ".hypermakro");
        Card.animate(sb, w, h, "src/assets/makros/movTo" + sbat + ".hypermakro");
    }

}
