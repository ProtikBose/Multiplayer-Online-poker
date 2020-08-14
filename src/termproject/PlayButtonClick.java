/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package termproject;

/**
 *
 * @author PRANTO
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package gamemenu;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import static termproject.PokerTable2.str;

/**
 *
 * @author PRANTO
 */
public class PlayButtonClick extends Application {

    Rectangle rect = new Rectangle(150, 30);
    final Label label = new Label("Hello");

    @Override
    public void start(Stage stage) throws Exception {

        Group root = new Group();
        Scene scene = new Scene(root);
        stage.fullScreenProperty();
        scene.setFill(Color.TRANSPARENT);

        Image background = new Image("file:background.jpg");
        ImageView ivb = new ImageView();
        ivb.setImage(background);
        root.getChildren().add(ivb);
        ivb.fitWidthProperty().bind(stage.widthProperty());
        ivb.fitHeightProperty().bind(stage.heightProperty());

        Image pokertable = new Image("file:pokertable.jpg");

        ImageView ivp = new ImageView();
        ivp.setImage(pokertable);
        ivp.setFitWidth(600);

        ivp.setFitHeight(400);
        ivp.setLayoutX(400);
        ivp.setLayoutY(150);
        root.getChildren().add(ivp);

        //player1
        Image player1 = new Image("file:player1.jpg");

        ImageView ivp1 = new ImageView();
        ivp1.setImage(player1);
        ivp1.setLayoutX(442);
        ivp1.setLayoutY(169);
        ivp1.setFitWidth(80);
        ivp1.setFitHeight(60);
        root.getChildren().add(ivp1);

        //player2
        Image player2 = new Image("file:player1.jpg");

        ImageView ivp2 = new ImageView();
        ivp2.setImage(player2);
        ivp2.setLayoutX(400);
        ivp2.setLayoutY(431);
        ivp2.setFitWidth(80);
        ivp2.setFitHeight(60);
        root.getChildren().add(ivp2);

        //player5
        Image player5 = new Image("file:player1.jpg");

        ImageView ivp5 = new ImageView();
        ivp5.setImage(player5);
        ivp5.setLayoutX(885);
        ivp5.setLayoutY(169);
        ivp5.setFitWidth(80);
        ivp5.setFitHeight(60);
        root.getChildren().add(ivp5);

        /* final Text text1 = new Text(25, 25, "java2s.com");
        text1.setFill(Color.CHOCOLATE);
        text1.setFont(Font.font(java.awt.Font., 25));
        root.getChildren().add(text1);*/
        scene.setOnMouseClicked(mouseHandler);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();

        Rectangle rect = new Rectangle(150, 30);
        final Label label = new Label("Hello");
        label.setStyle("-fx-font: 25 arial;");
        label.setLayoutX(40);

        rect.setStroke(Color.BLUE);
        rect.setStrokeWidth(3);
        rect.setFill(Color.WHITE);

        // final String[] greetings = new String[] { "A", "B", "C" };
        final ChoiceBox<String> cb = new ChoiceBox<String>(
                FXCollections.observableArrayList("MATCH", "RAISE", "FOLD"));
//        cb.setTooltip(new Tooltip("Select the language"));
//        cb.setValue("English");
        HBox hb = new HBox();
        hb.getChildren().addAll(cb, label);
        hb.setSpacing(30);
        hb.setAlignment(Pos.CENTER);
        hb.setPadding(new Insets(10, 0, 0, 10));

        ((Group) scene.getRoot()).getChildren().add(hb);
        //String str = new String();
        cb.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                str = cb.getSelectionModel().getSelectedItem();
                System.out.println(str);
            }

        });

        // final String[] greetings = new String[] { "A", "B", "C" };
       TextInputDialog dialog = new TextInputDialog("walter");
dialog.setTitle("Text Input Dialog");
dialog.setHeaderText("Look, a Text Input Dialog");
dialog.setContentText("Please enter your name:");

// Traditional way to get the response value.
Optional<String> result = dialog.showAndWait();
if (result.isPresent()){
    System.out.println("Your name: " + result.get());
}


        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent mouseEvent) {
            System.out.println(mouseEvent.getEventType() + "\n"
                    + "X : Y : " + mouseEvent.getX() + " : " + mouseEvent.getY() + "\n"
            );

        }

    };

}
