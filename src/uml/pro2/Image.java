package uml.pro2;

import uml.pro2.DbAdapter.DatabaseHelper;
import java.util.Collection;


public class Image {

	/**
	 * @uml.property  name="timeStamp"
	 */
	private String timeStamp;

	/**
	 * Getter of the property <tt>timeStamp</tt>
	 * @return  Returns the timeStamp.
	 * @uml.property  name="timeStamp"
	 */
	public String getTimeStamp() {
		return timeStamp;
	}

	/**
	 * Setter of the property <tt>timeStamp</tt>
	 * @param timeStamp  The timeStamp to set.
	 * @uml.property  name="timeStamp"
	 */
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	
	/**
	 * @uml.property  name="skinCondition"
	 */
	
	/**
	 * @uml.property  name="databaseHelper"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="image:uml.pro2.DbAdapter.DatabaseHelper"
	 */
	private DatabaseHelper databaseHelper = new uml.pro2.DbAdapter.DatabaseHelper();

	/**
	 * Getter of the property <tt>databaseHelper</tt>
	 * @return  Returns the databaseHelper.
	 * @uml.property  name="databaseHelper"
	 */
	public DatabaseHelper getDatabaseHelper() {
		return databaseHelper;
	}

	/**
	 * Setter of the property <tt>databaseHelper</tt>
	 * @param databaseHelper  The databaseHelper to set.
	 * @uml.property  name="databaseHelper"
	 */
	public void setDatabaseHelper(DatabaseHelper databaseHelper) {
		this.databaseHelper = databaseHelper;
	}


	/**
	 * @uml.property  name="group"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="image:uml.pro2.Group"
	 */
	private Collection group;

	/**
	 * Getter of the property <tt>group</tt>
	 * @return  Returns the group.
	 * @uml.property  name="group"
	 */
	public Collection getGroup() {
		return group;
	}

	/**
	 * Setter of the property <tt>group</tt>
	 * @param group  The group to set.
	 * @uml.property  name="group"
	 */
	public void setGroup(Collection group) {
		this.group = group;
	}


	/**
	 * @uml.property  name="skinCondition"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="image:uml.pro2.SkinCondition"
	 */
	private Collection skinCondition;

	/**
	 * Getter of the property <tt>skinCondition</tt>
	 * @return  Returns the skinCondition.
	 * @uml.property  name="skinCondition"
	 */
	public Collection getSkinCondition() {
		return skinCondition;
	}

	/**
	 * Setter of the property <tt>skinCondition</tt>
	 * @param skinCondition  The skinCondition to set.
	 * @uml.property  name="skinCondition"
	 */
	public void setSkinCondition(Collection skinCondition) {
		this.skinCondition = skinCondition;
	}

}
