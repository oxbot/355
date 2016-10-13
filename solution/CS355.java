package cs355.solution;

import cs355.GUIFunctions;
import cs355.controller.MyController;
import cs355.model.drawing.MyModel;
import cs355.view.MyView;

/**
 * This is the main class. The program starts here.
 * Make you add code below to initialize your model,
 * view, and controller and give them to the app.
 */
public class CS355 {

	/**
	 * This is where it starts.
	 * @param args = the command line arguments
	 */
	public static void main(String[] args) {
		
		MyModel model = new MyModel();
		
		
		MyView my_view = new MyView();
		MyController my_controller = new MyController();
		
		my_controller.addModel(model);
		my_controller.addView(my_view);
		my_view.addModel(model);
		my_view.addController(my_controller);
		

		// Fill in the parameters below with your controller and view.
		GUIFunctions.createCS355Frame(my_controller, my_view);
		
		//sets up the initial scroll bars
		my_controller.init();

		GUIFunctions.refresh();
	}
}
