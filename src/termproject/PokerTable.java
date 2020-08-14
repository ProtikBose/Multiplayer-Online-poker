/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package termproject;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import static javafx.scene.layout.Priority.min;

import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Screen;

import javafx.stage.Stage;
import termproject.Card;
import termproject.Player;

/**
 *
 * @author PRANTO
 */
public class PokerTable extends Application {

    static int flag = 0;
    static Stage stage2;
    public static Stage stage;
    public static int ok = 0;
    public static Player p;

    public static void choice() {
        Group root2 = new Group();
        Scene scene2 = new Scene(root2);
        if (ok == 2) {
            System.out.println("h");
            Rectangle rect = new Rectangle(150, 30);
            final Label label = new Label("Hello");
            label.setStyle("-fx-font: 25 arial;");
            label.setLayoutX(40);

            rect.setStroke(Color.BLUE);
            rect.setStrokeWidth(3);
            rect.setFill(Color.WHITE);

            // final String[] greetings = new String[] { "A", "B", "C" };
            final ChoiceBox<String> cb = new ChoiceBox<String>(
                    FXCollections.observableArrayList("MATCH", "CHECK", "RAISE", "FOLD"));

            /* cb.getSelectionModel().selectedIndexProperty()
        .addListener(new ChangeListener<Number>() {
          
          @Override
          public void changed(ObservableValue ov, Number value, Number new_value) {
            label.setText(greetings[new_value.intValue()]);
          }

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });*/
            cb.setTooltip(new Tooltip("Select the language"));
            cb.setValue("English");
            HBox hb = new HBox();
            hb.getChildren().addAll(cb, label);
            hb.setSpacing(30);
            hb.setAlignment(Pos.CENTER);
            hb.setPadding(new Insets(10, 0, 0, 10));

            ((Group) scene2.getRoot()).getChildren().add(hb);
            stage.setScene(scene2);
            stage.show();
        }
    }

    public static void playScene() {
//        new AnimationTimer(){
//            @Override
//            public void handle(long now) {
//                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//            
//        }

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

        //game on
        // playimageView.fitWidthProperty().bind(stage.widthProperty());
        //playimageView.fitHeightProperty().bind(stage.heightProperty());
        if (ok == 0) {
            stage.setScene(scene1);
            stage.show();
        }
        // stage.setFullScreen(true);
        //stage.show();
        //Player p = new Player();
        System.out.println(p.type);
        String s1; //=new String(); 
        String s2;
//                int q = 0,r = 0;
//                while (q < 1000000000){
//                    while(r < 1000000000){
//                    
//                    }
//                }

//                try {
//                    stage.wait(2000);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(PokerTable.class.getName()).log(Level.SEVERE, null, ex);
//                }
        //stage.notify();
        // Timer t=new Timer();
        //Z t.set
        if (ok == 1) {
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
            stage.show();
        }

        //p.display();
        //event.consume();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void updateScene() {
        Group root = new Group();
        Scene scene = new Scene(root);

        scene.setFill(Color.TRANSPARENT);
        // stage.setFullScreen(true);
        /*  String musicFile = "Casino.mp3";     // For example

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();*/

        Image image = new Image("file:giphy.gif");

        // simple displays ImageView the image as is
        ImageView iv1 = new ImageView();
        iv1.setImage(image);

        // root.getChildren().add(iv1);

        /*  Image img=new Image("file:playbutton.png",30,40,false,false);
         ImageView iv2 = new ImageView();
         iv2.setImage(img);
         iv2.setX(30);
         iv2.setY(40);
         
         root.getChildren().add(iv2); */
        // Rectangle2D recf=new Rectangle2D(40,35,110,110);
        //iv2.setViewport(recf);
        //Button playbtn=new Button();
        ImageView playimageView = new ImageView("file:Play.png");

        playimageView.setFitWidth(160);

        playimageView.setFitHeight(140);

        playimageView.setX(40);
        playimageView.setY(170);
        /*  Button btn=new Button("Hello");
        btn.setGraphic(playimageView);
        // btn.setPrefWidth(20);
        //btn.setPrefHeight(40);
        btn.setLayoutX(20);
        btn.setLayoutY(100);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });*/

//        playbtn.setGraphic(imageView);
//        playbtn.setLayoutX(20);
//        playbtn.setLayoutY(150);
//         root.getChildren().add(imageView);
        playimageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {

                playScene();
            }
        });

        /* playimageView.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                Image img2 = new Image("file:Play 2.png");
                playimageView.setImage(img2);
                playimageView.setFitWidth(600);

                playimageView.setFitHeight(400);

                playimageView.setX(20);
                playimageView.setY(100);
                System.out.println("Tile pressed ");
//event.consume();
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        });*/
        //  root.getChildren().add(playimageView);
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
        rulesimageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                flag = 1;
                Group root2 = new Group();
                Scene scene2 = new Scene(root2);
                Image img3 = new Image("file:download.png");
                rulesimageView.setImage(img3);
                rulesimageView.setFitWidth(1200);

                rulesimageView.setFitHeight(700);

                rulesimageView.setX(0);
                rulesimageView.setY(0);
                root2.getChildren().add(rulesimageView);

                Image back = new Image("file:back.png");
                ImageView backiv = new ImageView(back);
                backiv.setImage(back);
                backiv.setX(1200);
                backiv.setY(0);
                backiv.setFitWidth(100);

                backiv.setFitHeight(30);
                root2.getChildren().add(backiv);
                stage.setScene(scene2);
                // stage.setFullScreen(true);
                stage.show();
                backiv.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        ImageView rulesimageView = new ImageView("file:Rule.png");
                        rulesimageView.setFitWidth(160);
                        rulesimageView.setFitHeight(140);
                        rulesimageView.setX(40);
                        rulesimageView.setY(350);
                        root.getChildren().add(rulesimageView);
                        stage.setScene(scene);
                        //stage.setFullScreen(true);
                        stage.show();
                    }
                });
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

        root.getChildren().add(iv1);
        root.getChildren().add(playimageView);
        root.getChildren().add(creditsimageView);
        root.getChildren().add(rulesimageView);

//        ImageView creditsimageView = new ImageView("file:Credits.png");
//        creditsimageView.setFitWidth(600);
//        creditsimageView.setFitHeight(400);
//        creditsimageView.setX(20);
//        creditsimageView.setY(300);
//        
//        root.getChildren().add(creditsimageView);
//         HBox box = new HBox();
//         box.getChildren().add(iv2);
//         box.getChildren().add(iv1);
//         
//         root.getChildren().add(box);
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

    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        p = new Player();
        updateScene();
        choice();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

};
