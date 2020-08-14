/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package termproject;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static termproject.Player.balance;
import static termproject.Player.msg;
import static termproject.Player.oos;
import static termproject.Player.playerID;
import static termproject.Player.prebet;
import static termproject.PokerTable.Display;
import static termproject.PokerTable.p;
import static termproject.PokerTable.playScene;
import static termproject.PokerTable.stage;

/**
 *
 * @author PRANTO
 */
public class PokerTable2 extends Application {

    public static Stage stage;
    public static Player p;
    public static String str;

    public static void Scene03() {
        Image image = new Image("file:background.png");
        Group root1 = new Group();
        Scene scene1 = new Scene(root1);
        // simple displays ImageView the image as is
        // ImageView iv1 = new ImageView();
        ImageView playimageView = new ImageView();
        playimageView.setImage(image);
        playimageView.setX(0);
        playimageView.setY(0);
        playimageView.setFitWidth(1368);

        playimageView.setFitHeight(768);
        root1.getChildren().add(playimageView);
        Image pokertable = new Image("file:Table.png");

        ImageView ivp = new ImageView();
        ivp.setImage(pokertable);
        ivp.setFitWidth(700);

        ivp.setFitHeight(450);
        ivp.setLayoutX(400);
        ivp.setLayoutY(50);
        root1.getChildren().add(ivp);

        String s1; //=new String(); 
        String s2;

        Text t = new Text("MY CARD");
        t.setX(128);
        t.setY(598);
        t.setLayoutX(212);
        t.setLayoutY(68);
        root1.getChildren().add(t);
        s1 = Display(p.hand[0]);
        System.out.println(s1);
        Image playcard1 = new Image("file:" + s1 + ".png");
        ImageView ivplaycard1 = new ImageView();
        ivplaycard1.setImage(playcard1);
        ivplaycard1.setFitHeight(128);
        ivplaycard1.setFitWidth(115);
        ivplaycard1.setLayoutX(528);
        ivplaycard1.setLayoutY(562);
        root1.getChildren().add(ivplaycard1);
        s2 = Display(p.hand[1]);
        Image playcard2 = new Image("file:" + s2 + ".png");
        ImageView ivplaycard2 = new ImageView();
        ivplaycard2.setImage(playcard2);
        ivplaycard2.setFitHeight(128);
        ivplaycard2.setFitWidth(115);
        ivplaycard2.setLayoutX(688);
        ivplaycard2.setLayoutY(562);
        root1.getChildren().add(ivplaycard2);

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
        cb.setTooltip(new Tooltip("Select the language"));
        cb.setValue("English");
        HBox hb = new HBox();
        hb.getChildren().addAll(cb, label);
        hb.setSpacing(30);
        hb.setAlignment(Pos.CENTER);
        hb.setPadding(new Insets(10, 0, 0, 10));

        ((Group) scene1.getRoot()).getChildren().add(hb);
        //String str = new String();
        cb.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                str = cb.getSelectionModel().getSelectedItem();

                if (str.equals("MATCH")) {
                    p.balance = p.balance - (p.msg.Bet - p.prebet);
                    p.msg.pot = p.msg.pot + p.msg.Bet - p.prebet;
                    p.prebet = p.msg.Bet;
                    p.msg.message = "Opponent Matched.";
                    //CommunicationMsg temp = MakeNewMsg(msg);
                    //System.out.println("******In choice 1");
                    try {
                        oos.writeUnshared(msg);
                    } catch (IOException ex) {
                        Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    // code of showing balance
                } else if (str.equals("RAISE")) {
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("Raise");
                    dialog.setHeaderText("Set Bet");
                    dialog.setContentText("Please enter your raise amount :");
                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()) {
                        p.msg.Bet = Integer.parseInt(result.get());
                    }
                    p.msg.pot = p.msg.pot + p.msg.Bet;
                    p.balance = p.balance - p.msg.Bet;
                    p.prebet = p.msg.Bet;
                    p.msg.balance = p.balance;
                    p.msg.bidderID = p.playerID;
                    System.out.println("******In choice 2");
                    msg.message = "Opponent raised bet amount to " + msg.Bet + "$";
                    System.out.println(msg.message);
                    //CommunicationMsg temp = MakeNewMsg(msg);
                    //System.out.println(temp.message);
                    try {
                        oos.writeUnshared(p.msg);
                    } catch (IOException ex) {
                        Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (str.equals("FOLD")) {
                    p.msg.message = "Opponent Folded. ";
                    p.msg.foldID = p.playerID;

                    try {
                        oos.writeUnshared(p.msg);
                    } catch (IOException ex) {
                        Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }

        });

        stage.setScene(scene1);
        stage.show();
    }

    public static void Scene02() {
        Image image = new Image("file:background.png");
        Group root1 = new Group();
        Scene scene1 = new Scene(root1);
        // simple displays ImageView the image as is
        // ImageView iv1 = new ImageView();
        ImageView playimageView = new ImageView();
        playimageView.setImage(image);
        playimageView.setX(0);
        playimageView.setY(0);
        playimageView.setFitWidth(1368);

        playimageView.setFitHeight(768);
        root1.getChildren().add(playimageView);
        Image pokertable = new Image("file:Table.png");

        ImageView ivp = new ImageView();
        ivp.setImage(pokertable);
        ivp.setFitWidth(700);

        ivp.setFitHeight(450);
        ivp.setLayoutX(400);
        ivp.setLayoutY(50);
        root1.getChildren().add(ivp);

        String s1; //=new String(); 
        String s2;

        Text t = new Text("MY CARD");
        t.setX(128);
        t.setY(598);
        t.setLayoutX(212);
        t.setLayoutY(68);
        root1.getChildren().add(t);
        s1 = Display(p.hand[0]);
        System.out.println(s1);
        Image playcard1 = new Image("file:" + s1 + ".png");
        ImageView ivplaycard1 = new ImageView();
        ivplaycard1.setImage(playcard1);
        ivplaycard1.setFitHeight(128);
        ivplaycard1.setFitWidth(115);
        ivplaycard1.setLayoutX(528);
        ivplaycard1.setLayoutY(562);
        root1.getChildren().add(ivplaycard1);
        s2 = Display(p.hand[1]);
        Image playcard2 = new Image("file:" + s2 + ".png");
        ImageView ivplaycard2 = new ImageView();
        ivplaycard2.setImage(playcard2);
        ivplaycard2.setFitHeight(128);
        ivplaycard2.setFitWidth(115);
        ivplaycard2.setLayoutX(688);
        ivplaycard2.setLayoutY(562);
        root1.getChildren().add(ivplaycard2);

        stage.setScene(scene1);

    }

    public static void menu() {
        Group root = new Group();
        Scene scene = new Scene(root);

        scene.setFill(Color.TRANSPARENT);
        Image image = new Image("file:giphy.gif");

        // simple displays ImageView the image as is
        ImageView iv1 = new ImageView();
        iv1.setImage(image);
        ImageView playimageView = new ImageView("file:Play.png");

        playimageView.setFitWidth(160);

        playimageView.setFitHeight(140);

        playimageView.setX(40);
        playimageView.setY(170);
        playimageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {

                Scene02();
                if (p.msg.type == 2) {
                    if (p.prebet != p.msg.Bet) {
                        Scene03();
                    }
                }
            }
        });

        ImageView creditsimageView = new ImageView("file:Credit.png");
        creditsimageView.setFitWidth(160);
        creditsimageView.setFitHeight(140);
        creditsimageView.setX(40);
        creditsimageView.setY(530);

        // root.getChildren().add(creditsimageView);
        ImageView rulesimageView = new ImageView("file:Rule.png");
        rulesimageView.setFitWidth(160);
        rulesimageView.setFitHeight(140);
        rulesimageView.setX(40);
        rulesimageView.setY(350);
        root.getChildren().add(iv1);
        root.getChildren().add(playimageView);
        root.getChildren().add(creditsimageView);
        root.getChildren().add(rulesimageView);
        iv1.fitWidthProperty().bind(stage.widthProperty());
        iv1.fitHeightProperty().bind(stage.heightProperty());
        stage.setTitle("POKER");
        //stage.setWidth(415);
        //stage.setHeight(200);
        // scene.setOnMouseClicked(mouseHandler);
        stage.setScene(scene);
        // stage.sizeToScene();
        stage.show();
    }

    @Override
    public void start(Stage pstage) throws Exception {
        stage = pstage;
        p = new Player();
        menu();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public static String Display(Card card) {
        String strsuit = null, strrank = null, str = null;
        if (card.suit == 1) {
            strsuit = new String("S");
        }
        if (card.suit == 2) {
            strsuit = new String("H");
        }
        if (card.suit == 3) {
            strsuit = new String("D");
        }
        if (card.suit == 4) {
            strsuit = new String("C");
        }
        if (card.rank == 14) {
            strrank = new String("14");
        }
        if (card.rank == 2) {
            strrank = new String("2");
        }
        if (card.rank == 3) {
            strrank = new String("3");
        }
        if (card.rank == 4) {
            strrank = new String("4");
        }
        if (card.rank == 5) {
            strrank = new String("5");
        }
        if (card.rank == 6) {
            strrank = new String("6");
        }
        if (card.rank == 7) {
            strrank = new String("7");
        }
        if (card.rank == 8) {
            strrank = new String("8");
        }
        if (card.rank == 9) {
            strrank = new String("9");
        }
        if (card.rank == 10) {
            strrank = new String("10");
        }
        if (card.rank == 11) {
            strrank = new String("11");
        }
        if (card.rank == 12) {
            strrank = new String("12");
        }
        if (card.rank == 13) {
            strrank = new String("13");
        }

        str = strsuit + strrank;
        return str;
    }

}
