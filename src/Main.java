import javafx.animation.*;
import javafx.animation.Animation;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.net.URL;
import java.util.*;

public class Main extends Application implements Initializable{

    @FXML
    javafx.scene.control.Button station1AddButton;
    @FXML
    javafx.scene.control.Button station2AddButton;
    @FXML
    javafx.scene.control.Button station3AddButton;
    @FXML
    javafx.scene.control.Button station4AddButton;
    @FXML
    javafx.scene.control.Button station5AddButton;
    @FXML
    javafx.scene.control.Button station6AddButton;
    @FXML
    javafx.scene.control.Button station7AddButton;
    
    @FXML
    javafx.scene.control.Spinner station1CapSpin;
    @FXML
    javafx.scene.control.Spinner station2CapSpin;
    @FXML
    javafx.scene.control.Spinner station3CapSpin;
    @FXML
    javafx.scene.control.Spinner station4CapSpin;
    @FXML
    javafx.scene.control.Spinner station5CapSpin;
    @FXML
    javafx.scene.control.Spinner station6CapSpin;
    @FXML
    javafx.scene.control.Spinner station7CapSpin;
    @FXML
    javafx.scene.control.Spinner station8CapSpin;
    @FXML
    javafx.scene.control.TextArea infoFeed;
    @FXML
    Text trainNumber;
    @FXML
    Spinner capacitySpinner;
    @FXML
    Button deploytrain;
    @FXML
    Text station1PassengerText;
    @FXML
    Text station2PassengerText;
    @FXML
    Text station3PassengerText;
    @FXML
    Text station4PassengerText;
    @FXML
    Text station5PassengerText;
    @FXML
    Text station6PassengerText;
    @FXML
    Text station7PassengerText;
    @FXML
    Text station8PassengerText;

    public static Stage primaryStage;
    public static Pane rootPane;
    public static int numOfTrains = 0;
///////////////////////////////////////////////////////////////////////////////////////////////////////////
    public CalTrain c;
    public ArrayList<Station> allStations = new ArrayList<Station>();
    public ArrayList<Train> allTrains = new ArrayList<Train>();

    int totalPassengers = 0;
    int passengersLeft = totalPassengers;	// Passengers left to be picked up
//    int passengersServed = totalPassengers;	// Passengers who haven't arrived to their destination
//    boolean trainsReturned = true;			// If trains haven't returned to Station 0

    /* Program running-related variables */
    int totalPassengersBoarded = 0;
    int totalNumSeats = 0;
    int threadsCompleted = 0;
//    int maxFreeSeats = 5;
    int trainCtr = 0;
//    int passCtr = 10;
//    int maxInsert = 0;
    boolean loadTrainReturned = false;

    /* Temporary Variables */
    int inStationNum, freeSeats;
    Passenger tempRobot;
    Train tempTrain;
    public static int currentTrain = 0;
    public static int totalPassServed = 0;

    public static boolean pause = false;
    public int passLeft = 0;
    public int nextStation = 0;

    public static int decommissioned = 0;
    public Timer timer = new Timer();
//////////////////////////////////////////////////////////////////////////
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        station1AddButton.setOnAction(e -> addPassenger(station1AddButton));
        station2AddButton.setOnAction(e -> addPassenger(station2AddButton));
        station3AddButton.setOnAction(e -> addPassenger(station3AddButton));
        station4AddButton.setOnAction(e -> addPassenger(station4AddButton));
        station5AddButton.setOnAction(e -> addPassenger(station5AddButton));
        station6AddButton.setOnAction(e -> addPassenger(station6AddButton));
        station7AddButton.setOnAction(e -> addPassenger(station7AddButton));

        SpinnerValueFactory<Integer> valueFac = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 40, 1);
        capacitySpinner.setValueFactory(valueFac);

        SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 8, 2);
        station1CapSpin.setValueFactory(valueFactory1);
        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(3, 8, 3);
        station2CapSpin.setValueFactory(valueFactory2);
        SpinnerValueFactory<Integer> valueFactory3 = new SpinnerValueFactory.IntegerSpinnerValueFactory(4, 8, 4);
        station3CapSpin.setValueFactory(valueFactory3);
        SpinnerValueFactory<Integer> valueFactory4 = new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 8, 5);
        station4CapSpin.setValueFactory(valueFactory4);
        SpinnerValueFactory<Integer> valueFactory5 = new SpinnerValueFactory.IntegerSpinnerValueFactory(6, 8, 6);
        station5CapSpin.setValueFactory(valueFactory5);
        SpinnerValueFactory<Integer> valueFactory6 = new SpinnerValueFactory.IntegerSpinnerValueFactory(7, 8, 7);
        station6CapSpin.setValueFactory(valueFactory6);
        SpinnerValueFactory<Integer> valueFactory7 = new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 8, 8);
        station7CapSpin.setValueFactory(valueFactory7);

        this.c = new CalTrain(this);
        createStations();
        update();
    }
    ////////////////////////////////////////////////////////////////
    public Station[] stations = new Station[8];

    public void createStations(){
        passLeft = 0;
        for(int i=0;i<8;i++) {
            allStations.add(c.station_init(i));
            if (i >= 1 && i < 8) {
                allStations.get(i-1).setNextStation(allStations.get(i));
//                allStations.get(i).setLeftStation(allStations.get(i-1));
            }
//            System.out.println(allStations.get(i).displayNextStations());
        }
        System.out.println("[STATIONS INITIALIZED] \n");
        addLog("-----------------------------------------------------\n");
    }

   // public void createPassengers() (DELETED TIHS FUNCTION)

    public void addPassenger(int in, int out, ImageView img){//, boolean direction){
        inStationNum = in;
        totalPassengers++;
        Passenger temp = new Passenger(allStations.get(in-1), c, totalPassengers, allStations.get(out-1));
        temp.setSprite(img);
        System.out.println(temp);
//        if(temp.getDirection() != direction){
//            allStations.get(in).decWaitPass(temp, temp.getDirection());
//            temp.setDirection(direction);
//            allStations.get(in).addPassenger(temp, direction);
//        }
        allStations.get(in-1).addPassWaiting(temp);
        addLog( "[PASSENGER " + totalPassengers +"] Arrived at station " + in + " to station " + out +"\n");
        addLog("-----------------------------------------------------\n");
        threadsCompleted++;
        //updateStationPassengerText();
        try {Thread.sleep(300);} catch(Exception e){}
//        for(int i = 0; i < allStations.size(); i++){
//            int pass = allStations.get(i).getPassWaiting().size();
////            p.createStation(i, pass, allStations.get(i).getWaitPassCount(true)); //add passenger gui

//            System.out.println("Station: " + (i + 1) + " Passengers: " + pass);
//        }
    }

//    public void a() (DELETED THIS FUNCTION)

    public void createTrain(ImageView imgView){
//        freeSeats = 5; //FROM CAPACITY GUI
        freeSeats = Integer.parseInt(capacitySpinner.getValue().toString());
        totalNumSeats += freeSeats;

        loadTrainReturned = false;
        tempTrain = new Train(allStations.get(0), c, freeSeats, trainCtr);
        tempTrain.setSprite(imgView);
        loadTrainReturned = true;
        allTrains.add(tempTrain);
        trainCtr++;

        moveTrainToNextStn(tempTrain, 1);

        allStations.get(0).setCurrTrain(tempTrain); //NEW ADDED BY CHESIE

//        p.train(allTrains.size(), tempTrain.getRiders().size(), tempTrain.getBoardStation().getStationNum());
//        t.createTrain(this); //ADD TRAIN GUI

        addLog( "[NEW TRAIN] Successfully deployed with capacity " + freeSeats + "\n");
        addLog("-----------------------------------------------------\n");
    }

    public void update(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                /*
                 * 1. Check if a train has reached a station
                 * 		a. stop trains behind it
                 * 		b. board passengers
                 *
                 */
                logic();
//                updateStationPassengerText();

            }
        }, 0, 100);
    }

    public void logic(){
        for(int i = 0; i < allTrains.size(); i++){
            currentTrain = i;

            try{Thread.sleep(2500);} catch(Exception e) {e.printStackTrace();}

//            if(pause){
//                try{
//                    System.out.println("Threads are asleep");
//                    Thread.sleep(p.a.getTime());
//                }
//                catch(Exception e){
//                    e.printStackTrace();
//                }
//                finally{
//                    pause = false;
//                    t.getAnim(i).start();
//                }
//            }
//
//            boolean tempDirection = allTrains.get(i).getDirection();

            int threadsToReap = -1;
            int threadsReaped = 0;

            //min : (passengers waiting, free seats)
            addLog(("[TRAIN "+(allTrains.get(i).trainNum+1) + "] Currently at station "+(allTrains.get(i).getCurrStation().getStationNum()+1))+"\n");
            addLog("-----------------------------------------------------\n");
//            System.out.println("waiters: "+allTrains.get(i).getCurrStation().passWaiting.size()+" MIN available: "+allTrains.get(i).getAvailable());
            threadsToReap = Math.min(allTrains.get(i).getCurrStation().passWaiting.size(),
                                     allTrains.get(i).getAvailable());

            while(threadsReaped < threadsToReap) {
                System.out.println(threadsReaped +" < "+ threadsToReap);
//                System.out.println("pasok 1");
                boolean boarded = false;
                if(threadsCompleted > 0) {
//                    System.out.println("pasok 2");

                    if(allTrains.get(i).getCurrStation().passWaiting.size() > 0){ //may nag hihintay
//                        System.out.println("pasok 3");

                        // GUI: REMOVED PASSENGER
                        allTrains.get(i).getCurrStation().passWaiting.get(0).getSprite().setImage(null);

                        System.out.println(allTrains.get(i).getCurrStation().getStationNum());
                        boarded = c.station_on_board(allTrains.get(i).getCurrStation(),             //board mo na woooo
                                                     allTrains.get(i).getCurrStation().passWaiting.get(0),
                                                    threadsReaped + 1 == threadsToReap);
                    }

                    if(boarded) {
                        System.out.println("boarded");
                        threadsReaped++;
                    }
                }
            }

            passengersLeft -= threadsReaped;
            totalPassengersBoarded += threadsReaped;

            if(threadsToReap != threadsReaped)
                System.out.println("Error: Too many passengers on this train!");
            try{Thread.sleep(800);} catch(Exception e){e.printStackTrace();}

            /* Make sure all trains return to first station */
            if (totalPassServed == totalPassengers && i>0) {
                allTrains.get(i).stopRun();
                System.out.println("Train " + allTrains.get(i).getTrainNum() + " is decommissioned.");
                decommissioned++;
                allTrains.remove(allTrains.get(i));
//                t.anims.remove(i);
//                t.trains.remove(i);
                i--;
                if (allTrains.size() == 0) {
                    System.out.println("All trains are gone!");
                }
            }

            //baba passengers
            System.out.println("train num: "+allTrains.get(i).getTrainNum()+" passengers: "+allTrains.get(i).getPassBoarded().size());
//            int k=0;
//            while(allTrains.get(i).getPassBoarded().size() !=0){
//                System.out.println(k);
//                System.out.println("DEST: "+allTrains.get(i).getPassBoarded().get(k).getDest().getStationNum()+" CURR: "+allTrains.get(i).getCurrStation().getStationNum());
//
//                if (allTrains.get(i).getPassBoarded().get(k).getDest().getStationNum() ==  allTrains.get(i).getCurrStation().getStationNum())
//                {
//                    System.out.println(allTrains.get(i).getPassBoarded().get(k).getDest().getStationNum() ==  allTrains.get(i).getCurrStation().getStationNum());
//                    allTrains.get(i).getCurrStation().getCurrTrain().setAvailable(allTrains.get(i).getAvailable()+1);
//
//                    System.out.println("[PASSENGER " + allTrains.get(i).getPassBoarded().get(k).getPassNum() +
//                            "] leaves Train " + allTrains.get(i).getTrainNum() +
//                            " at Station " + ( allTrains.get(i).getCurrStation().getStationNum() + 1));
//
////                    addLog("[PASSENGER " + allTrains.get(i).getPassBoarded().get(k).getPassNum() +
////                            "] leaves Train " + allTrains.get(i).getTrainNum() +
////                            " at Station " + ( allTrains.get(i).getCurrStation().getStationNum() + 1)+"\n");
////                    addLog("-----------------------------------------------------\n");
//                    allTrains.get(i).deletePassBoarded(allTrains.get(i).getPassBoarded().get(0).getPassNum());
//
//                    System.out.println(" passengers: "+allTrains.get(i).getPassBoarded().size());
//                }
//                k++;
//            }
            ArrayList <Integer> removeThese = new ArrayList<Integer>();
            for(int k=0; k<allTrains.get(i).getPassBoarded().size(); k++) {
                System.out.println(k);
                System.out.println("DEST: "+allTrains.get(i).getPassBoarded().get(k).getDest().getStationNum()+" CURR: "+allTrains.get(i).getCurrStation().getStationNum());

                if (allTrains.get(i).getPassBoarded().get(k).getDest().getStationNum() ==  allTrains.get(i).getCurrStation().getStationNum())
                {
                    System.out.println(allTrains.get(i).getPassBoarded().get(k).getDest().getStationNum() ==  allTrains.get(i).getCurrStation().getStationNum());
                    allTrains.get(i).getCurrStation().getCurrTrain().setAvailable(allTrains.get(i).getAvailable()+1);
                    allTrains.get(i).getPassBoarded().get(k).setBoarded(false);
                    System.out.println(allTrains.get(i).getPassBoarded().get(k).isBoarded());
                    removeThese.add(k);
                }
            }

            for (int j=0; j<removeThese.size(); j++){

                addLog("[PASSENGER " + allTrains.get(i).getPassBoarded().get(allTrains.get(i).getPassBoarded().size()-1).getPassNum() +
                        "] leaves Train " + allTrains.get(i).getTrainNum() +
                        " at Station " + ( allTrains.get(i).getCurrStation().getStationNum() + 1)+"\n");
                addLog("-----------------------------------------------------\n");

                allTrains.get(i).deletePassBoarded(allTrains.get(i).getPassBoarded().get(allTrains.get(i).getPassBoarded().size()-1).getPassNum());

            }

            removeThese.clear();

//            System.out.println("moving train");
//            System.out.println(i);
//            System.out.println(allTrains.get(i).getCurrStation().stationNum + 1);

            if (allTrains.get(i).getCurrStation().getStationNum() == 7){
                allTrains.get(i).getCurrStation().setCurrTrain(null);
//                allTrains.get(i).setCurrStation(allTrains.get(i).getCurrStation().getNextStation());
//                allTrains.get(i).getCurrStation().setCurrTrain(allTrains.get(i));
                  moveTrainToNextStn(allTrains.get(i), allTrains.get(i).getCurrStation().getStationNum() + 2);
                allTrains.remove(i);

            }
            else if (allTrains.get(i).getCurrStation().getStationNum() <= 6){
                allTrains.get(i).getCurrStation().setCurrTrain(null);
                allTrains.get(i).setCurrStation(allTrains.get(i).getCurrStation().getNextStation());
                allTrains.get(i).getCurrStation().setCurrTrain(allTrains.get(i));
                moveTrainToNextStn(allTrains.get(i), allTrains.get(i).getCurrStation().getStationNum() + 1);

            }





        }
    }
    public void updateStationPassengerText(){
        station1PassengerText.setText(Integer.toString(allStations.get(0).getPassWaiting().size()));
        station2PassengerText.setText(Integer.toString(allStations.get(1).getPassWaiting().size()));
        station3PassengerText.setText(Integer.toString(allStations.get(2).getPassWaiting().size()));
        station4PassengerText.setText(Integer.toString(allStations.get(3).getPassWaiting().size()));
        station5PassengerText.setText(Integer.toString(allStations.get(4).getPassWaiting().size()));
        station6PassengerText.setText(Integer.toString(allStations.get(5).getPassWaiting().size()));
        station7PassengerText.setText(Integer.toString(allStations.get(6).getPassWaiting().size()));
        station8PassengerText.setText(Integer.toString(allStations.get(7).getPassWaiting().size()));
    }


    public void checkStop(){
        for(int i = 1; i < allTrains.size() - 2; i++){
            //if sabay yungtrains
            if(allTrains.get(i - 1).getCurrStation().getStationNum() == allTrains.get(i).getCurrStation().getStationNum() + 1){
//                t.getAnim(i).stop(); //ADD SABAY GUI
            }

            //overtake trains
//            if(allTrains.get(i - 1).getCurrStation().getStationNum() > allTrains.get(i).getCurrStation().getStationNum() + 1)
//                t.getAnim(i).start();
        }

        if(currentTrain > 0){
            try{
//				if(allTrains.get(currentTrain).getBoardStation().checkNextQueue(allTrains.get(currentTrain - 1), allTrains.get(currentTrain).getDirection()))
//					t.getAnim(currentTrain).stop();
//				else
//					t.getAnim(currentTrain).start();
            }catch(Exception e){}
        }
    }

//    public void resetTrains()(DELETED TIHS FUNCTION)
//    public void resetStations() (DELETED TIHS FUNCTION)
//    public void resetTrainPrev() (DELETED TIHS FUNCTION)
//    public void pause() (DELETED THIS FUNCTION)

////////////////////////////////////////////////////////////////

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        rootPane = new Pane();

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View.fxml"));

        primaryStage.setTitle("CalTrainII Automation (Process Synchronization)");
        primaryStage.setResizable(false);
        rootPane.getChildren().add(root);
        primaryStage.setScene(new Scene(rootPane, 1360, 690));
        primaryStage.show();
    }

    @FXML
    public void newTrain() throws Exception{
        ImageView imgview = new ImageView();
        File file = new File("src/res/cutetrain.png");
        Image image = new Image(file.toURI().toString());

        imgview.setImage(image);
        imgview.setFitHeight(52);
        imgview.setFitWidth(210);
        imgview.setLayoutX(-200);
        imgview.setLayoutY(220);
        imgview.toFront();

        //NEW TRAIN LOGIC!!!!!!!!!!!!!!!!
        createTrain(imgview);


//        Thread thread = new Thread(() -> {
//            double nextStation = 0;
//
//            int delay = 3500;
//            while (nextStation <= 8){
//                nextStation ++;
//
//                moveTrainToNextStn(imgview, nextStation);
//
//                try {
//                    Thread.sleep(delay);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        thread.start();
        rootPane.getChildren().add(imgview);
        numOfTrains++;
        trainNumber.setText(numOfTrains+"");
        //moveTrainToNextStn(imgview, 1);
    }

    public void moveTrainToNextStn(Train train, double nextStation) {
        double xPos = 0;

//        System.out.println(nextStation);
        if (nextStation >= 5) {
            train.getSprite().setLayoutX(-1200);
            train.getSprite().setLayoutY(539);

                }
        if (nextStation == 1) {
            xPos = 220;
        } else if (nextStation == 2) {
            xPos = 330;
        } else if (nextStation == 3) {
            xPos = 280;
        } else if (nextStation == 4) {
            xPos = 180;
        } else if (nextStation == 5) {
            xPos = 150;
        } else if (nextStation == 6) {
            xPos = 330;
        } else if (nextStation == 7) {
            xPos = 260;
        } else if (nextStation == 8) {
            xPos = 230;
        } else if (nextStation == 9) {// (CREATED JUST TO REMOVE THE TRAIN FROM GUI)
            xPos = 270;
            train.getSprite().setVisible(false);

        }

        TranslateTransition transitn = new TranslateTransition(Duration.millis(3000), train.getSprite());
        transitn.setFromX(train.getSprite().getTranslateX());
        transitn.setFromY(train.getSprite().getTranslateY());
        transitn.setByX(xPos);
        transitn.setRate(2);
        transitn.setInterpolator(Interpolator.LINEAR);
        transitn.play();

    }

    @FXML
    public void addPassenger(Button b)  {

        ImageView imgview = new ImageView();
        File file = new File("src/res/robot1.png");
        Image image = new Image(file.toURI().toString());
        imgview.setImage(image);
        imgview.setFitHeight(46);
        imgview.setFitWidth(48);

        TranslateTransition transitn = new TranslateTransition(Duration.millis(4000), imgview);

        Random rand = new Random();

        if (b.getId().compareToIgnoreCase("station1AddButton") == 0) {
            int value = rand.nextInt(215-50)+50;
            imgview.setLayoutX(value);
            imgview.setLayoutY(220);
            //NEW PASSENGER LOGIC!!!!!!!!!!!!!!!
            addPassenger(1, Integer.parseInt(station1CapSpin.getValue().toString()), imgview);
        }
        else if (b.getId().compareToIgnoreCase("station2AddButton") == 0){
            int value = rand.nextInt(461-375)+375;
            imgview.setLayoutX(value);
            imgview.setLayoutY(220);

            imgview.toBack();

            addPassenger(2, Integer.parseInt(station2CapSpin.getValue().toString()), imgview);

        }
        else if (b.getId().compareToIgnoreCase("station3AddButton") == 0){
            int value = rand.nextInt(760-635)+635;
            imgview.setLayoutX(value);
            imgview.setLayoutY(220);

            addPassenger(3, Integer.parseInt(station3CapSpin.getValue().toString()), imgview);

        }
        else if (b.getId().compareToIgnoreCase("station4AddButton") == 0){
            int value = rand.nextInt(945-905)+905;
            imgview.setLayoutX(value);
            imgview.setLayoutY(220);

            addPassenger(4, Integer.parseInt(station4CapSpin.getValue().toString()), imgview);

        }
        else if (b.getId().compareToIgnoreCase("station5AddButton") == 0){
            int value = rand.nextInt(170-35)+35;
            imgview.setLayoutX(value);
            imgview.setLayoutY(537);

            addPassenger(5, Integer.parseInt(station5CapSpin.getValue().toString()), imgview);

        }
        else if (b.getId().compareToIgnoreCase("station6AddButton") == 0){
            int value = rand.nextInt(454-334)+334;
            imgview.setLayoutX(value);
            imgview.setLayoutY(537);

            addPassenger(6, Integer.parseInt(station6CapSpin.getValue().toString()), imgview);

        }
        else if (b.getId().compareToIgnoreCase("station7AddButton") == 0){
            int value = rand.nextInt(697-619)+619;
            imgview.setLayoutX(value);
            imgview.setLayoutY(537);

            addPassenger(7, Integer.parseInt(station7CapSpin.getValue().toString()), imgview);

        }


        transitn.setByX(10);
        transitn.setRate(3);

        transitn.setInterpolator(Interpolator.EASE_IN);
        transitn.setCycleCount(Animation.INDEFINITE);
        transitn.play();

        rootPane.getChildren().add(imgview);
    }

    public void addLog(String text){
        infoFeed.appendText(text);
        //infoFeed.appendText(text);

    }

    public static void main(String[] args) {
        launch(args);
    }

}
