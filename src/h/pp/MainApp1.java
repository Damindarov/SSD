package h.pp;

import java.io.IOException;

import h.pp.model.Point;
import h.pp.view.Controller_view;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
public class MainApp1 extends Application {
	public static Stage primaryStage;
    private BorderPane rootLayout;
    @Override
    

    public void start(Stage primaryStage) {
        MainApp1.primaryStage = primaryStage;
        MainApp1.primaryStage.setTitle("ScanApp");
        initRootLayout();
        showMainSceen();
        
        
    }
    private ObservableList<Point> personData = FXCollections.observableArrayList();
    public static void main(String[] args) {
    	launch(args);
    } 
    /**
     * Возвращает данные в виде наблюдаемого списка адресатов.
     * @return
     */
    public ObservableList<Point> getPersonData() {
        return personData;
    }
    /**
     * Инициализирует корневой макет.
     */
    
    public void initRootLayout() {
        try {
            // Загружаем корневой макет из fxml файла.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp1.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            
            // Отображаем сцену, содержащую корневой макет.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Возвращает главную сцену, испрравил на статик, EditTableZond .
     * @return
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
    /**
     * Показывает в корневом макете сведения об точках спектрометра.
     */
    public void showMainSceen() {
        try {
            // Загружаем UI.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp1.class.getResource("view/View.fxml"));
            AnchorPane Table_spectr = (AnchorPane) loader.load();
            // Помещаем сведения об точках в правый край корневого макета.
            rootLayout.setCenter(Table_spectr);
            Controller_view controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
}
