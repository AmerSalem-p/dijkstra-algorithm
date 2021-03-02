package Project3;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Driver extends Application {

	static String cityPath = "";
	static String cityWeight = "";
	static Graph graph;
	static BorderPane pane = new BorderPane();
	static ComboBox<String> combo1;
	static ComboBox<String> combo2;
	static TextArea Area;
	static int numOfCircles = 0;
	static Pane pane2 = new Pane();

	@SuppressWarnings("exports")
	public void start(Stage s) throws Exception {

		Label Label1 = new Label("   Palestine");
		Label1.setFont(new Font(50));
		Label1.setTextFill(Color.CORNFLOWERBLUE);

		Button B2 = new Button("Start");
		Button B3 = new Button("Distance");
		Button B4 = new Button("Show City Path");
		Button B5 = new Button("Clear map");
		Button B6 = new Button("Reset");
		Button B7 = new Button("Exit");

		B2.setMinWidth(60);
		B5.setMinWidth(90);
		B6.setMinWidth(90);
		B7.setMinWidth(90);

		combo1 = new ComboBox<>();
		combo2 = new ComboBox<>();
		combo1.setPromptText("Source City");
		combo2.setPromptText("Target City");

		TextField Field1 = new TextField();
		Field1.setPromptText("Distance");
		Field1.setAlignment(Pos.CENTER);
		Field1.setMaxWidth(100);

		Area = new TextArea();
		Area.setMaxSize(300, 300);

		Image Image1 = new Image("Palestine.png");
		ImageView Image2 = new ImageView(Image1);

		HBox H2 = new HBox(B3, Field1);
		HBox H3 = new HBox(combo1, combo2);
		HBox H4 = new HBox(B5, B6, B7);
		VBox V1 = new VBox(Label1, H3, B2, H2, B4, Area, H4);

		pane2.getChildren().add(Image2);
		pane.setRight(V1);
		pane.setLeft(pane2);

		H2.setSpacing(5);
		H3.setSpacing(10);
		H4.setSpacing(10);
		V1.setSpacing(15);
		V1.setPadding(new Insets(10, 10, 10, 10));

		Field1.setEditable(false);
		Area.setEditable(false);

		getFromFile();
		addPointsToMap();
		MouseClick();

		B2.setOnAction(e -> {
			try {
				graph.initilizeTable(graph.index(combo1.getValue()));
				graph.CalculateDistance();
			} catch (Exception x) {
				Alert a = new Alert(AlertType.ERROR);
				a.setHeaderText("Please Select your source and Target City");
				a.showAndWait();
			}
		});

		B3.setOnAction(e -> {
			try {
				Vertex x = graph.findVertex(combo2.getValue());
				getPath(graph.findVertex(combo2.getValue()));

				if(Integer.parseInt(cityWeight)==Integer.MAX_VALUE) {
					Field1.setText("No Path");
				}
				else
				Field1.setText(String.valueOf(x.table.getWeight()) + " KM");
			} catch (Exception x) {
				Alert a = new Alert(AlertType.ERROR);
				a.setHeaderText("Please Select your source and Target City");
				a.showAndWait();
			}

		});

		B4.setOnAction(e -> {
			try {
				cityPath = "";
				cityWeight = "";
				Area.clear();
				getPath(graph.findVertex(combo2.getValue()));
				if(Integer.parseInt(cityWeight)==Integer.MAX_VALUE) {
					cityPath = "No Path To Show";
					cityWeight = "unknown";
					Area.setText(cityPath);
					Area.appendText("\n" + "Distance = " + cityWeight );
				}
				else {
				Area.setText(cityPath);
				Area.appendText("\n" + "Distance = " + cityWeight + " KM");
				addLines(graph.findVertex(combo2.getValue()));
				}
			} catch (Exception x) {
				Area.setText("No Path To Show");
			}
		});

		B5.setOnAction(e -> {

			pane2 = new Pane();
			pane2.getChildren().add(Image2);
			pane.setLeft(pane2);
			addPointsToMap();
			numOfCircles = 0;

		});
		B6.setOnAction(e -> {

			combo1.getSelectionModel().clearSelection();
			combo2.getSelectionModel().clearSelection();

			Area.clear();
			Field1.clear();
			numOfCircles = 0;

		});

		B7.setOnAction(e -> {
			System.exit(0);
		});
		Scene scene = new Scene(pane, 900, 790);
		s.setScene(scene);
		s.setTitle("Palestine Map");
		s.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public static void getFromFile() {
		graph = new Graph();
		graph.readFromFile("C:\\Users\\ryze\\eclipse-workspace\\Algorithm-project#3\\src\\Cities.txt");
		for (int i = 0; i < graph.numOfVertex; i++) {
			combo1.getItems().add(graph.vertex[i].getName());
			combo2.getItems().add(graph.vertex[i].getName());
		}
	}

	public void MouseClick() {

		pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if (numOfCircles < 2) {

					for (int i = 0; i < graph.numOfVertex; i++) {

						if (graph.vertex[i].getX() + 10 > event.getX() && graph.vertex[i].getX() - 10 < event.getX()
								&& graph.vertex[i].getY() + 10 > event.getY()
								&& graph.vertex[i].getY() - 10 < event.getY()) {
							if (graph.vertex[i].circle.getFill().equals(Color.BLACK)) {
								numOfCircles++;
								graph.vertex[i].circle.setFill(Color.YELLOW);
								if (numOfCircles == 1)
									combo1.setValue(graph.vertex[i].getName());
								if (numOfCircles == 2)
									combo2.setValue(graph.vertex[i].getName());
							}
							break;

						}

					}
				}
			}

		});
	}

	public static void getPath(Vertex v) {

		if (v.table.getPath() != null) {
			getPath(v.table.getPath());
			cityPath += "-->";
		}

		cityPath += v.getName();
		cityWeight = v.table.getWeight() + "";

	}

	public static void addLines(Vertex v) {
		Line line = new Line();
		if (v.table.getPath() != null) {
			line.setStartX(v.getX());
			line.setStartY(v.getY());
			line.setEndX(v.table.getPath().getX());
			line.setEndY(v.table.getPath().getY());
			addLines(v.table.getPath());
		}

		pane2.getChildren().add(line);
	}

	public static void addPointsToMap() {
		for (int i = 0; i < graph.numOfVertex; i++) {
			Circle c = new Circle(5);
			c.setCenterX(graph.vertex[i].getX());
			c.setCenterY(graph.vertex[i].getY());
			c.setFill(Color.BLACK);
			graph.vertex[i].circle = c;
			pane.getChildren().add(graph.vertex[i].circle);
		}

	}

}
