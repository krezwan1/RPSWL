import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.effect.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.beans.property.*;
import javafx.beans.value.*;
import javafx.event.*; 
import javafx.animation.*;
import javafx.geometry.*;
import java.io.*;
import java.util.*;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import javafx.collections.*;
import javafx.scene.control.cell.*;
import javafx.scene.control.TableColumn.*;
import javafx.util.converter.*;
import javafx.scene.chart.*;

public class DataVisualization extends Application 
{
    public static void main(String[] args) 
    {
        // Automatic VM reset, thanks to Joseph Rachmuth.
        try
        {
            launch(args);
            System.exit(0);
        }
        catch (Exception error)
        {
            error.printStackTrace();
            System.exit(0);
        }
    }

    public void start(Stage mainStage) 
    {
        mainStage.setTitle("RPSWL Genetic Algorithm");

        VBox root = new VBox();
        root.setPadding( new Insets(16) );
        root.setSpacing(16);
        root.setAlignment( Pos.CENTER );

        Scene mainScene = new Scene(root);
        mainStage.setScene(mainScene);

        // custom code below --------------------------------------------

        NumberAxis xAxis = new NumberAxis(0,1000,100);
        xAxis.setLabel("Generation");

        NumberAxis yAxis = new NumberAxis(0,0.5,0.1);
        yAxis.setLabel("Probability");

        LineChart chart = new LineChart(xAxis,yAxis);

        // first data set of averages

        ObservableList<XYChart.Data> RockAvgList = FXCollections.observableArrayList();
        ObservableList<XYChart.Data> PaperAvgList = FXCollections.observableArrayList();
        ObservableList<XYChart.Data> ScissorAvgList = FXCollections.observableArrayList();
        ObservableList<XYChart.Data> WinAvgList = FXCollections.observableArrayList();
        ObservableList<XYChart.Data> LoseAvgList = FXCollections.observableArrayList();

        // second data set of standard deviations

        ObservableList<XYChart.Data> RockStdDevList = FXCollections.observableArrayList();
        ObservableList<XYChart.Data> PaperStdDevList = FXCollections.observableArrayList();
        ObservableList<XYChart.Data> ScissorStdDevList = FXCollections.observableArrayList();
        ObservableList<XYChart.Data> WinStdDevList = FXCollections.observableArrayList();
        ObservableList<XYChart.Data> LoseStdDevList = FXCollections.observableArrayList();

        // dataList1.add( new XYChart.Data(x, y) );
        // series: a collection of lists
        ObservableList<XYChart.Series> dataSeriesList = FXCollections.observableArrayList();
        dataSeriesList.add( new XYChart.Series("Rock Average", RockAvgList) );
        dataSeriesList.add( new XYChart.Series("Paper Average", PaperAvgList) );
        dataSeriesList.add( new XYChart.Series("Scissor Average", ScissorAvgList) );
        dataSeriesList.add( new XYChart.Series("Win Average", WinAvgList) );
        dataSeriesList.add( new XYChart.Series("Lose Average", LoseAvgList) );

        dataSeriesList.add( new XYChart.Series("Rock Standard Dev", RockStdDevList) );
        dataSeriesList.add( new XYChart.Series("Paper Standard Dev", PaperStdDevList) );
        dataSeriesList.add( new XYChart.Series("Scissor Standard Dev", ScissorStdDevList) );
        dataSeriesList.add( new XYChart.Series("Win Standard Dev", WinStdDevList) );
        dataSeriesList.add( new XYChart.Series("Lose Standard Dev", LoseStdDevList) );

        chart.setData( dataSeriesList );

        // format options 
        chart.setTitle("RPSWL Genetic Algorithm");
        chart.setLegendVisible(true);
        chart.setLegendSide(Side.LEFT);

        root.getChildren().add( chart );

        // custom code above --------------------------------------------
        mainStage.show();

        new Thread()
        {
            public void run()
            {
                Population population = new Population(100);
                for (int loopNum = 0; loopNum < 1000; loopNum++)
                {
                    final int generation = loopNum;
                    try { Thread.sleep(20); }
                    catch (Exception e) {   }

                    population.evaluate();
                    population.recalculateStats();

                    //Update average lists
                    Platform.runLater( () ->
                        {
                            if (!Double.isNaN(population.rockAvg))
                                RockAvgList.add( new XYChart.Data(generation, population.rockAvg) );
                        });
                    Platform.runLater( () ->
                        {
                            if (!Double.isNaN(population.paperAvg))
                                PaperAvgList.add( new XYChart.Data(generation, population.paperAvg) );
                        }); 
                    Platform.runLater( () ->
                        {
                            if (!Double.isNaN(population.scissorAvg))
                                ScissorAvgList.add( new XYChart.Data(generation, population.scissorAvg) );
                        }); 
                    Platform.runLater( () ->
                        {
                            if (!Double.isNaN(population.winAvg))
                                WinAvgList.add( new XYChart.Data(generation, population.winAvg) );
                        }); 
                    Platform.runLater( () ->
                        {
                            if (!Double.isNaN(population.loseAvg))
                                LoseAvgList.add( new XYChart.Data(generation, population.loseAvg) );
                        }); 

                        
                    //Update standard dev lists
                    Platform.runLater( () ->
                        {
                            if (!Double.isNaN(population.rockStdDev))
                                RockStdDevList.add( new XYChart.Data(generation, population.rockStdDev) );
                        }); 
                    Platform.runLater( () ->
                        {
                            if (!Double.isNaN(population.paperStdDev))
                                PaperStdDevList.add( new XYChart.Data(generation, population.paperStdDev) );
                        }); 
                    Platform.runLater( () ->
                        {
                            if (!Double.isNaN(population.scissorStdDev))
                                ScissorStdDevList.add( new XYChart.Data(generation, population.scissorStdDev) );
                        }); 
                    Platform.runLater( () ->
                        {
                            if (!Double.isNaN(population.winStdDev))
                                WinStdDevList.add( new XYChart.Data(generation, population.winStdDev) );
                        }); 
                    Platform.runLater( () ->
                        {
                            if (!Double.isNaN(population.loseStdDev))
                                LoseStdDevList.add( new XYChart.Data(generation, population.loseStdDev) );
                        }); 

                    population.restock(0.001);
                }

                System.out.println(population.agents);
              
            }
        }.start();

    }

}
