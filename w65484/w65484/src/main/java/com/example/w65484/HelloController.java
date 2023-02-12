package com.example.w65484;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

public class HelloController {
    @FXML
    DatePicker date;

    @FXML
    TextField temp;

    @FXML
    TextField wiatr;

    @FXML
    TextField cisn;

    @FXML
    DatePicker dateFrom;

    @FXML
    DatePicker dateTo;

    @FXML
    DatePicker dateFromMinMax;

    @FXML
    DatePicker dateToMinMax;

    @FXML
    Label AvgBetween;

    @FXML
    Label min;

    @FXML
    Label max;

    public void resetFirstRow(){
        cisn.setText("");
        temp.setText("");
        wiatr.setText("");
        date.setValue(null);
    }

    public void resetSecondRow(){
        dateFrom.setValue(null);
        dateTo.setValue(null);
    }

    public void resetThirdRow(){
        dateFromMinMax.setValue(null);
        dateToMinMax.setValue(null);
    }

    public boolean isNegative(String value){
        float val = Float.parseFloat(value);
        return val < 0;
    }

    public void addToDatebase(){
        Integer id = new Random().nextInt();
        try{
            if(date.getValue() == null || temp.getText().isEmpty() || cisn.getText().isEmpty() || wiatr.getText().isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Uzupełnij pola");
                alert.show();
                return;
            }
            if(isNegative(temp.getText()) || isNegative(cisn.getText()) || isNegative(wiatr.getText()))
            {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Podano nieprawidłowe liczby");
                alert.show();
                return;
            }
            Connection c = DbAccessor.c;
            Statement stmt = c.createStatement();
            String query = "INSERT INTO Odczyt VALUES(%s,'%s',%s,%s,%s)".formatted(id, date.getValue(), temp.getText(), cisn.getText(), wiatr.getText());
            stmt.executeUpdate(query);
            stmt.close();
            c.commit();
            resetFirstRow();
        }
        catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public void getAvg(){
        if(isNullAvg())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Wypełnij zakres dat od i do dla średniej");
            alert.show();
            return;
        }
        String query = "SELECT AVG(temperatura) as temp, AVG(cisnienie) as cisn, AVG(wiatr) as wiatr FROM Odczyt WHERE data BETWEEN '%s' AND '%s'".formatted(dateFrom.getValue(), dateTo.getValue());
        try {
            Statement stmt = DbAccessor.c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                Float avgTemp = rs.getFloat("temp");
                Float avgCisnienie = rs.getFloat("cisn");
                Float avgWiatr = rs.getFloat("wiatr");
                AvgBetween.setText("Temperatura: %s, Ciśnienie: %s, Prędkość wiatru: %s".formatted(avgTemp, avgCisnienie, avgWiatr));
            }
            rs.close();
            stmt.close();
            resetSecondRow();
        }
        catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    private boolean isNullAvg()
    {
        return dateFrom.getValue() == null || dateTo.getValue() == null;
    }

    private boolean isNullMinMax()
    {
        return dateFromMinMax.getValue() == null || dateToMinMax.getValue() == null;
    }


    public void getMinMax(){
        if(isNullMinMax())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Wypełnij zakres dat od i do dla minmax");
            alert.show();
            return;
        }
        String query = "SELECT MIN(temperatura) as minTemp, MAX(temperatura) as maxTemp, MIN(cisnienie) as minCis, MAX(cisnienie) as maxCis, MIN(wiatr) as minWiatr, MAX(wiatr) as maxWiatr FROM Odczyt WHERE data BETWEEN '%s' AND '%s'".formatted(dateFromMinMax.getValue(), dateToMinMax.getValue());
        try {
            Statement stmt = DbAccessor.c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                Float minTemp = rs.getFloat("minTemp");
                Float minCis = rs.getFloat("minCis");
                Float minWiatr = rs.getFloat("minWiatr");
                Float maxTemp = rs.getFloat("maxTemp");
                Float maxCis = rs.getFloat("maxCis");
                Float maxWiatr = rs.getFloat("maxWiatr");
                min.setText("Temperatura: %s, Ciśnienie: %s, Prędkość wiatru: %s".formatted(minTemp, minCis, minWiatr));
                max.setText("Temperatura: %s, Ciśnienie: %s, Prędkość wiatru: %s".formatted(maxTemp, maxCis, maxWiatr));
            }
            rs.close();
            stmt.close();
            resetThirdRow();
        }
        catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
}