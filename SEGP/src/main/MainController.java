/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import database.History_Managment;
import java.beans.EventHandler;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

//import javax.swing.text.Document;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import userInterface.Hamburger;
import userInterface.History;
import userInterface.TabPaneView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;

/**
 *
 * @author Segp-Group 3
 */
public class MainController implements Initializable {

	/*
	 * Reference: We got this idea from this link Doc Link:
	 * https://docs.oracle.com/javase/8/javafx/api/index.html?javafx/scene/web/
	 * WebEngine.html We got the conceptual thought from Stack: Link:
	 * http://stackoverflow.com/questions/32486758/detect-url-changes-in-javafx-
	 * webview Description: 1st rootBorderPane that is the actual root for scene
	 * and 2nd borderpane is the tabpane #pane Below is 4 buttons for navigation
	 * backward to go back page,forward to go the previous visited page,refresh
	 * will reload the page and search button is a specific url search Textfield
	 * it to write a url.
	 * 
	 * We Extends this Main controller Class with Renderer to work more
	 * efficiently and can easily test any renderer
	 * 

	 * 
	 ***********************************************************************************************************/

	@FXML
	private BorderPane rootBorderPane;
	@FXML
	private BorderPane borderpane;
	@FXML
	private JFXButton back;
	@FXML
	private JFXButton forward;
	@FXML
	private JFXButton refresh;
	@FXML
	private JFXButton search;

	@FXML
	private JFXTextField searchField;
	@FXML
	private TabPane tabPane;
	@FXML
	private Tab addNewTab;
	@FXML
	private JFXHamburger hamburger;
	@FXML
	private GridPane navigationBar;
	@FXML private BorderPane rootBorderPane;
	@FXML private BorderPane borderpane;
	@FXML private JFXButton back;
	@FXML private JFXButton forward; @FXML private JFXButton refresh; @FXML private JFXButton search;
	@FXML private JFXTextField searchField; 
	@FXML private TabPane tabPane;
	@FXML private Tab addNewTab; 
	@FXML private JFXHamburger hamburger;
	@FXML private GridPane navigationBar;
	private VBox drawerPane = new VBox();
	private WebView browser = new WebView();
	private WebEngine webEngine = browser.getEngine();
	//Classes objects to get methods or set methods access
	private History object1 = new History();
	private Hamburger ham = new Hamburger();
	
	public VBox drawerPane = new VBox();
	// make obejc to get the setter method for url
	public WebView browser = new WebView();
	public WebEngine webEngine = browser.getEngine();
	

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		// ---------------All opens tabs should be closed so below line is for
		// just closing tabs------------------------
		tabPane.setTabClosingPolicy(TabClosingPolicy.ALL_TABS);
		pageRender("https://www.google.com.pk/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8");
		
		// ---------------------webView---------------------------webEngine----------------------------------------------

		// --------------------- Default url will be google
		webEngine.load("http://www.google.com/");
		back.setDisable(true);
		forward.setDisable(true);
		// --------------Renderer Class-------webView-----------webEngine----------------------------------------------
		searchField.setText(webEngine.getLocation());
		borderpane.setCenter(browser);
		
		//---------------URL of addressbar load if user clicked search button

		search.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			pageRender(searchField.getText()); //method call 
		});


		/*
		 * Bug Found: 	Remove comment and almost will remove in future as Below
		 * 				method run continous with main thread to check the Changing
		 * 				url or changing properity in browser. So that's why we yet 
		 * 				remove to abstain the bug that we faced while showing work to mentor
		 ************************************************************************************/
			//webEngine.load(searchField.getText());
			/*webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
				@Override
				public void changed(ObservableValue ov, State oldState, State newState) {
					if (newState == Worker.State.SUCCEEDED) {
<<<<<<< HEAD
						System.out.println(webEngine.getLocation());
						searchField.setText(webEngine.getLocation());
=======
						//System.out.println("New Link"+webEngine.getLocation());
						
						//searchField.setText(webEngine.getLocation());
>>>>>>> upstream/master
						
					}

				}
			});*/
			
		// ---------------------Listner for search textfield of search button---------------------------------------
		searchField.setOnKeyPressed(event -> {

			//Search Field Listener
			searchField.setOnKeyPressed(e -> {

			if (event.getCode() == KeyCode.ENTER) {
				pageRender(searchField.getText()); //method call
			}
		});

		//System.out.println(webEngine.getLocation());
		
		//-----------------------Thread is continously running to check any change of link in browser to set value in broser addressbar
		
//		
		webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
			@Override
			public void changed(ObservableValue ov, State oldState, State newState) {
			
				if (newState == Worker.State.SUCCEEDED) {
					searchField.setText(webEngine.getLocation());
					webEngine.getLocation();
					//System.out.println("URL changing: "+ webEngine.getLocation());
					//webEngine.load(webEngine.getLocation());
					//webEngine.load(searchField.getText());
					
				}
			}
		});

		//--------------------Bookmarks and History detials didn't start yet !-------------------------
		/*
		 * bookmarks.setOnAction(new EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent event) { // TODO
		 * Auto-generated method stub String url = engine.getLocation();
		 * System.out.println(url); // engine.get // write.println(url); }
		 * 
		 * }); History.setOnAction(new EventHandler<ActionEvent>(){
		 * 
		 * @Override public void handle(ActionEvent event) { // TODO
		 * Auto-generated method stub WebHistory history = engine.getHistory();
		 * ObservableList<Entry> list = history.getEntries(); for(int i=0 ; i<
		 * list.size();i++){ System.out.println(list.get(i)); } } });
		 * 
		 ************************************************************************/

		// --------------------------------------------------------Backward-------------------------------------------

		back.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			try {

				WebHistory history = webEngine.getHistory();
				history.go(-1);
				if(forward.isDisable()){
					forward.setDisable(false);
				}
			} catch (IndexOutOfBoundsException e1) {
				back.setDisable(true);      // made changes to disable or enable forward button.
				
			}
		});

		// --------------------------------------------------------Forward--------------------------------------------

		forward.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			try {
				WebHistory history = webEngine.getHistory();
				history.go(1);
				if(back.isDisable()){
					back.setDisable(false);
				}
			} catch (IndexOutOfBoundsException e1) {
				forward.setDisable(true);    // made changes to disable or enable backward button.
				
			}
		});

		// -------------------------------------------Refresh--------------------------------------------------------

		refresh.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			webEngine.reload();
		});
		
		// -------------------------------------------Hamburger----Drawer----Menu------------------------------------
		
		/*
		*	New tabs will add and but due to some reasome the tabpan_view is
		*	comment as We cannont handle yet 
		*	The Mulit view tabs yet our aim to handle single tab
		***********************************************************/
		ham.getHamburger(hamburger, borderpane, tabPane);
		//----------------------------------------TabPane-----------------------------------------------------//
		//Adding multiple tabs would be done later.
		
		TabPaneView tabpan_view = new TabPaneView();
		//----------------------------------------------------------------------------------------------------//
		
		// end intializer method
		}

	
	//mehtod to rendere page
	public void pageRender(String url)
	{
		webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
			@Override
			public void changed(ObservableValue ov, State oldState, State newState) {

				if (newState == Worker.State.SUCCEEDED) {
					searchField.setText(webEngine.getLocation());
					if(!(webEngine.getLocation().equals("about:blank")))
					History_Managment.insertUrl(webEngine.getLocation());
				}
				
			}
			
		});
		webEngine.load(url);
		borderpane.setCenter(browser);
		}
	// end class
}