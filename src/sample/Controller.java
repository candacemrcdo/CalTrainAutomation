package sample;

import Model.Passenger;
import Model.Train;
import Model.Station;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.application.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.lang.InterruptedException;

public class Controller implements Initializable {
    @FXML
    private  TextArea station1TextArea;
    @FXML
    private  TextArea station2TextArea;
    @FXML
    private  TextArea station3TextArea;
    @FXML
    private  TextArea station4TextArea;
    @FXML
    private  TextArea station5TextArea;
    @FXML
    private  TextArea station6TextArea;
    @FXML
    private  TextArea station7TextArea;
    @FXML
    private  TextArea station8TextArea;

    @FXML
    Button station1AddButton;
    @FXML
    Button station2AddButton;

    @FXML
    Button station3AddButton;

    @FXML
    Button station4AddButton;

    @FXML
    Button station5AddButton;
    @FXML
    Button station6AddButton;
    @FXML
    Button station7AddButton;

    @FXML
    Button station8AddButton;

    @FXML
    Text trainNumber;

    @FXML
    Spinner capacitySpinner;

    @FXML
    Button deploytrain;

    ArrayList<Passenger> newPassengers = new ArrayList<Passenger>();
    ArrayList<Train> newTrains = new ArrayList<Train>();
    Station[] stations = new Station[8];

    public Controller() {
        stations[0]= new Station(1);
        stations[1]= new Station(2);
        stations[2]= new Station(3);
        stations[3]= new Station(4);
        stations[4]= new Station(5);
        stations[5]= new Station(6);
        stations[6]= new Station(7);
        stations[7]= new Station(8);

    }

    int trainnumber = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        station1AddButton.setOnAction(e -> addPassenger(station1AddButton));
        station2AddButton.setOnAction(e -> addPassenger(station2AddButton));
        station3AddButton.setOnAction(e -> addPassenger(station3AddButton));
        station4AddButton.setOnAction(e -> addPassenger(station4AddButton));
        station5AddButton.setOnAction(e -> addPassenger(station5AddButton));
        station6AddButton.setOnAction(e -> addPassenger(station6AddButton));
        station7AddButton.setOnAction(e -> addPassenger(station7AddButton));
        station8AddButton.setOnAction(e -> addPassenger(station8AddButton));

        deploytrain.setOnAction(e-> addTrain(deploytrain));

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 40, 1);
        capacitySpinner.setValueFactory(valueFactory);
    }


    @FXML
    public void addPassenger(Button b){
            System.out.println(b.getText());
            if(b.getText().compareToIgnoreCase("Station 1") == 0){
                int passengerNum = newPassengers.size()+1;
                station1TextArea.appendText("Passenger "+ passengerNum +" arrived in Station 1\n");
                newPassengers.add(new Passenger(passengerNum, 1,1));
                stations[0].addPassengersWaiting(newPassengers.get(newPassengers.size()-1));
                //station_wait_for_train(Station);
                //newPassengers.get(newPassengers.size()-1).station_Wait_For_Train(stations[0]);
            }
            else if(b.getText().compareToIgnoreCase("Station 2") == 0){
                int passengerNum = newPassengers.size()+1;
                station2TextArea.appendText("Passenger "+ passengerNum +" arrived in Station 2\n");
                newPassengers.add(new Passenger(passengerNum, 2,2));
                stations[1].addPassengersWaiting(newPassengers.get(newPassengers.size()-1));
            }
            else if(b.getText().compareToIgnoreCase("Station 3") == 0){
                int passengerNum = newPassengers.size()+1;
                station3TextArea.appendText("Passenger "+ passengerNum +" arrived in Station 3\n");
                newPassengers.add(new Passenger(passengerNum, 3,3));
                stations[2].addPassengersWaiting(newPassengers.get(newPassengers.size()-1));

            }
            else if(b.getText().compareToIgnoreCase("Station 4") == 0){
                int passengerNum = newPassengers.size()+1;
                station4TextArea.appendText("Passenger "+ passengerNum +" arrived in Station 4\n");
                newPassengers.add(new Passenger(passengerNum, 4,4));
                stations[3].addPassengersWaiting(newPassengers.get(newPassengers.size()-1));

            }
            else if(b.getText().compareToIgnoreCase("Station 5") == 0){
                int passengerNum = newPassengers.size()+1;
                station5TextArea.appendText("Passenger "+ passengerNum +" arrived in Station 5\n");
                newPassengers.add(new Passenger(passengerNum, 5,5));
                stations[4].addPassengersWaiting(newPassengers.get(newPassengers.size()-1));

            }
            else if(b.getText().compareToIgnoreCase("Station 6") == 0){
                int passengerNum = newPassengers.size()+1;
                station6TextArea.appendText("Passenger "+ passengerNum +" arrived in Station 6\n");
                newPassengers.add(new Passenger(passengerNum, 6,6));
                stations[5].addPassengersWaiting(newPassengers.get(newPassengers.size()-1));

            }
            else if(b.getText().compareToIgnoreCase("Station 7") == 0){
                int passengerNum = newPassengers.size()+1;
                station7TextArea.appendText("Passenger "+ passengerNum +" arrived in Station 7\n");
                newPassengers.add(new Passenger(passengerNum, 7,7));
                stations[6].addPassengersWaiting(newPassengers.get(newPassengers.size()-1));

            }
            else if(b.getText().compareToIgnoreCase("Station 8") == 0){
                int passengerNum = newPassengers.size()+1;
                station8TextArea.appendText("Passenger "+ passengerNum +" arrived in Station 8\n");
                newPassengers.add(new Passenger(passengerNum, 8,8));
                stations[7].addPassengersWaiting(newPassengers.get(newPassengers.size()-1));

            }
    }


    @FXML
    public void addTrain(Button b) {

        trainnumber++;
        trainNumber.setText(trainnumber+"");

        Train newtrain = new Train(trainnumber,40);
        newtrain.setCapacity(Integer.parseInt(capacitySpinner.getValue() + ""));
        newtrain.setAvailableSeats(newtrain.getCapacity());
        newTrains.add(newtrain);


        Thread thread = new Thread(() -> {
            int index = 0;
            while (index < 8){
                if(index!=0){
                    stations[index-1].setCurrTrain(newtrain);
                }

                index++;
                newtrain.setCurrStation(stations[index-1]);
                stations[index-1].setCurrTrain(newtrain);
                updateStations(newtrain.getTrainNum(), newtrain.getCurrStation());

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void updateStations(int trainnumber, Station station){
        if (station.getStationNum() == 1) {
            station1TextArea.appendText("Train " + trainnumber +  " is at station " + station.getStationNum() + "\n");
            updateTrains(trainnumber, station);
        } else if (station.getStationNum() == 2){
            station2TextArea.appendText("Train " + trainnumber +  " is at station " + station.getStationNum() + "\n");
            updateTrains(trainnumber,station);
        } else if (station.getStationNum() == 3){
            station3TextArea.appendText("Train " + trainnumber +  " is at station " + station.getStationNum() + "\n");
            updateTrains(trainnumber,station);
        } else if (station.getStationNum() == 4){
            station4TextArea.appendText("Train " + trainnumber +  " is at station " + station.getStationNum() + "\n");
            updateTrains(trainnumber,station);
        } else if (station.getStationNum() == 5){
            station5TextArea.appendText("Train " + trainnumber +  " is at station " + station.getStationNum() + "\n");
            updateTrains(trainnumber,station);
        } else if (station.getStationNum() == 6){
            station6TextArea.appendText("Train " + trainnumber +  " is at station " + station.getStationNum() + "\n");
            updateTrains(trainnumber,station);
        } else if (station.getStationNum() == 7){
            station7TextArea.appendText("Train " + trainnumber +  " is at station " + station.getStationNum() + "\n");
            updateTrains(trainnumber,station);
        } else if (station.getStationNum() == 8){
            station8TextArea.appendText("Train " + trainnumber +  " is at station " + station.getStationNum() + "\n");
            updateTrains(trainnumber,station);
        }
    }

    public void updateTrains(int trainnumber, Station station){
        if (station.getStationNum() == 1) {
            for(int i = 0; i < newPassengers.size(); i++){
                int passengerNum = stations[0].subPassengersWaiting(newPassengers.get(i));
                if (passengerNum != 0)
                    station1TextArea.appendText("Passenger "+ passengerNum + " rode Train " + trainnumber + "\n");
            }
        }
        else if (station.getStationNum() == 2) {
            for(int i = 0; i < newPassengers.size(); i++){
                int passengerNum = stations[1].subPassengersWaiting(newPassengers.get(i));
                if (passengerNum != 0)
                    station2TextArea.appendText("Passenger "+ passengerNum + " rode Train " + trainnumber + "\n");
            }
        }
        else if (station.getStationNum() == 3) {
            for(int i = 0; i < newPassengers.size(); i++){
                int passengerNum = stations[2].subPassengersWaiting(newPassengers.get(i));
                if (passengerNum != 0)
                    station3TextArea.appendText("Passenger "+ passengerNum + " rode Train " + trainnumber + "\n");
            }
        }
        else if (station.getStationNum() == 4) {
            for(int i = 0; i < newPassengers.size(); i++){
                int passengerNum = stations[3].subPassengersWaiting(newPassengers.get(i));
                if (passengerNum != 0)
                    station4TextArea.appendText("Passenger "+ passengerNum + " rode Train " + trainnumber + "\n");
            }
        }
        else if (station.getStationNum() == 5) {
            for(int i = 0; i < newPassengers.size(); i++){
                int passengerNum = stations[4].subPassengersWaiting(newPassengers.get(i));
                if (passengerNum != 0)
                    station5TextArea.appendText("Passenger "+ passengerNum + " rode Train " + trainnumber + "\n");
            }
        }
        else if (station.getStationNum() == 6) {
            for(int i = 0; i < newPassengers.size(); i++){
                int passengerNum = stations[5].subPassengersWaiting(newPassengers.get(i));
                if (passengerNum != 0)
                    station6TextArea.appendText("Passenger "+ passengerNum + " rode Train " + trainnumber + "\n");
            }
        }
        else if (station.getStationNum() == 7) {
            for(int i = 0; i < newPassengers.size(); i++){
                int passengerNum = stations[6].subPassengersWaiting(newPassengers.get(i));
                if (passengerNum != 0)
                    station7TextArea.appendText("Passenger "+ passengerNum + " rode Train " + trainnumber + "\n");
            }
        }
        else if (station.getStationNum() == 8) {
            for(int i = 0; i < newPassengers.size(); i++){
                int passengerNum = stations[7].subPassengersWaiting(newPassengers.get(i));
                if (passengerNum != 0)
                    station8TextArea.appendText("Passenger "+ passengerNum + " rode Train " + trainnumber + "\n");
            }
        }
    }



}
