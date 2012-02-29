package uml.pro2;

import android.database.sqlite.SQLiteDatabase;


public class Database extends SQLiteDatabase {

	/**
	 * @uml.property  name="dbAdapter"
	 * @uml.associationEnd  inverse="database:uml.pro2.DbAdapter"
	 */
	private DbAdapter dbAdapter;

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

}
