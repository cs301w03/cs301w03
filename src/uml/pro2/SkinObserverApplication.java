package uml.pro2;

import android.app.Application;


public class SkinObserverApplication extends Application {

	/**
	 * @uml.property  name="controller"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="skinObserverApplication:uml.pro2.Controller"
	 */
	private Controller controller = new uml.pro2.Controller();

	/**
	 * Getter of the property <tt>controller</tt>
	 * @return  Returns the controller.
	 * @uml.property  name="controller"
	 */
	public Controller getController() {
		return controller;
	}

	/**
	 * Setter of the property <tt>controller</tt>
	 * @param controller  The controller to set.
	 * @uml.property  name="controller"
	 */
	public void setController(Controller controller) {
		this.controller = controller;
	}

	/**
	 * @uml.property  name="controller1"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="skinObserverApplication1:uml.pro2.Controller"
	 */
	private Controller controller1 = new uml.pro2.Controller();

	/**
	 * Getter of the property <tt>controller1</tt>
	 * @return  Returns the controller1.
	 * @uml.property  name="controller1"
	 */
	public Controller getController1() {
		return controller1;
	}

	/**
	 * Setter of the property <tt>controller1</tt>
	 * @param controller1  The controller1 to set.
	 * @uml.property  name="controller1"
	 */
	public void setController1(Controller controller1) {
		this.controller1 = controller1;
	}

	/**
	 * @uml.property  name="skinController"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="skinObserverApplication:uml.pro2.SkinController"
	 */
	private SkinController skinController = new uml.pro2.SkinController();

	/**
	 * Getter of the property <tt>skinController</tt>
	 * @return  Returns the skinController.
	 * @uml.property  name="skinController"
	 */
	public SkinController getSkinController() {
		return skinController;
	}

	/**
	 * Setter of the property <tt>skinController</tt>
	 * @param skinController  The skinController to set.
	 * @uml.property  name="skinController"
	 */
	public void setSkinController(SkinController skinController) {
		this.skinController = skinController;
	}

	/**
	 * @uml.property  name="skinModel"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="skinObserverApplication:uml.pro2.SkinModel"
	 */
	private SkinModel skinModel = new uml.pro2.SkinModel();

	/**
	 * Getter of the property <tt>skinModel</tt>
	 * @return  Returns the skinModel.
	 * @uml.property  name="skinModel"
	 */
	public SkinModel getSkinModel() {
		return skinModel;
	}

	/**
	 * Setter of the property <tt>skinModel</tt>
	 * @param skinModel  The skinModel to set.
	 * @uml.property  name="skinModel"
	 */
	public void setSkinModel(SkinModel skinModel) {
		this.skinModel = skinModel;
	}

}
