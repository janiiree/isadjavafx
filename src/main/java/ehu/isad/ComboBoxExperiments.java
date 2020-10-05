package ehu.isad;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.*;

import java.io.IOException;
import java.net.*;
import java.io.*;
import com.google.gson.Gson;


public class ComboBoxExperiments extends Application  {

    private Txanpona txanpona;

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Txanponak");

        ComboBox comboBox = new ComboBox();

        comboBox.getItems().add("BTC");
        comboBox.getItems().add("ETH");
        comboBox.getItems().add("LTC");

        comboBox.setEditable(true);

        Label label = new Label();
        Text text=null;

       try {
            txanpona= this.URLirakurri(comboBox.getValue().toString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        text.setTextContent("prezioa: " + txanpona.price);

        label.setVisible(true);
        label.setText("Txanponak: ");


        comboBox.setOnAction(e -> {
            System.out.println( comboBox.getValue().toString() );
            try {
                txanpona = this.URLirakurri(comboBox.getValue().toString());
            }
            catch (IOException IOe) {
                IOe.printStackTrace();
            }
            text.setTextContent("prezioa: " + txanpona.price);
        });

        VBox vbox = new VBox(comboBox);

        Scene scene = new Scene(vbox, 200, 120);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public Txanpona URLirakurri(String txIzena) throws IOException {
        String inputLine;
        URL coinmarket;
        Txanpona txanpona=null;
        try {
            coinmarket = new URL("https://api.gdax.com/products/" + txIzena + "-eur/ticker");
            URLConnection yc = coinmarket.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            inputLine = in.readLine();
            Gson gson = new Gson();
            txanpona = gson.fromJson(inputLine, Txanpona.class);

            in.close();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return txanpona;
    }


    public static void main(String[] args) {
        Application.launch(args);
    }
}
