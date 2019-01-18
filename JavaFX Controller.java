import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javafx.application.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class RaspPi extends Application{
	long lastTime = System.currentTimeMillis();
	private Slider SRed = new Slider(0,255,0);
	private Slider SGreen = new Slider(0,255,0);
	private Slider SBlue = new Slider(0,255,0);
	private Slider SRed2 = new Slider(0,255,0);
	private Slider SGreen2 = new Slider(0,255,0);
	private Slider SBlue2 = new Slider(0,255,0);
	
	String status1 = "RGB";
	String status2 = "RGB";
	
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage stage) throws Exception {
		
		stage.setTitle("JavaFX Test application");
		stage.setWidth(800);
		stage.setHeight(480);
		stage.show();
		Pane pane = new Pane();
		
		Rectangle rect1 = new Rectangle(280,10,100,150);
		Rectangle rect2 = new Rectangle(680,10,100,150);
		
		
		Button close = new Button("Exit");
		Button toggle1 = new Button("ON");
		Button toggle2 = new Button("ON");
		close.setLayoutX(625);
		close.setLayoutY(370);
		close.setPrefSize(150, 60);
		toggle1.setLayoutX(290);
		toggle1.setLayoutY(170);
		toggle1.setPrefSize(70, 20);
		toggle1.setStyle("-fx-background-color: green; ");
		toggle2.setLayoutX(690);
		toggle2.setLayoutY(170);
		toggle2.setPrefSize(70, 20);
		toggle2.setStyle("-fx-background-color: green; ");
		
		
		Button fade1 = new Button("Fade");
		Button fade2 = new Button("Fade");
		Button rainbow1 = new Button("Cycle");
		Button rainbow2 = new Button("Cycle");
		Button rgb1 = new Button("RGB");
		Button rgb2 = new Button("RGB");
		Button sparkle1 = new Button("Sparkle");
		Button sparkle2 = new Button("Sparkle");
		fade1.setLayoutX(15);
		fade1.setLayoutY(215);
		fade1.setPrefSize(65, 20);
		fade1.setStyle("-fx-background-color: red; ");
		fade2.setLayoutX(415);
		fade2.setLayoutY(215);
		fade2.setPrefSize(65, 20);
		fade2.setStyle("-fx-background-color: red; ");
		rainbow1.setLayoutX(90);
		rainbow1.setLayoutY(215);
		rainbow1.setPrefSize(80, 20);
		rainbow1.setStyle("-fx-background-color: red; ");
		rainbow2.setLayoutX(490);
		rainbow2.setLayoutY(215);
		rainbow2.setPrefSize(80, 20);
		rainbow2.setStyle("-fx-background-color: red; ");
		rgb1.setLayoutX(180);
		rgb1.setLayoutY(215);
		rgb1.setPrefSize(60, 20);
		rgb1.setStyle("-fx-background-color: green; ");
		rgb2.setLayoutX(580);
		rgb2.setLayoutY(215);
		rgb2.setPrefSize(60, 20);
		rgb2.setStyle("-fx-background-color: green; ");
		sparkle1.setLayoutX(250);
		sparkle1.setLayoutY(215);
		sparkle1.setPrefSize(85, 20);
		sparkle1.setStyle("-fx-background-color: red; ");
		sparkle2.setLayoutX(650);
		sparkle2.setLayoutY(215);
		sparkle2.setPrefSize(85, 20);
		sparkle2.setStyle("-fx-background-color: red; ");
		
		Slider sBrightness1 = new Slider(0, 255, 40);
		Slider sBrightness2 = new Slider(0, 255, 40);
		TextField tfBrightness1 = new TextField("40");
		TextField tfBrightness2 = new TextField("40");
		sBrightness1.setLayoutX(15);
		sBrightness1.setLayoutY(265);
	    sBrightness1.setPrefWidth(200);
		sBrightness2.setLayoutX(415);
		sBrightness2.setLayoutY(265);
	    sBrightness2.setPrefWidth(200);
	    tfBrightness1.setLayoutX(220);
	    tfBrightness1.setLayoutY(260);
	    tfBrightness1.setPrefSize(50, 20);
	    tfBrightness2.setLayoutX(620);
	    tfBrightness2.setLayoutY(260);
	    tfBrightness2.setPrefSize(50, 20);
	    
	    Slider sDist1 = new Slider(5, 200, 50);
	    Slider sDist2 = new Slider(5, 200, 50);
	    TextField tfDist1 = new TextField("50");
	    TextField tfDist2 = new TextField("50");
	    sDist1.setVisible(false);
	    sDist1.setLayoutX(15);
	    sDist1.setLayoutY(345);
	    sDist1.setPrefWidth(200);
	    sDist2.setLayoutX(415);
	    sDist2.setLayoutY(345);
	    sDist2.setPrefWidth(200);
	    sDist2.setVisible(false);
	    tfDist1.setLayoutX(220);
	    tfDist1.setLayoutY(340);
	    tfDist1.setVisible(false);
	    tfDist1.setPrefSize(50, 20);
	    tfDist2.setLayoutX(620);
	    tfDist2.setLayoutY(340);
	    tfDist2.setVisible(false);
	    tfDist2.setPrefSize(50, 20);
	    

		Slider sFade1 = new Slider(10, 200, 30);
		Slider sFade2 = new Slider(10, 200, 30);
		TextField tfFade1 = new TextField("30");
		TextField tfFade2 = new TextField("30");
		sFade1.setLayoutX(15);
		sFade1.setLayoutY(305);
	    sFade1.setPrefWidth(200);
	    sFade1.setVisible(false);
		sFade2.setLayoutX(415);
		sFade2.setLayoutY(305);
	    sFade2.setPrefWidth(200);
	    sFade2.setVisible(false);
	    tfFade1.setLayoutX(220);
	    tfFade1.setLayoutY(300);
	    tfFade1.setPrefSize(50, 20);
	    tfFade1.setVisible(false);
	    tfFade2.setLayoutX(620);
	    tfFade2.setLayoutY(300);
	    tfFade2.setPrefSize(50, 20);
	    tfFade2.setVisible(false);
	    
		Slider sSparkle1 = new Slider(0, 100, 30);
		Slider sSparkle2 = new Slider(0, 100, 30);
		TextField tfSparkle1 = new TextField("30");
		TextField tfSparkle2 = new TextField("30");
		sSparkle1.setLayoutX(15);
		sSparkle1.setLayoutY(305);
	    sSparkle1.setPrefWidth(200);
	    sSparkle1.setVisible(false);
		sSparkle2.setLayoutX(415);
		sSparkle2.setLayoutY(305);
	    sSparkle2.setPrefWidth(200);
	    sSparkle2.setVisible(false);
	    tfSparkle1.setLayoutX(220);
	    tfSparkle1.setLayoutY(300);
	    tfSparkle1.setPrefSize(50, 20);
	    tfSparkle1.setVisible(false);
	    tfSparkle2.setLayoutX(620);
	    tfSparkle2.setLayoutY(300);
	    tfSparkle2.setPrefSize(50, 20);
	    tfSparkle2.setVisible(false);
	    
		
		
		SRed.setLayoutX(15);
	    SRed.setLayoutY(25);
	    SRed.setPrefWidth(200);
	    SRed.setStyle("-fx-control-inner-background: red;");
	    SGreen.setLayoutX(15);
	    SGreen.setLayoutY(75);
	    SGreen.setPrefWidth(200);
	    SGreen.setStyle("-fx-control-inner-background: green;");
	    SBlue.setLayoutX(15);
	    SBlue.setLayoutY(125);
	    SBlue.setPrefWidth(200);
	    SBlue.setStyle("-fx-control-inner-background: blue;");
		SRed2.setLayoutX(415);
	    SRed2.setLayoutY(25);
	    SRed2.setPrefWidth(200);
	    SRed2.setStyle("-fx-control-inner-background: red;");
	    SGreen2.setLayoutX(415);
	    SGreen2.setLayoutY(75);
	    SGreen2.setPrefWidth(200);
	    SGreen2.setStyle("-fx-control-inner-background: green;");
	    SBlue2.setLayoutX(415);
	    SBlue2.setLayoutY(125);
	    SBlue2.setPrefWidth(200);
	    SBlue2.setStyle("-fx-control-inner-background: blue;");
	    
	    
	    TextField tfRed = new TextField();
		TextField tfGreen = new TextField();
		TextField tfBlue = new TextField();
	    TextField tfRed2 = new TextField();
		TextField tfGreen2 = new TextField();
		TextField tfBlue2 = new TextField();
		tfRed.setLayoutX(220);
		tfRed.setLayoutY(20);
		tfRed.setPrefSize(50, 20);
		tfRed.setText("0");
		tfGreen.setLayoutX(220);
		tfGreen.setLayoutY(70);
		tfGreen.setPrefSize(50, 20);
		tfGreen.setText("0");
		tfBlue.setLayoutX(220);
		tfBlue.setLayoutY(120);
		tfBlue.setPrefSize(50, 20);
		tfBlue.setText("0");
		tfRed2.setLayoutX(620);
		tfRed2.setLayoutY(20);
		tfRed2.setPrefSize(50, 20);
		tfRed2.setText("0");
		tfGreen2.setLayoutX(620);
		tfGreen2.setLayoutY(70);
		tfGreen2.setPrefSize(50, 20);
		tfGreen2.setText("0");
		tfBlue2.setLayoutX(620);
		tfBlue2.setLayoutY(120);
		tfBlue2.setPrefSize(50, 20);
		tfBlue2.setText("0");
		
		
		TextField ip11 = new TextField("192");
		TextField ip12 = new TextField("168");
		Spinner<Integer> ip13 = new Spinner<Integer>(0, 255, 0);
		Spinner<Integer> ip14 = new Spinner<Integer>(1, 255, 110);
		CheckBox cbIP1 = new CheckBox();
		ip11.setLayoutX(40);
		ip11.setLayoutY(170);
		ip11.setPrefSize(45, 20);
		ip12.setLayoutX(90);
		ip12.setLayoutY(170);
		ip12.setPrefSize(45, 20);
		ip13.setLayoutX(140);
		ip13.setLayoutY(170);
		ip13.setPrefSize(55, 20);
		ip14.setLayoutX(200);
		ip14.setLayoutY(170);
		ip14.setPrefSize(80, 20);
		cbIP1.setLayoutX(15);
		cbIP1.setLayoutY(175);
		cbIP1.setSelected(true);
		
		
		TextField ip21 = new TextField("192");
		TextField ip22 = new TextField("168");
		Spinner<Integer> ip23 = new Spinner<Integer>(0, 255, 0);
		Spinner<Integer> ip24 = new Spinner<Integer>(1, 255, 111);
		CheckBox cbIP2 = new CheckBox();
		ip21.setLayoutX(440);
		ip21.setLayoutY(170);
		ip21.setPrefSize(45, 20);
		ip22.setLayoutX(490);
		ip22.setLayoutY(170);
		ip22.setPrefSize(45, 20);
		ip23.setLayoutX(540);
		ip23.setLayoutY(170);
		ip23.setPrefSize(55, 20);
		ip24.setLayoutX(600);
		ip24.setLayoutY(170);
		ip24.setPrefSize(80, 20);
		cbIP2.setLayoutX(415);
		cbIP2.setLayoutY(175);
		cbIP2.setSelected(true);
		
		
		pane.getChildren().addAll(sBrightness1, sBrightness2, SRed, SGreen, SBlue, SRed2, SGreen2, SBlue2, sDist1, sDist2, tfDist1, tfDist2, sparkle1, sparkle2, sSparkle1, sSparkle2, tfSparkle1, tfSparkle2, tfBrightness1, tfBrightness2, tfFade1, tfFade2, sFade1, sFade2, tfRed, tfGreen, tfBlue, tfRed2, tfGreen2, tfBlue2, fade1, fade2, rgb1, rgb2, rainbow1, rainbow2, rect1, rect2, ip11, ip12, ip13, ip14, cbIP1, ip21, ip22, ip23, ip24, cbIP2, close, toggle1, toggle2);
		stage.setScene(new Scene(pane, 300, 400));
		pane.setStyle("-fx-background-color: #444444");
//		stage.setAlwaysOnTop(true); 
		
		
		SRed.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				rect1.setFill(Color.rgb((int) SRed.getValue(), (int) SGreen.getValue(), (int) SBlue.getValue()));
				tfRed.setText(String.valueOf((int) SRed.getValue()));
				if((System.currentTimeMillis() - lastTime) > 20) {
					try {
						if(cbIP1.isSelected()) {
							sendPacket(getMessage1(), InetAddress.getByName(ip11.getText() + "." + ip12.getText() + "." + ip13.getValue() + "." + ip14.getValue()), 4210);
							lastTime = System.currentTimeMillis();
						}
					} 
					catch (UnknownHostException e) {
						e.printStackTrace();
					}
				}
	        }
	    });
		SGreen.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				rect1.setFill(Color.rgb((int) SRed.getValue(), (int) SGreen.getValue(), (int) SBlue.getValue()));
				tfGreen.setText(String.valueOf((int) SGreen.getValue()));
				if((System.currentTimeMillis() - lastTime) > 20) {
					try {
						if(cbIP1.isSelected()) {
							sendPacket(getMessage1(), InetAddress.getByName(ip11.getText() + "." + ip12.getText() + "." + ip13.getValue() + "." + ip14.getValue()), 4210);
							lastTime = System.currentTimeMillis();
						}
					} 
					catch (UnknownHostException e) {
						e.printStackTrace();
					}
				}
	        }
	    });
		SBlue.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				rect1.setFill(Color.rgb((int) SRed.getValue(), (int) SGreen.getValue(), (int) SBlue.getValue()));
				tfBlue.setText(String.valueOf((int) SBlue.getValue()));
				if((System.currentTimeMillis() - lastTime) > 20) {
					try {
						if(cbIP1.isSelected()) {
							sendPacket(getMessage1(), InetAddress.getByName(ip11.getText() + "." + ip12.getText() + "." + ip13.getValue() + "." + ip14.getValue()), 4210);
							lastTime = System.currentTimeMillis();
						}
					} 
					catch (UnknownHostException e) {
						e.printStackTrace();
					}
				}
	        }
	    });
		
		SRed2.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				rect2.setFill(Color.rgb((int) SRed2.getValue(), (int) SGreen2.getValue(), (int) SBlue2.getValue()));
				tfRed2.setText(String.valueOf((int) SRed2.getValue()));
				if((System.currentTimeMillis() - lastTime) > 20) {
					try {
						if(cbIP2.isSelected()) {
							sendPacket(getMessage2(), InetAddress.getByName(ip21.getText() + "." + ip22.getText() + "." + ip23.getValue() + "." + ip24.getValue()), 4210);
							lastTime = System.currentTimeMillis();
						}
					} 
					catch (UnknownHostException e) {
						e.printStackTrace();
					}
				}
	        }
	    });
		SGreen2.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				rect2.setFill(Color.rgb((int) SRed2.getValue(), (int) SGreen2.getValue(), (int) SBlue2.getValue()));
				tfGreen2.setText(String.valueOf((int) SGreen2.getValue()));
				if((System.currentTimeMillis() - lastTime) > 20) {
					try {
						if(cbIP2.isSelected()) {
							sendPacket(getMessage2(), InetAddress.getByName(ip21.getText() + "." + ip22.getText() + "." + ip23.getValue() + "." + ip24.getValue()), 4210);
							lastTime = System.currentTimeMillis();
						}
					} 
					catch (UnknownHostException e) {
						e.printStackTrace();
					}
				}
	        }
	    });
		SBlue2.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				rect2.setFill(Color.rgb((int) SRed2.getValue(), (int) SGreen2.getValue(), (int) SBlue2.getValue()));
				tfBlue2.setText(String.valueOf((int) SBlue2.getValue()));
				if((System.currentTimeMillis() - lastTime) > 20) {
					try {
						if(cbIP2.isSelected()) {
							sendPacket(getMessage2(), InetAddress.getByName(ip21.getText() + "." + ip22.getText() + "." + ip23.getValue() + "." + ip24.getValue()), 4210);
							lastTime = System.currentTimeMillis();
						}
					} 
					catch (UnknownHostException e) {
						e.printStackTrace();
					}
				}
	        }
	    });
		sBrightness1.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				tfBrightness1.setText(String.valueOf((int) sBrightness1.getValue()));
				if((System.currentTimeMillis() - lastTime) > 20) {
					try {
						if(cbIP1.isSelected()) {
							sendPacket(("B" + tfBrightness1.getText()).getBytes(), InetAddress.getByName(ip11.getText() + "." + ip12.getText() + "." + ip13.getValue() + "." + ip14.getValue()), 4210);
							lastTime = System.currentTimeMillis();
						}
					} 
					catch (UnknownHostException e) {
						e.printStackTrace();
					}
				}
			}
	    });
		sBrightness2.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				tfBrightness2.setText(String.valueOf((int) sBrightness2.getValue()));
				if((System.currentTimeMillis() - lastTime) > 20) {
					try {
						if(cbIP2.isSelected()) {
							sendPacket(("B" + tfBrightness2.getText()).getBytes(), InetAddress.getByName(ip21.getText() + "." + ip22.getText() + "." + ip23.getValue() + "." + ip24.getValue()), 4210);
							lastTime = System.currentTimeMillis();
						}
					} 
					catch (UnknownHostException e) {
						e.printStackTrace();
					}
				}
	        }
	    });
		sFade1.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				tfFade1.setText(String.valueOf((int) sFade1.getValue()));
				if((System.currentTimeMillis() - lastTime) > 20) {
					try {
						if(cbIP1.isSelected()) {
							sendPacket(("F" + tfFade1.getText()).getBytes(), InetAddress.getByName(ip11.getText() + "." + ip12.getText() + "." + ip13.getValue() + "." + ip14.getValue()), 4210);
							lastTime = System.currentTimeMillis();
						}
					} 
					catch (UnknownHostException e) {
						e.printStackTrace();
					}
				}
	        }
	    });
		sFade2.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				tfFade2.setText(String.valueOf((int) sFade2.getValue()));
				if((System.currentTimeMillis() - lastTime) > 20) {
					try {
						if(cbIP2.isSelected()) {
							sendPacket(("F" + tfFade2.getText()).getBytes(), InetAddress.getByName(ip21.getText() + "." + ip22.getText() + "." + ip23.getValue() + "." + ip24.getValue()), 4210);
							lastTime = System.currentTimeMillis();
						}
					} 
					catch (UnknownHostException e) {
						e.printStackTrace();
					}
				}
	        }
	    });
		
		
		sDist1.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				tfDist1.setText(String.valueOf((int) sDist1.getValue()));
				if((System.currentTimeMillis() - lastTime) > 20) {
					try {
						if(cbIP1.isSelected()) {
							sendPacket(("D" + tfDist1.getText()).getBytes(), InetAddress.getByName(ip11.getText() + "." + ip12.getText() + "." + ip13.getValue() + "." + ip14.getValue()), 4210);
							lastTime = System.currentTimeMillis();
						}
					} 
					catch (UnknownHostException e) {
						e.printStackTrace();
					}
				}
	        }
	    });
		sDist2.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				tfDist2.setText(String.valueOf((int) sDist2.getValue()));
				if((System.currentTimeMillis() - lastTime) > 20) {
					try {
						if(cbIP2.isSelected()) {
							sendPacket(("D" + tfDist2.getText()).getBytes(), InetAddress.getByName(ip21.getText() + "." + ip22.getText() + "." + ip23.getValue() + "." + ip24.getValue()), 4210);
							lastTime = System.currentTimeMillis();
						}
					} 
					catch (UnknownHostException e) {
						e.printStackTrace();
					}
				}
	        }
	    });
		
		
		sSparkle1.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				tfSparkle1.setText(String.valueOf((int) sSparkle1.getValue()));
				if((System.currentTimeMillis() - lastTime) > 20) {
					try {
						if(cbIP1.isSelected()) {
							sendPacket(("S" + tfSparkle1.getText()).getBytes(), InetAddress.getByName(ip11.getText() + "." + ip12.getText() + "." + ip13.getValue() + "." + ip14.getValue()), 4210);
							lastTime = System.currentTimeMillis();
						}
					} 
					catch (UnknownHostException e) {
						e.printStackTrace();
					}
				}
	        }
	    });
		sSparkle2.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				tfSparkle2.setText(String.valueOf((int) sSparkle2.getValue()));
				if((System.currentTimeMillis() - lastTime) > 20) {
					try {
						if(cbIP2.isSelected()) {
							sendPacket(("S" + tfSparkle2.getText()).getBytes(), InetAddress.getByName(ip21.getText() + "." + ip22.getText() + "." + ip23.getValue() + "." + ip24.getValue()), 4210);
							lastTime = System.currentTimeMillis();
						}
					} 
					catch (UnknownHostException e) {
						e.printStackTrace();
					}
				}
	        }
	    });
		
		
		
		close.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				System.exit(1);
			}
		});
		
		toggle1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				try {
					if (cbIP1.isSelected()) {
						switch (toggle1.getText()) {
						case "ON":
							toggle1.setText("OFF");
							toggle1.setStyle("-fx-background-color: red; ");
							break;
						case "OFF":
							toggle1.setText("ON");
							toggle1.setStyle("-fx-background-color: green; ");
							break;
						default:
							toggle1.setText("ERROR");
							break;
						}
						sendPacket(toggle1.getText().getBytes(), InetAddress.getByName(ip11.getText() + "." + ip12.getText() + "." + ip13.getValue() + "." + ip14.getValue()), 4210);
					}
				} 
				catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}
		});
		toggle2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				try {
					if (cbIP2.isSelected()) {
						switch (toggle2.getText()) {
						case "ON":
							toggle2.setText("OFF");
							toggle2.setStyle("-fx-background-color: red; ");
							break;
						case "OFF":
							toggle2.setText("ON");
							toggle2.setStyle("-fx-background-color: green; ");
							break;
						default:
							toggle2.setText("ERROR");
							break;
						}
						sendPacket(toggle2.getText().getBytes(), InetAddress.getByName(ip21.getText() + "." + ip22.getText() + "." + ip23.getValue() + "." + ip24.getValue()), 4210);
					}
					
				} 
				catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}
		});
		
		rgb1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(cbIP1.isSelected()) {
					status1 = "RGB";
					rgb1.setStyle("-fx-background-color: green; ");
					rainbow1.setStyle("-fx-background-color: red; ");
					fade1.setStyle("-fx-background-color: red; ");
					sparkle1.setStyle("-fx-background-color: red; ");
					tfFade1.setVisible(false);
					sFade1.setVisible(false);
					sSparkle1.setVisible(false);
					tfSparkle1.setVisible(false);
					sDist1.setVisible(false);
					tfDist1.setVisible(false);
					try {
						sendPacket(("M3").getBytes(), InetAddress.getByName(ip11.getText() + "." + ip12.getText() + "." + ip13.getValue() + "." + ip14.getValue()), 4210);
						sendPacket(getMessage1(), InetAddress.getByName(ip11.getText() + "." + ip12.getText() + "." + ip13.getValue() + "." + ip14.getValue()), 4210);
					} 
					catch (UnknownHostException e) {
						e.printStackTrace();
					}
				}
			}
		});
		rgb2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(cbIP2.isSelected()) {
					status2 = "RGB";
					rgb2.setStyle("-fx-background-color: green; ");
					rainbow2.setStyle("-fx-background-color: red; ");
					fade2.setStyle("-fx-background-color: red; ");
					sparkle2.setStyle("-fx-background-color: red; ");
					tfFade2.setVisible(false);
					sFade2.setVisible(false);
					sSparkle2.setVisible(false);
					tfSparkle2.setVisible(false);
					sDist2.setVisible(false);
					tfDist2.setVisible(false);
					try {
						sendPacket(("M3").getBytes(), InetAddress.getByName(ip21.getText() + "." + ip22.getText() + "." + ip23.getValue() + "." + ip24.getValue()), 4210);
						sendPacket(getMessage2(), InetAddress.getByName(ip21.getText() + "." + ip22.getText() + "." + ip23.getValue() + "." + ip24.getValue()), 4210);
					} 
					catch (UnknownHostException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		rainbow1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(cbIP1.isSelected()) {
					status1 = "Rainbow";
					rgb1.setStyle("-fx-background-color: red; ");
					rainbow1.setStyle("-fx-background-color: green; ");
					fade1.setStyle("-fx-background-color: red; ");
					sparkle1.setStyle("-fx-background-color: red; ");
					tfFade1.setVisible(true);
					sFade1.setVisible(true);
					sSparkle1.setVisible(false);
					tfSparkle1.setVisible(false);
					sDist1.setVisible(true);
					tfDist1.setVisible(true);
					try {
						sendPacket(("M2").getBytes(), InetAddress.getByName(ip11.getText() + "." + ip12.getText() + "." + ip13.getValue() + "." + ip14.getValue()), 4210);
						sendPacket(("F" + String.valueOf((int) sFade1.getValue())).getBytes(), InetAddress.getByName(ip11.getText() + "." + ip12.getText() + "." + ip13.getValue() + "." + ip14.getValue()), 4210);
					} 
					catch (UnknownHostException e) {
						e.printStackTrace();
					}
				}
			}
		});
		rainbow2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(cbIP2.isSelected()) {
					status2 = "Rainbow";
					rgb2.setStyle("-fx-background-color: red; ");
					rainbow2.setStyle("-fx-background-color: green; ");
					fade2.setStyle("-fx-background-color: red; ");
					sparkle2.setStyle("-fx-background-color: red; ");
					tfFade2.setVisible(true);
					sFade2.setVisible(true);
					sSparkle2.setVisible(false);
					tfSparkle2.setVisible(false);
					sDist2.setVisible(true);
					tfDist2.setVisible(true);
					try {
						sendPacket(("M2").getBytes(), InetAddress.getByName(ip21.getText() + "." + ip22.getText() + "." + ip23.getValue() + "." + ip24.getValue()), 4210);
						sendPacket(("F" + String.valueOf((int) sFade2.getValue())).getBytes(), InetAddress.getByName(ip21.getText() + "." + ip22.getText() + "." + ip23.getValue() + "." + ip24.getValue()), 4210);
					} 
					catch (UnknownHostException e) {
						e.printStackTrace();
					}
				}
			}
		});
		fade1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(cbIP1.isSelected()) {
					status1 = "Fade";
					rgb1.setStyle("-fx-background-color: red; ");
					rainbow1.setStyle("-fx-background-color: red; ");
					fade1.setStyle("-fx-background-color: green; ");
					sparkle1.setStyle("-fx-background-color: red; ");
					tfFade1.setVisible(true);
					sFade1.setVisible(true);
					sSparkle1.setVisible(false);
					tfSparkle1.setVisible(false);
					sDist1.setVisible(false);
					tfDist1.setVisible(false);
					try {
						sendPacket(("M1").getBytes(), InetAddress.getByName(ip11.getText() + "." + ip12.getText() + "." + ip13.getValue() + "." + ip14.getValue()), 4210);
						sendPacket(("F" + tfFade1.getText()).getBytes(), InetAddress.getByName(ip11.getText() + "." + ip12.getText() + "." + ip13.getValue() + "." + ip14.getValue()), 4210);
					} 
					catch (UnknownHostException e) {
						e.printStackTrace();
					}
				}
			}
		});
		fade2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(cbIP2.isSelected()) {
					status2 = "Fade";
					rgb2.setStyle("-fx-background-color: red; ");
					rainbow2.setStyle("-fx-background-color: red; ");
					fade2.setStyle("-fx-background-color: green; ");
					sparkle2.setStyle("-fx-background-color: red; ");
					tfFade2.setVisible(true);
					sFade2.setVisible(true);
					sSparkle2.setVisible(false);
					tfSparkle2.setVisible(false);
					sDist2.setVisible(false);;
					tfDist2.setVisible(false);
					try {
						sendPacket(("M1").getBytes(), InetAddress.getByName(ip21.getText() + "." + ip22.getText() + "." + ip23.getValue() + "." + ip24.getValue()), 4210);
						sendPacket(("F" + tfFade2.getText()).getBytes(), InetAddress.getByName(ip21.getText() + "." + ip22.getText() + "." + ip23.getValue() + "." + ip24.getValue()), 4210);
					} 
					catch (UnknownHostException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		sparkle1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(cbIP1.isSelected()) {
					status1 = "Rainbow";
					rgb1.setStyle("-fx-background-color: red; ");
					rainbow1.setStyle("-fx-background-color: red; ");
					fade1.setStyle("-fx-background-color: red; ");
					sparkle1.setStyle("-fx-background-color: green; ");
					tfFade1.setVisible(false);
					sFade1.setVisible(false);
					sSparkle1.setVisible(true);
					tfSparkle1.setVisible(true);
					sDist1.setVisible(false);
					tfDist1.setVisible(false);
					try {
						sendPacket(("M4").getBytes(), InetAddress.getByName(ip11.getText() + "." + ip12.getText() + "." + ip13.getValue() + "." + ip14.getValue()), 4210);
						sendPacket(("S" + String.valueOf((int) sSparkle1.getValue())).getBytes(), InetAddress.getByName(ip11.getText() + "." + ip12.getText() + "." + ip13.getValue() + "." + ip14.getValue()), 4210);
					} 
					catch (UnknownHostException e) {
						e.printStackTrace();
					}
				}
			}
		});
		sparkle2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(cbIP2.isSelected()) {
					status2 = "Rainbow";
					rgb2.setStyle("-fx-background-color: red; ");
					rainbow2.setStyle("-fx-background-color: red; ");
					fade2.setStyle("-fx-background-color: red; ");
					sparkle2.setStyle("-fx-background-color: green; ");
					tfFade2.setVisible(false);
					sFade2.setVisible(false);
					sSparkle2.setVisible(true);
					tfSparkle2.setVisible(true);
					sDist2.setVisible(false);
					tfDist2.setVisible(false);
					try {
						sendPacket(("S" + String.valueOf((int) sSparkle2.getValue())).getBytes(), InetAddress.getByName(ip21.getText() + "." + ip22.getText() + "." + ip23.getValue() + "." + ip24.getValue()), 4210);
					} 
					catch (UnknownHostException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		
	}
	
	public static void sendPacket(byte[] message, InetAddress ipAddress, int port) {
		try {
			DatagramPacket packet = new DatagramPacket(message, message.length, ipAddress, port);
			DatagramSocket dsocket = new DatagramSocket();
			dsocket.send(packet);
			dsocket.disconnect();
			dsocket.close();
			String output = new String(message);
			System.out.println(output);
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	private byte[] getMessage1() {
		String red = "";
		String green = "";
		String blue = "";
		
		if((int) SRed.getValue() < 10) {
			red = "00" + (int)SRed.getValue();
		}
		if((int) SRed.getValue() >= 10 && (int) SRed.getValue() < 100) {
			red = "0" + (int)SRed.getValue();
		}
		if((int) SRed.getValue() >= 100) {
			red = "" + (int)SRed.getValue();
		}
		
		if((int) SGreen.getValue() < 10) {
			green = "00" + (int)SGreen.getValue();
		}
		if((int) SGreen.getValue() >= 10 && (int) SGreen.getValue() < 100) {
			green = "0" + (int)SGreen.getValue();
		}
		if((int) SGreen.getValue() >= 100) {
			green = "" + (int)SGreen.getValue();
		}
		
		if((int) SBlue.getValue() < 10) {
			blue = "00" + (int)SBlue.getValue();
		}
		if((int) SBlue.getValue() >= 10 && (int) SBlue.getValue() < 100) {
			blue = "0" + (int)SBlue.getValue();
		}
		if((int) SBlue.getValue() >= 100) {
			blue = "" + (int)SBlue.getValue();
		}
		
		return (red + "," + green + "," + blue).getBytes();
	}
	private byte[] getMessage2() {
		String red = "";
		String green = "";
		String blue = "";
		
		if((int) SRed2.getValue() < 10) {
			red = "00" + (int)SRed2.getValue();
		}
		if((int) SRed2.getValue() >= 10 && (int) SRed2.getValue() < 100) {
			red = "0" + (int)SRed2.getValue();
		}
		if((int) SRed2.getValue() >= 100) {
			red = "" + (int)SRed2.getValue();
		}
		
		if((int) SGreen2.getValue() < 10) {
			green = "00" + (int)SGreen2.getValue();
		}
		if((int) SGreen2.getValue() >= 10 && (int) SGreen2.getValue() < 100) {
			green = "0" + (int)SGreen2.getValue();
		}
		if((int) SGreen2.getValue() >= 100) {
			green = "" + (int)SGreen2.getValue();
		}
		
		if((int) SBlue2.getValue() < 10) {
			blue = "00" + (int)SBlue2.getValue();
		}
		if((int) SBlue2.getValue() >= 10 && (int) SBlue2.getValue() < 100) {
			blue = "0" + (int)SBlue2.getValue();
		}
		if((int) SBlue2.getValue() >= 100) {
			blue = "" + (int)SBlue2.getValue();
		}
		
		return (red + "," + green + "," + blue).getBytes();
	}
}
