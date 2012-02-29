package uml.pro2;

import java.util.Collection;


public class SkinModel extends FModel<V> {

	/**
	 * @uml.property  name="dbAdapter"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="skinModel:uml.pro2.DbAdapter"
	 */
	private DbAdapter dbAdapter = new uml.pro2.DbAdapter();

	/**
	 * Getter of the property <tt>dbAdapter</tt>
	 * @return  Returns the dbAdapter.
	 * @uml.property  name="dbAdapter"
	 */
	public DbAdapter getDbAdapter() {
		return dbAdapter;
	}

	/**
	 * Setter of the property <tt>dbAdapter</tt>
	 * @param dbAdapter  The dbAdapter to set.
	 * @uml.property  name="dbAdapter"
	 */
	public void setDbAdapter(DbAdapter dbAdapter) {
		this.dbAdapter = dbAdapter;
	}

	/**
	 * @uml.property  name="controller"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="skinModel:uml.pro2.Controller"
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
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="skinModel1:uml.pro2.Controller"
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
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="skinModel:uml.pro2.SkinController"
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
	 * @uml.property  name="skinObserverApplication"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="skinModel:uml.pro2.SkinObserverApplication"
	 */
	private SkinObserverApplication skinObserverApplication = new uml.pro2.SkinObserverApplication();

	/**
	 * Getter of the property <tt>skinObserverApplication</tt>
	 * @return  Returns the skinObserverApplication.
	 * @uml.property  name="skinObserverApplication"
	 */
	public SkinObserverApplication getSkinObserverApplication() {
		return skinObserverApplication;
	}

	/**
	 * Setter of the property <tt>skinObserverApplication</tt>
	 * @param skinObserverApplication  The skinObserverApplication to set.
	 * @uml.property  name="skinObserverApplication"
	 */
	public void setSkinObserverApplication(
			SkinObserverApplication skinObserverApplication) {
		this.skinObserverApplication = skinObserverApplication;
	}

}
