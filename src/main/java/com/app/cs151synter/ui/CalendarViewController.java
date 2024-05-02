package com.app.cs151synter.ui;
import com.app.cs151synter.customExceptions.IntentResponseAndViewControllerCollisionException;
import com.app.cs151synter.dataContainers.DatedSyllabusEntity;
import com.app.cs151synter.dataManipulation.CalendarResponse;
import com.app.cs151synter.dataManipulation.IntentResponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;


public class CalendarViewController implements ViewController {

    ZonedDateTime dateFocus = ZonedDateTime.now();
    ZonedDateTime today = ZonedDateTime.now();
    List<DatedSyllabusEntity> DatedSyllabusEntities;
    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private FlowPane calendar;
    @FXML
    private Button homeButton;

    public void setDatedSyllabusEntities(List<DatedSyllabusEntity> DatedSyllabusEntities) {
        this.DatedSyllabusEntities = DatedSyllabusEntities;
        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();
        drawCalendar();
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();

    }
    @FXML
    private void headHome() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("syllabusmain.fxml"));

        Scene scene = homeButton.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;

        stage.setScene(new Scene(root));
    }
    @FXML
    void backOneMonth(ActionEvent event) {
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void forwardOneMonth(ActionEvent event) {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    private void drawCalendar(){
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));

        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 1;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();

        //List of Dated Syllabus Entities
        Map<Integer, List<DatedSyllabusEntity>> DatedSyllabusEntitiesMap = createCalendarMap(DatedSyllabusEntities);

        int monthMaxDate = dateFocus.getMonth().maxLength();
        //Check for leap year
        if(dateFocus.getYear() % 4 != 0 && monthMaxDate == 29){
            monthMaxDate = 28;
        }
        int dateOffset = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1,0,0,0,0,dateFocus.getZone()).getDayOfWeek().getValue();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(strokeWidth);
                double rectangleWidth =(calendarWidth/7) - strokeWidth - spacingH;
                rectangle.setWidth(rectangleWidth);
                double rectangleHeight = (calendarHeight/6) - strokeWidth - spacingV;
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);

                int calculatedDate = (j+1)+(7*i);
                if(calculatedDate > dateOffset){
                    int currentDate = calculatedDate - dateOffset;
                    if(currentDate <= monthMaxDate){
                        Text date = new Text(String.valueOf(currentDate));
                        double textTranslationY = - (rectangleHeight / 2) * 0.75;
                        date.setTranslateY(textTranslationY);
                        stackPane.getChildren().add(date);

                        List<DatedSyllabusEntity> datedSyllabusEntities = DatedSyllabusEntitiesMap.get(currentDate);
                        if(datedSyllabusEntities != null){
                            createCalendarBlock(datedSyllabusEntities, rectangleHeight, rectangleWidth, stackPane);
                        }
                    }
                    if(today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate){
                        rectangle.setStroke(Color.RED);
                    }
                }
                calendar.getChildren().add(stackPane);
            }
        }
    }

    private void createCalendarBlock(List<DatedSyllabusEntity> datedSyllabusEntities, double rectangleHeight, double rectangleWidth, StackPane stackPane) {
        VBox calendarActivityBlock = new VBox();
        for (int k = 0; k < datedSyllabusEntities.size(); k++) {
            //figure out how to actually show the items on the dates
            DatedSyllabusEntity currActivity = datedSyllabusEntities.get(k);
            ZonedDateTime currActivityDate = ZonedDateTime.ofInstant(currActivity.getDueDate().toInstant(), ZoneId.systemDefault());
            if(k >= 2) {
                Text moreActivities = new Text("Click for more detail...");
                calendarActivityBlock.getChildren().add(moreActivities);
                moreActivities.setOnMouseClicked(mouseEvent -> {
                    String text = "";
                    for(DatedSyllabusEntity calendarActivity : datedSyllabusEntities){
                        ZonedDateTime calendarActivityDate = ZonedDateTime.ofInstant(calendarActivity.getDueDate().toInstant(), ZoneId.systemDefault());
                        text += calendarActivity.getTitle() + ", " + calendarActivityDate.toLocalTime() + "\n" + calendarActivity.getDescription() + "\n";
                    }
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Info");
                    alert.setHeaderText("Information on " + currActivityDate.getMonthValue() + "/" + currActivityDate.getDayOfMonth() + "/" + currActivityDate.getYear());
                    alert.setContentText(text);
                    alert.showAndWait();
                });
                break;
            }
            Text text = new Text(datedSyllabusEntities.get(k).getTitle() + ", " + currActivityDate.toLocalTime());
            calendarActivityBlock.getChildren().add(text);
            text.setOnMouseClicked(mouseEvent -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Info");
                alert.setHeaderText("Information on  " + currActivityDate.getMonth().getValue() + "/" + currActivityDate.getDayOfMonth() + "/" + currActivityDate.getYear());
                alert.setContentText(currActivity.getTitle() + ", " + currActivityDate.toLocalTime() + "\n" + currActivity.getDescription());
                alert.showAndWait();
            });
        }
        calendarActivityBlock.setTranslateY((rectangleHeight / 2) * 0.20);
        calendarActivityBlock.setMaxWidth(rectangleWidth * 0.8);
        calendarActivityBlock.setMaxHeight(rectangleHeight * 0.65);
        calendarActivityBlock.setStyle("-fx-background-color:WHITE");
        stackPane.getChildren().add(calendarActivityBlock);
    }

    private Map<Integer, List<DatedSyllabusEntity>> createCalendarMap(List<DatedSyllabusEntity> datedSyllabusEntities) {
        Map<Integer, List<DatedSyllabusEntity>> calendarMap = new HashMap<>();

        for (DatedSyllabusEntity syllabusItem: datedSyllabusEntities) {
            ZonedDateTime dueDate = ZonedDateTime.ofInstant(syllabusItem.getDueDate().toInstant(), ZoneId.systemDefault());
            int syllabusItemMonth = dueDate.getMonthValue();
            int syllabusItemDate = dueDate.getDayOfMonth();
            if(syllabusItemMonth == dateFocus.getMonthValue()) {
                if (!calendarMap.containsKey(syllabusItemDate)) {
                    calendarMap.put(syllabusItemDate, List.of(syllabusItem));
                } else {
                    List<DatedSyllabusEntity> OldListByDate = calendarMap.get(syllabusItemDate);

                    List<DatedSyllabusEntity> newList = new ArrayList<>(OldListByDate);
                    newList.add(syllabusItem);
                    calendarMap.put(syllabusItemDate, newList);
                }
            }
        }
        return  calendarMap;
    }

    public static void main(String[] args) {

    }

    public void buildAccordingToIntentResponse(IntentResponse o) {
        if (!(o instanceof CalendarResponse))
            throw new IntentResponseAndViewControllerCollisionException();

    }
}
