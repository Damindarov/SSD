package h.pp.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.prefs.Preferences;

import h.pp.MainApp1;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class Controller_view {
	private MainApp1 mainApp;
	private static SerialPort serialPort;
	long time_start = 0;
	static int mass_arr = 0;
	int num_write = 0;
	public long mass_time[];
	public int l = 0;
	public ArrayList<Float> x1 = new ArrayList<>();
	public List<ArrayList<Float>> words = new ArrayList<ArrayList<Float>>();
	byte[] data;
	Byte j1 = 0;
	int count = 0;
	float l1[] = new float[17];
	List<float[]> rowList = new ArrayList<float[]>();
	@FXML
    public Button buttonStart;	
	@FXML
    public Button buttonStop;
	@FXML
    public Button buttonShow;
	@FXML
    private TextField FilePath;
	@FXML
    private static TextField COM;
	@FXML
    public TextField Bytes_field;
	@FXML
    private ComboBox <String> portBox;
	@FXML
	private ComboBox <String> paramBox;
	NumberAxis x = new NumberAxis();
	NumberAxis y = new NumberAxis();
	@FXML
	ScatterChart<Number,Number> scatterChart = new ScatterChart<>(x,y);
	XYChart.Series s_ax = new XYChart.Series();//ax
	XYChart.Series s_ay = new XYChart.Series();//ax
	/**
     * Конструктор вызывается раньше метода initialize().
     * Конструктор.
     */
	public Controller_view() {
		
	}
	/**
     * Инициализация класса-контроллера. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
    	FilePath.setText(getPersonFilePath().getAbsolutePath());
    	String[] portNames = SerialPortList.getPortNames();
    	String[] paramNames = {"Ax","Ay","Az","Mx","My","Mz","Gx","Gy","Gz"};
    	paramBox.getItems().setAll(paramNames);
    	paramBox.getSelectionModel().selectFirst();
    	portBox.getItems().setAll(portNames); 	
    	buttonShow.setDisable(true);
    }
    /**
     * Вызывается главным приложением, которое даёт на себя ссылку.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp1 mainApp) {
        this.mainApp = mainApp;
    }
    @FXML
    private void handleOpentxt() throws IOException {
    	FileChooser fc = new FileChooser();//Создаем chooser для создания окна выбора
       	fc.getExtensionFilters().addAll(
       			new ExtensionFilter("TEXT files (*.txt)", "*.txt"));//Добавляю форматы
      	File selectedFile = fc.showOpenDialog(null);
      	if(selectedFile != null) {//Если файл выбран
      		setPersonFilePath(selectedFile);//Забиваем путь в реестр, что бы само открывалось
    	    FilePath.setText(selectedFile.getAbsolutePath());//Прописываем путь в строке рядом с кнопкой выбора
    	 }else{
    	    		//здесь выпадает окно ошибки
    	    		// Ничего не выбрано.
    	            Alert alert = new Alert(AlertType.WARNING);
    	            alert.initOwner(MainApp1.getPrimaryStage());
    	            alert.setTitle("No Selection");
    	            alert.setHeaderText("No File Selected");
    	            alert.setContentText("Please select a file");
    	            alert.showAndWait();
    	    	}	
    	    }
    /**
     * Set_time_start and after connect with controller and disable
     * button start
     */
    @FXML
    public void handleStart() {
    	clear_file();
    	time_start = System.currentTimeMillis();//
    	connect();
    	buttonStart.setDisable(true);
    }
    /**
     * if you don't connect controller before open APP,
     * you can connect after open APP and toгеch this button
     */
    @FXML
    private void handleRefind() {
		String[] portNames = SerialPortList.getPortNames();
		portBox.getItems().setAll(portNames);
    }
    @FXML
    private void handleShow_Param() {
		//paramBox.getItems().setAll();
		String a = paramBox.getValue();
		switch (a) {
			case ("Ax"):{
				System.out.println("Ax");
			}
		}
		
    }
    /**
     *	just disconnect opened com-port and after that 
     *	enable button start
     */
    @FXML
    private void handleStop() {
    	disconnect();
    	buttonStart.setDisable(false);
    }
    /**
     * Задаёт путь текущему загруженному файлу. Этот путь сохраняется
     * в реестре, специфичном для конкретной операционной системы.
     * 
     * @param file - файл или null, чтобы удалить путь
     */
    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp1.class);
        if (file != null) {
        	prefs.put("filePath", file.getPath());
            // Обновление заглавия сцены.
            mainApp.primaryStage.setTitle(file.getName());
        } else {
            prefs.remove("filePath");
            // Обновление заглавия сцены.
            mainApp.primaryStage.setTitle("");
        }
    }
    /**
     * Возвращает preference файла адресатов, то есть, последний открытый файл.
     * Этот preference считывается из реестра, специфичного для конкретной
     * операционной системы. Если preference не был найден, то возвращается null.
     * 
     * @return
     */
    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp1.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }
    public String[] getNamePorts(String[] portNames){
    	 portNames = SerialPortList.getPortNames();
    	 return portNames;
    }
    /**
     * void connect com-port with some parametrs,
     */
	@SuppressWarnings("unchecked")
	public void connect(){
		serialPort = new SerialPort (portBox.getValue()); /*Передаем в конструктор суперкласса имя порта с которым будем работать*/
		buttonShow.setDisable(false);
		try {
	        serialPort.openPort (); /*Метод открытия порта*/
	        serialPort.setParams (SerialPort.BAUDRATE_256000, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE); /*Задаем основные параметры протокола UART*/
	        serialPort.setEventsMask (SerialPort.MASK_RXCHAR); /*Устанавливаем маску или список события на которые будет происходить реакция. В данном случае это приход данных в буффер порта*/
	        serialPort.addEventListener (new EventListener ()); /*Передаем экземпляр класса EventListener порту, где будет обрабатываться события. Ниже описан класс*/
	        s_ax.setName("AX");
	        scatterChart.getData().addAll(s_ax);
	        
	    }
	    catch (SerialPortException ex) {
	        System.out.println (ex);
	    }
	}
	/**
     * two voids for convert bytes to float and int
     */
	public static float bytearray2float(byte[] b) {
	    ByteBuffer buf = ByteBuffer.wrap(b);
	    return buf.getFloat();
	}
	public static int bytearray2int(byte[] b) {
	    ByteBuffer buf = ByteBuffer.wrap(b);
	    return buf.getInt();
	}
	int count1 = 0;
	/**
     * Listener for listen COM-port with treatment data packet ( in future will be in new class)
     * 
     */
public class EventListener implements SerialPortEventListener {
	@SuppressWarnings("rawtypes")
	public void serialEvent (SerialPortEvent event) {
        if (event.isRXCHAR () && event.getEventValue () > 0){ /*Если происходит событие установленной маски и количество байтов в буфере более 0*/
            try {
                      data = serialPort.readBytes(64);
                      byte[] buff = new byte[4];
                      write_to_file_bytes(data);
                      //System.out.println(data.length);
                      for(int i = 0; i < 4; i++) {
                    	  buff[i] = data[i];
                      }
                      for(int y = 1; y < 16; y++) {   
	                      for(int i = 0; i < 4; i++) {
	                    	  buff[i] = data[i+y*4];
	                      }
	                  x1.add(bytearray2float(buff));
                      }
                      words.add(new ArrayList<Float>(x1));
                      Data<Float, Float> a = new XYChart.Data<Float, Float>();
                      Data<Float, Float> b = new XYChart.Data<Float, Float>();
                      
                      a.setXValue(x1.get(14));
                      a.setYValue(x1.get(1));
                      b.setXValue(x1.get(14));
                      b.setYValue(x1.get(2));
                      
                      
                      Platform.runLater(new Runnable() {
                          public void run() {
                          s_ax.getData().add(a);
                          s_ay.getData().add(b);
                          }
                     });
                      x1.clear();
                      //disconnect();
            }			
            catch (SerialPortException ex) {
                      System.out.println (ex);
            }
        }
    }
}

	/**
	 * Void for plot graph from txt
	 */
	@SuppressWarnings("unchecked")
		@FXML
	   public void graph() {	   	
		Path fileLocation = Paths.get(FilePath.getText());
		try {
			byte[] data = Files.readAllBytes(fileLocation);
			//while(data.length > 0) {
				byte[] data64 =  Arrays.copyOfRange(data,0, 64);
				System.out.println();
				//}
			/* byte[] buff = new byte[4];
             //System.out.println(data.length);
             for(int i = 0; i < 4; i++) {
           	  buff[i] = data[i];
             }
             for(int y = 1; y < 16; y++) {   
                 for(int i = 0; i < 4; i++) {
               	  buff[i] = data[i+y*4];
                 }
             x1.add(bytearray2float(buff));
             }
             Data<Float, Float> a = new XYChart.Data<Float, Float>();
             
             a.setXValue(x1.get(14));
             a.setYValue(x1.get(1));
             
             
             Platform.runLater(new Runnable() {
                 public void run() {
                 s_ax.getData().add(a);
                 }
            });
             x1.clear();*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
	/**
     * Void for clear txt file before start write data to this file
     */
public void clear_file() {
	String filePath = FilePath.getText().toString();
    PrintWriter writer;
	try {
		writer = new PrintWriter(filePath);
		writer.print("");
	    writer.close();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}   
}
	public void write_to_file(String c) {
		String filePath = FilePath.getText().toString();
	    try {
	        Files.write(Paths.get(filePath), (c+" ").getBytes(), StandardOpenOption.APPEND);
	    }
	    catch (IOException e) {
	        System.out.println(e);
	    }
	}
	public void write_to_file_bytes(byte[] buff) {
		String filePath = FilePath.getText().toString();
	    try {
	        Files.write(Paths.get(filePath), buff , StandardOpenOption.APPEND);
	    }
	    catch (IOException e) {
	        System.out.println(e);
	    }
	}
	public void disconnect(){
	            if(serialPort.isOpened()){
	                try {
	                	for(int i = 0; i < words.size(); i++) {
	                		for(int ii = 0; ii < words.get(i).size(); ii++) {
	                			System.out.print(words.get(i).get(ii)+" ");
	                		}
	                		
	                	System.out.println();
	                	}
	                	serialPort.closePort();
						buttonStart.setDisable(false);
						//write_to_file(rowList);//TestWrite2FileDONOTWORK
	                }catch (SerialPortException e) {
						// TODO Auto-generated catch block
	                	buttonStart.setDisable(true);
	                	e.printStackTrace();
					}
	            }
	}       
	class AffableThread extends Thread
	{
		@Override
		public void run()	//Этот метод будет выполнен в побочном потоке
		{
			System.out.println("");
		}
	}
	/*public int convertirOctetEnEntier(byte[] b){    
	    int MASK = 0xFF;
	    int result = 0;   
	        result = b[0] & MASK;
	        result = result + ((b[1] & MASK) << 8);
	        result = result + ((b[2] & MASK) << 16);
	        result = result + ((b[3] & MASK) << 24);            
	    return result;
	}*/
}
