/*
 * Author: Ahmad Sami Abu Shawarib
 * Student #: 251149713
 * Date:  Feb. 10, 2022
 * Description: the CarRentalAgent object class to handle info about CarRentalAgents and Cars in the inventory
 */

package com.example.hydraheadsgame;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class HydraHeadsController implements Initializable {
    @FXML private Slider slider;//the variable slider that sets the first heads size
    @FXML private Button play;//the game start button
    @FXML private AnchorPane spawnGrounds;//the pane that heads spawn on
    private int score = 0;//running score
    private final TreeMap<String, ImageViewExtra> heads = new TreeMap<>();//treemap to track the current operational heads with a custom key
    private final TreeMap<String, Integer> positions = new TreeMap<>();//treemap to track the current operational space used up heads with a custom key
    private ImageViewExtra head;//the variable to temporarily store the head to be spawned
    private int numID = 1;//a unique number identifier that ticks for each head spawned, part of the custom key made for the treemaps
    private final Random randomNumber = new Random();//a random number generator

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //resets and initializes the game; gets called when the app is run or when the reset button is pressed

        slider.setDisable(false);//makes slider usable
        play.setDisable(false);//makes play button usable
        slider.setValue(4);//default requested to size 4
        heads.clear();//resets previous spawned heads
        positions.clear();//resets previous spawned heads' positions
        spawnGrounds.getChildren().removeAll(spawnGrounds.getChildren());//removes all potential nodes currently operational in the pane
        numID = 1;//resets the id count
        score = 0;//resets score count
    }

    public void gameStart() {
        slider.setDisable(true);//makes slider unusable
        play.setDisable(true);//makes play button unusable
        spawnHeads((int) slider.getValue());//creates the first head based on the slider's value
    }

    public void gameReset() {
        initialize(null, null);//resets the game; gets called when the reset button is pressed
    }

    private void spawnHeads(int size){
        //creates imageview "heads" based on the arg int size

        int posX, posY;//the randomize x and y positions of the spawned heads

        do{
            posX = randomNumber.nextInt(19);//random x coefficient between 0-18
            posY = randomNumber.nextInt(19);//random y coefficient between 0-18
        }while(positions.containsValue(posX *100+ posY));//checks if the combined x and y positions are currently in use


        switch (size) {
            case 1, 2, 3, 4, 5, 6 -> {
                head = new ImageViewExtra(numID++);//creates a new ImageView head with the numID, then increments it
                head.setId(size+"."+head.getNumID());//creates a custom key out of the current size and rolling numID
                head.setImage(new Image("file:src/main/resources/com/example/hydraheadsgame/HeadSize" + size + ".png"));//retrieves the sprite based on the current size
            }//instantiate a new ImageViewExtra to create a new head

            default -> {
                System.out.println("This Sprite Size Doesn't Exist: Cannot Create A New ImageViewExtra Head Of Size " + size);
                System.exit(0);
            }//in-case size is outside the range of 1-6, prints an error message and ends the app
        }

        head.setFitHeight(40);//set height to 40 px
        head.setFitWidth(40);//set width to 40 px
        head.setX(posX *40);//set the x position on increments of 40
        head.setY(posY *40);//set the y position on increments of 40
        head.setOnMouseClicked(this::killHead);//adds a mouse listener to the imageview calling killHead()
        heads.put(head.getId(), head);//adds the head to collection of current operational heads
        positions.put(head.getId(), posX *100+ posY);//adds a unique value representing the current operational heads' positions
        spawnGrounds.getChildren().add(head);//adds the sprite to the pane
    }

    private void killHead(MouseEvent event){
        String id = event.getPickResult().getIntersectedNode().getId();//gets the fx:id of the head that was clicked on
        score++;//increments score
        int randomCount = randomNumber.nextInt(2)+2;//chooses a random integer between 2-3 corresponding to the number of heads to be spawned
        int newSize = (int)Double.parseDouble(heads.get(id).getId());//extracts the size of the clicked head from fx:id
        ImageViewExtra lastHead = heads.remove(id);//stores the latest head to be clicked on and removes it from the collection of current operational heads
        spawnGrounds.getChildren().remove(lastHead);//removes the sprite representing the head that was clicked on
        positions.remove(id);//removes the clicked on head's unique position value from the collection of current operational heads' positions
        switch (newSize){
            case 2,3,4,5,6 -> {
                newSize--;
                for (int count = 1; count <= randomCount; count++)
                    spawnHeads(newSize);
            }//spawns 2-3 new heads

            case 1 ->{}//do nothing

            default -> {
                System.out.println("This Sprite Size Doesn't Exist: Cannot Delete A New ImageViewExtra Head Of Size " + (newSize-1));
                System.exit(0);
            }//in-case newSize is outside the range of 1-6, prints an error message and ends the app
        }


        if(spawnGrounds.getChildren().size() == 0 && (int)Double.parseDouble(lastHead.getId()) == 1)
            gameEnd();//ends game when there are no more nodes in the pane and the last head's size is 1
    }

    private void gameEnd(){
        Label endScore = new Label();
        endScore.setText("Good Job! - You Have Defeated The Mighty Hydra\nYou Hacked Through " + score + " Heads!");//victory message displaying the number of heads that were clicked on
        endScore.setStyle("-fx-text-fill: red; -fx-font-size: 24px; -fx-text-alignment: center;");//css styles
        endScore.setLayoutX(110);//sets x offset to pane
        endScore.setLayoutY(300);//sets y offset to pane
        spawnGrounds.getChildren().add(endScore);//displays the label on the pane
    }

    private static class ImageViewExtra extends ImageView {
        private final int numID;//unique number corresponding to the order the head was created in

        public ImageViewExtra(int numID){
            super();//invokes ImageView
            this.numID = numID;//sets numID
        }

        public int getNumID(){//gets numID
            return numID;
        }
    }

}
