package uml.pro2;


public class SkinController implements Controller {

	/**
	 * @uml.property  name="skinModel"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="skinController:uml.pro2.SkinModel"
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

	/**
	 * @uml.property  name="skinObserverApplication"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="skinController:uml.pro2.SkinObserverApplication"
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
