package Form;


import java.util.concurrent.TimeUnit;

import LineChart.LineChartApplication;
import financeCalculation.DataProxyService;
import financeCalculation.ExpenseObject;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class InformationForm extends Application {
	private Stage primaryStage;
	private GridPane grid;
	private DataProxyService dataProxyService;

	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			primaryStage.setTitle("Expense Visualizer");
			dataProxyService = new DataProxyService();
			
			showMainView();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void showMainView() {	
		grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		implementUI();

	
		Scene scene = new Scene(grid);
		// scene.getStylesheets().add(getClass().getResource("informationForm.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void implementUI() {
		Text scenetitle = new Text("Expense Visualizer");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);
		
		
		
		Label sn = new Label("Service Name:");
		grid.add(sn, 0, 1);

		TextField serviceName = new TextField();
		grid.add(serviceName, 1, 1);
		
		Label cra = new Label("Charge Rate:");
		grid.add(cra, 0, 2);
		
		TextField chargeRateAmount = new TextField();
		grid.add(chargeRateAmount, 1, 2);
		

		ObservableList<String> options = 
			    FXCollections.observableArrayList(
			    	ExpenseObject.ONE_TIME,
			        ExpenseObject.DAILY,
			        ExpenseObject.MONTHLY,
			        ExpenseObject.YEARLY
			    );
			final ComboBox chargeRateDropDown = new ComboBox(options);
			grid.add(chargeRateDropDown, 2, 2);
		
			
		
		
		Button btn = new Button("Add Expense");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.CENTER_RIGHT);
		hbBtn.getChildren().add(btn);
		btn.setMinWidth(120);
		grid.add(hbBtn, 1, 5);
		
		
		Button btnSubmission = new Button("Visualize!");
		HBox hbBtnSubmission = new HBox(10);
		hbBtnSubmission.setAlignment(Pos.CENTER_RIGHT);
		hbBtnSubmission.getChildren().add(btnSubmission);
		btnSubmission.setMinWidth(120);
		grid.add(hbBtnSubmission, 1, 6);

		// message box
		final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 7);
        
        
        final Separator separator = new Separator();
        separator.setHalignment(HPos.LEFT);
        grid.add(separator, 0, 9);
        final Separator separator2 = new Separator();
        separator2.setHalignment(HPos.LEFT);
        grid.add(separator2, 1, 9);
        
        
        Label dod = new Label("Duration of data:");
		grid.add(dod, 0, 11);
		
		TextField durationOfData = new TextField("36");
		grid.add(durationOfData, 1, 11);
		
		Label dodSupportLabel = new Label("month(s)");
		grid.add(dodSupportLabel, 2, 11);
		dodSupportLabel.setAlignment(Pos.CENTER_LEFT);
		
		Button btnSettingsSubmit = new Button("Submit Settings");
		HBox hbSsBtn = new HBox(10);
		hbSsBtn.setAlignment(Pos.CENTER_RIGHT);
		hbSsBtn.getChildren().add(btnSettingsSubmit);
		grid.add(hbSsBtn, 1, 12);
		
		// settings message box
		final Text settingstarget = new Text();
        grid.add(settingstarget, 1, 13);
        
		
		// updating settings
		btnSettingsSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	try {
	            	if(durationOfData.textProperty().isEmpty().getValue())
	            	{
		                settingstarget.setFill(Color.FIREBRICK);
		                settingstarget.setText("Please fill all settings information");
	            	}
	            	else {
	            		int duration = Integer.parseInt(durationOfData.textProperty().getValue());
	            		
	            		
	            		dataProxyService.setAmountOfMonths(duration);
	            		
	            		settingstarget.setFill(Color.LIMEGREEN);
	            		settingstarget.setText("Updated!");
	            		
	            		// clear text fields
	            		durationOfData.clear();
	            	}
            	}
            	catch(Exception e1) {
            		e1.printStackTrace();
            	}
            }
        });
        
        // adding
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	try {
	            	if(serviceName.textProperty().isEmpty().getValue() ||
	            		chargeRateAmount.textProperty().isEmpty().getValue() ||
	            		chargeRateDropDown.getValue() == null)
	            		{
		                actiontarget.setFill(Color.FIREBRICK);
		                actiontarget.setText("Please fill all information");
	            	}
	            	else {
	            		String name = serviceName.textProperty().getValue();
	            		double amount = Double.parseDouble(chargeRateAmount.textProperty().getValue());
	            		String rate = chargeRateDropDown.getValue().toString();
	            		
	            		
	            		dataProxyService.addExpenseObject(name, amount, rate);
	            		
	            		actiontarget.setFill(Color.LIMEGREEN);
	            		actiontarget.setText("Added!");
	            		
	            		// clear text fields
	            		serviceName.clear();
	            		chargeRateAmount.clear();
	            	}
            	}
            	catch(Exception e1) {
            		e1.printStackTrace();
            	}
            }
        });
        
        // visualization
        btnSubmission.setOnAction(new EventHandler<ActionEvent>() {
       	 
            @SuppressWarnings("unused")
			@Override
            public void handle(ActionEvent e) {
            	if(false) {
            		// check expense component exist logic
	                actiontarget.setText("Please enter at least one expense component");
            	}
            	else {
            		// visualize
            		LineChartApplication lca = new LineChartApplication(dataProxyService);
            		try {
						lca.start(new Stage());
						
						// clear text fields
	            		serviceName.clear();
	            		chargeRateAmount.clear();
	            		durationOfData.clear();
	            		
	            		// reset data proxy service instance
	            		dataProxyService = new DataProxyService();
	            		actiontarget.setFill(Color.ORANGE);
	            		actiontarget.setText("Data has been reset");
	            		
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
            	}	
            }
        });
        
        chargeRateAmount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d*")) {
                    chargeRateAmount.setText(newValue.replaceAll("[^\\d.]", ""));
                }
            }

        });
        
        // text hover handler
        /*
        sn.setOnMouseMoved(new EventHandler<MouseEvent>() {
        	@Override
        	public void handle(MouseEvent e) {
        		actiontarget.setText("Incorrect Information");
        	}
        });*/
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Application.launch(args);
	}

}
