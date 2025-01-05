package FXIO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import javafx.scene.text.Text;
public class CountrwisePlayerCount {

    @FXML
    private BarChart<String, Number> countryBarChart;

    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    public LoginApp main;
    /*public void setCountryData(HashMap<String, Integer> countryData) {
        // Set axis labels
        xAxis.setLabel("Country");
        yAxis.setLabel("Number of Players");

        // Clear previous data
        countryBarChart.getData().clear();

        // Create a series for the data
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Players by Country");

        // Populate the series with data from the HashMap
        countryData.forEach((country, count) -> {
            XYChart.Data<String, Number> data = new XYChart.Data<>(country, count);
            series.getData().add(data);

            // Add a text label to show the count above each bar
            data.nodeProperty().addListener((observable, oldNode, newNode) -> {
                if (newNode != null) {
                    Text label = new Text(String.valueOf(count));
                    label.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");

                    newNode.parentProperty().addListener((obs, oldParent, newParent) -> {
                        if (newParent != null) {
                            // Center the label on the bar (horizontally centered)
                            label.layoutXProperty().bind(newNode.layoutXProperty().add(newNode.getBoundsInParent().getWidth() / 2 - label.getBoundsInParent().getWidth() / 2));
                            // Position the label slightly above the bar
                            label.layoutYProperty().bind(newNode.layoutYProperty().subtract(5));
                            ((Group) newParent).getChildren().add(label);
                        }
                    });
                }
            });
        });

        // Add the series to the BarChart
        countryBarChart.getData().add(series);

        // Set chart title
        countryBarChart.setTitle("Countrywise Player Count");
    }*/
    public void setCountryData(HashMap<String, Integer> countryData) {
        xAxis.setLabel("Country");
        yAxis.setLabel("Number of Players");

        countryBarChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Players by Country");

        // Adjust the bar width based on the number of data points
        int numCountries = countryData.size();
        double barWidth = 50.0;  // Default bar width
        if (numCountries > 10) {
            barWidth = 40.0;
        }
        if (numCountries > 20) {
            barWidth = 30.0;
        }

        double finalBarWidth = barWidth;
        countryData.forEach((country, count) -> {
            XYChart.Data<String, Number> data = new XYChart.Data<>(country, count);
            series.getData().add(data);

            // Add a text label inside the bar
            data.nodeProperty().addListener((observable, oldNode, newNode) -> {
                if (newNode != null) {
                    Text label = new Text(String.valueOf(count));
                    label.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-fill: black;");

                    // Bind the label's position to be inside the bar
                    newNode.parentProperty().addListener((obs, oldParent, newParent) -> {
                        if (newParent != null) {
                            // Bind the label's position to the center of the bar
                            label.layoutXProperty().bind(newNode.layoutXProperty().add(newNode.getBoundsInParent().getWidth()/1000- label.getBoundsInParent().getWidth() / 2));
                            label.layoutYProperty().bind(newNode.layoutYProperty().add(newNode.getBoundsInParent().getHeight() /20000000- label.getBoundsInParent().getHeight() / 2000000)); // Center vertically in the bar
                            ((Group) newParent).getChildren().add(label);
                        }
                    });
                }
            });

            // You can now safely modify the bar style after the node is available
            data.nodeProperty().addListener((observable, oldNode, newNode) -> {
                if (newNode != null) {
                    newNode.setStyle("-fx-bar-width: " + finalBarWidth + "px;");
                }
            });
        });

        countryBarChart.getData().add(series);
        countryBarChart.setTitle("Countrywise Player Count");
    }







    public void setMain(LoginApp main) {
        this.main = main;
    }

    public void BackButtonPressed(ActionEvent actionEvent) {
        try {
            //cleanup();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("PlayerSearch.fxml"));
            Parent root = loader.load();

            // Loading the controller
            PlayerSearchController controller = loader.getController();
            controller.setMain(main);

            // Set the primary stage
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Player Search");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
