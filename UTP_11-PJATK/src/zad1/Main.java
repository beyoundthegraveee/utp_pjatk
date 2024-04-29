package zad1;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javafx.scene.control.Button;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Main extends Application{

    TextArea textArea;

    List<MyThread> list = new ArrayList<>();

     private Scene scene;

    ExecutorService service = Executors.newCachedThreadPool();

    List<MyThread> canceledThreads = new ArrayList<>();

    List<Future<Void>> futures = new ArrayList<>();
    int count = 0;

    HBox buttons;
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane parent = new BorderPane();
        stage.setResizable(true);
        textArea = new TextArea();
        textArea.setEditable(false);
        ScrollPane scrollPane = new ScrollPane(textArea);
        scrollPane.setFitToHeight(true);
        Button button1 = new Button("Stop");
        button1.setAlignment(Pos.TOP_CENTER);
        Button button2 = new Button("Create new");
        button2.setAlignment(Pos.TOP_CENTER);
        stage.setTitle("Thread pool");
        button1.setMaxWidth(Double.MAX_VALUE);
        button2.setMaxWidth(Double.MAX_VALUE);
        scene = new Scene(parent,600, 700);



        buttons = new HBox();
        VBox.setVgrow(textArea, Priority.ALWAYS);
        buttons.setAlignment(Pos.BOTTOM_LEFT);
        VBox buttonsVBox = new VBox(button1, button2);
        parent.setTop(buttonsVBox);

        parent.setCenter(textArea);
        parent.setBottom(buttons);
        button1.setPrefSize(scene.getWidth(), 20);
        button2.setPrefSize(scene.getWidth(), 20);
        button2.setOnAction(e -> {
            double currentSize = stage.getWidth();
            if(count >=6) {
                stage.setWidth(currentSize + 100);
            }
            createNewThreadButton();
        });
        button1.setOnAction(e -> stopAllThreads());
        stage.setScene(scene);
        stage.show();
    }


    public void createNewThreadButton(){
        count++;
        Button button = new Button("T"+count);
        String name = button.getText();
        button.setPrefSize(100,30);
        buttons.getChildren().add(button);
        MyThread thread = new MyThread(button, name);
        list.add(thread);
        button.setOnAction(e-> {
            thread.ThreadButton(button);
        });


        button.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.C) {
                thread.CancelThread();
            }
        });

        button.setFocusTraversable(true);
    }


    public void stopAllThreads(){
        for (MyThread thread : list){
            if(!canceledThreads.contains(thread)){
                thread.StopAllThreads();
            }
        }
        service.shutdownNow();
    }


    public static void main(String[] args) {
        launch(args);
    }


    class MyThread{

        private final Button button;

        private String buttonName;

        volatile boolean stopped = false;

        volatile boolean suspended = false;





        public MyThread(Button button, String buttonName) {
            this.button = button;
            this.buttonName = buttonName;
        }

        public void ThreadButton(Button button){
            String text = button.getText();
            if (text.startsWith("T")){
                StartThread(button);
            }
            if(text.startsWith("Suspend")){
                StopThread();
            }
            if (text.startsWith("Continue")){
                ContinueThread();
            }
        }

        public void StartThread(Button current){
            button.getParent().requestFocus();
            if(service.isShutdown()){
                textArea.appendText("Executor is Shutdown");
                return;
            }
            String [] parts = current.getText().split("[^0-9]");
            String number = parts[1];
            current.setText("Suspend " + current.getText());
            Random random = new Random();
            int[] possibleLimits = {1000, 2000, 3000};
            int limit = possibleLimits[random.nextInt(possibleLimits.length)];
               Callable <Void> callable = ()->{
                int [] sum = {0};
                while (sum[0] <= limit &&  !stopped){
                    try {
                        synchronized (this) {
                            while (suspended)
                                wait();
                        }
                    }catch (InterruptedException e){
                        continue;
                    }
                    int randNumber = (int) (Math.random()*100+1);
                    sum[0]+=randNumber;
                    Platform.runLater(()->{
                        textArea.appendText("Thread " + number + " (limit = " + limit + "): " + randNumber + ", sum = " + sum[0] +"\n");
                    });
                    try {
                        Thread.sleep(300);
                    }catch (InterruptedException e){
                        continue;
                    }
                }
                Platform.runLater(()->{
                    textArea.appendText("Thread " + number + ": Done!" + "\n");
                });
                    button.setDisable(true);
                    Thread.currentThread().interrupt();
                   ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
                   scheduler.schedule(() -> {
                       Platform.runLater(() -> buttons.getChildren().remove(button));
                   }, 3, TimeUnit.SECONDS);

                return null;
              };

              Future<Void> future = service.submit(callable);
              futures.add(future);
        }

        public void StopThread(){
            button.getParent().requestFocus();
            suspended = true;
            String [] text = button.getText().split(" ");
            button.setText("Continue " + text[1]);
        }

        public void ContinueThread(){
            button.getParent().requestFocus();
            suspended = false;
            synchronized (this){
                notify();
            }
            String [] parts = button.getText().split(" ");
            button.setText("Suspend "+parts[1]);
        }


        public void StopAllThreads(){
            String [] parts = button.getText().split(" ");
            stopped = true;
            button.setText(parts[1]+" done!"+"\n");
            button.setDisable(true);
        }

        public void CancelThread(){
            suspended = true;
            canceledThreads.add(this);
            try {
                Thread.currentThread().interrupt();
            }catch (Exception e){
                return;
            }
            String [] text = button.getText().split(" ");
            button.setText(buttonName + " cancelled");
            textArea.appendText("Thread " + text[1] + ": Cancelled!" + "\n");
            button.setDisable(true);
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.schedule(() -> {
                Platform.runLater(() -> buttons.getChildren().remove(button));
            }, 3, TimeUnit.SECONDS);

        }

    }
}
