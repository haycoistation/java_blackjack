package blackjack;

import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.Orientation;
import javafx.scene.text.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class DealStage extends Pane
{
    private Controller controller;
    private Group buttons;
    private Group pHand;
    private Group dHand;
    private DealStage me;
    private Slider bet;
    private Group text;
    private Group start;
    private Text money;
    private Text con;
    private Chart chart;
    private Group chartGroup;
    
    public DealStage(Controller controller){
        super();
        this.controller = controller;
        me = this;
        chart = new Chart(250);
        
        //Screen K
        Button btn_K = new Button();
        btn_K.relocate(175,220);
        btn_K.setText("Start");
        getChildren().add(btn_K);
        btn_K.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                controller.sendMsg("Start",me);
            }
        });
        
        //Screen Game
        pHand = new Group();
        buttons = new Group();
        dHand = new Group();
        text = new Group();
        start = new Group();
        chartGroup = new Group();
        
        
        //bet slider
        bet = new Slider(5,100,25);    
        bet.setBlockIncrement(25);
        bet.setMinorTickCount(1);
        bet.setMajorTickUnit(10);
        bet.setShowTickLabels(true);
        bet.setShowTickMarks(true); 
        bet.setSnapToTicks(true);
        bet.setOrientation(Orientation.VERTICAL);
        bet.relocate(10,280);
        start.getChildren().add(bet);
        
        //deal button
        Button btn_Deal = new Button();
        start.getChildren().add(btn_Deal);
        btn_Deal.relocate(175,470);
        btn_Deal.setText("Deal");
        btn_Deal.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                controller.sendMsg("Deal",me);
            }
        });
        
        //(con)ditional text
        con = new Text();
        con.setFont(new Font(20));
        con.setText("Choose a bet and press Deal");
        con.relocate(140, 230);
        start.getChildren().add(con);
        
        //bet Text
        Text betDisp = new Text();
        betDisp.setText("Bet: $25.00");
        betDisp.relocate(10, 225);
        text.getChildren().add(betDisp);
        
        bet.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                betDisp.setText("Bet: $" + String.format("%3.2f",new_val));
            }
        });
        
        //Money Text
        Text moneyDisp = new Text();
        money = moneyDisp;
        moneyDisp.setText("Money: $250.00");
        moneyDisp.relocate(10, 238);
        text.getChildren().add(moneyDisp);
        
        //Hit
        Button btn_Hit = new Button();
        buttons.getChildren().add(btn_Hit);
        btn_Hit.relocate(25,470);
        btn_Hit.setText("Hit");
        btn_Hit.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                controller.sendMsg("Hit",me);
            }
        });
        
        //Stand
        Button btn_Stand = new Button();
        buttons.getChildren().add(btn_Stand);
        btn_Stand.relocate(60,470);
        btn_Stand.setText("Stand");
        btn_Stand.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                controller.sendMsg("Stand",me);
            }
        });
        
        //Double
        Button btn_Dbl = new Button();
        buttons.getChildren().add(btn_Dbl);
        btn_Dbl.relocate(110,470);
        btn_Dbl.setText("Dbl Down");
        btn_Dbl.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                controller.sendMsg("DblD",me);
            }
        });
    }
    
    public void setCon(String str){
        con.setText(str);
    }
    
    public int getBet(){
        return Integer.valueOf(String.format("%3.0f",bet.getValue()).trim());
    }
    
    public void updateMoney(int newMon){
        money.setText("Money: $" + newMon + ".00");
    }
    
    public void updatePHand(Group hand){
        pHand = hand;
        pHand.relocate(50,280);
    }
    
    public void updateDHand(Group hand){
        dHand = hand;
        dHand.relocate(50,50);
    }
    
    public void showButtons(boolean tf){
        buttons.setVisible(tf);
        start.setVisible(!tf);
    }
    
    public void updateChart(int money){
        chart.add((double)money);
    }
    
    public void show_Game(){
        getChildren().clear();
        getChildren().add(pHand);
        getChildren().add(dHand);
        getChildren().add(buttons);
        getChildren().add(start);
        getChildren().add(text);
        chartGroup = chart.makeChart();
        chartGroup.relocate(500,210);
        getChildren().add(chartGroup);
        requestLayout();    
    }
}
