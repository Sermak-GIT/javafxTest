package test;

        import javafx.application.Application;
        import javafx.geometry.Point3D;
        import javafx.scene.DepthTest;
        import javafx.scene.Group;
        import javafx.scene.PerspectiveCamera;
        import javafx.scene.Scene;
        import javafx.scene.image.Image;
        import javafx.scene.input.MouseEvent;
        import javafx.scene.paint.Color;
        import javafx.scene.paint.PhongMaterial;
        import javafx.scene.shape.Cylinder;
        import javafx.scene.transform.Rotate;
        import javafx.scene.transform.Transform;
        import javafx.scene.transform.Translate;
        import javafx.stage.Stage;

public class CoinTest extends Application {
    final Group root = new Group();
    final RotGroup world = new RotGroup();
    final PerspectiveCamera camera = new PerspectiveCamera(true);
    final RotCam cameraXform = new RotCam();
    private static final double CAMERA_INITIAL_DISTANCE = -1000;
    private static final double CAMERA_NEAR_CLIP = 0.1;
    private static final double CAMERA_FAR_CLIP = 10000.0;
    double mousePosX, mousePosY, mouseOldX, mouseOldY, mouseDeltaX, mouseDeltaY;
    double mouseFactorX, mouseFactorY;

    @Override
    public void start(Stage primaryStage) {
        root.getChildren().add(world);
        root.setDepthTest(DepthTest.ENABLE);
        build();
        Scene scene = new Scene(root, 800, 600, true);
        scene.setFill(Color.CORNFLOWERBLUE);
        handleMouse(scene);
        primaryStage.setTitle("CoinTest");
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.setCamera(camera);
        mouseFactorX = 180.0 / scene.getWidth();
        mouseFactorY = 180.0 / scene.getHeight();
    }

    private void build() {
        PhongMaterial yellowStuff = new PhongMaterial();
        yellowStuff.setDiffuseColor(Color.GOLD);
        yellowStuff.setSpecularColor(Color.DEEPSKYBLUE);
        Cylinder coin = new Cylinder(100, 10, 100);
        yellowStuff.setDiffuseMap(new Image(getClass().getResource("diffuse.jpg").toExternalForm()));
        coin.setMaterial(yellowStuff);
        world.getChildren().addAll(coin);

        root.getChildren().add(cameraXform);
        cameraXform.getChildren().add(camera);
        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
    }

    private void handleMouse(Scene scene) {
        scene.setOnMousePressed((MouseEvent me) -> {
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            mouseOldX = me.getSceneX();
            mouseOldY = me.getSceneY();
        });
        scene.setOnMouseDragged((MouseEvent me) -> {
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            mouseDeltaX = (mousePosX - mouseOldX);
            mouseDeltaY = (mousePosY - mouseOldY);
            if (me.isPrimaryButtonDown()) {
                cameraXform.ry(mouseDeltaX * 180.0 / scene.getWidth());
                cameraXform.rx(-mouseDeltaY * 180.0 / scene.getHeight());
            } else if (me.isSecondaryButtonDown()) {
                camera.setTranslateZ(camera.getTranslateZ() + mouseDeltaY);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}

class RotGroup extends Group {
    Translate t = new Translate(0.0, 0.0, 0.0);
    Rotate rx = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
    Rotate ry = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
    Rotate rz = new Rotate(0, 0, 0, 0, Rotate.Z_AXIS);

    public RotGroup() {
        super();
        this.getTransforms().addAll(t, rx, ry, rz);
    }
}

class RotCam extends Group {
    Point3D px = new Point3D(1.0, 0.0, 0.0);
    Point3D py = new Point3D(0.0, 1.0, 0.0);
    Rotate r;
    Transform t = new Rotate();

    public RotCam() {
        super();
    }

    public void rx(double angle) {
        r = new Rotate(angle, px);
        this.t = t.createConcatenation(r);
        this.getTransforms().clear();
        this.getTransforms().addAll(t);
    }

    public void ry(double angle) {
        r = new Rotate(angle, py);
        this.t = t.createConcatenation(r);
        this.getTransforms().clear();
        this.getTransforms().addAll(t);
    }

}