package uml.pro2;

import java.util.Collection;


public interface Controller {

	/**
	 * @return  Returns the skinModel.
	 * @uml.property  name="skinModel"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="controller:uml.pro2.SkinModel"
	 */
	public Collection getSkinModel();

	/**
	 * Setter of the property <tt>skinModel</tt>
	 * @param skinModel  The skinModel to set.
	 * @uml.property  name="skinModel"
	 */
	public void setSkinModel(Collection skinModel);

	/**
	 * @return  Returns the skinObserverApplication.
	 * @uml.property  name="skinObserverApplication" default="new uml.pro2.SkinObserverApplication()"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="controller:uml.pro2.SkinObserverApplication"
	 */
	public SkinObserverApplication getSkinObserverApplication();

	/**
	 * Setter of the property <tt>skinObserverApplication</tt>
	 * @param skinObserverApplication  The skinObserverApplication to set.
	 * @uml.property  name="skinObserverApplication"
	 */
	public void setSkinObserverApplication(
			SkinObserverApplication skinObserverApplication);

	/**
	 * @return  Returns the skinModel1.
	 * @uml.property  name="skinModel1" default="new uml.pro2.SkinModel()"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="controller1:uml.pro2.SkinModel"
	 */
	public SkinModel getSkinModel1();

	/**
	 * Setter of the property <tt>skinModel1</tt>
	 * @param skinModel1  The skinModel1 to set.
	 * @uml.property  name="skinModel1"
	 */
	public void setSkinModel1(SkinModel skinModel1);

	/**
	 * @return  Returns the skinObserverApplication1.
	 * @uml.property  name="skinObserverApplication1" default="new uml.pro2.SkinObserverApplication()"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="controller1:uml.pro2.SkinObserverApplication"
	 */
	public SkinObserverApplication getSkinObserverApplication1();

	/**
	 * Setter of the property <tt>skinObserverApplication1</tt>
	 * @param skinObserverApplication1  The skinObserverApplication1 to set.
	 * @uml.property  name="skinObserverApplication1"
	 */
	public void setSkinObserverApplication1(
			SkinObserverApplication skinObserverApplication1);

}
