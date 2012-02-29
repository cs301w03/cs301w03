package uml.pro2;

import android.database.sqlite.SQLiteDatabase;
import java.util.Collection;


/**
 * @uml.dependency   supplier="uml.pro2.Image"
 * @uml.dependency   supplier="uml.pro2.ReviewTakenPhotoActivity"
 */
public class DbAdapter {

	
	
	public class DatabaseHelper {

		/**
		 * @uml.property  name="image"
		 * @uml.associationEnd  multiplicity="(0 -1)" aggregation="composite" inverse="databaseHelper:uml.pro2.Image"
		 */
		private Collection image;

		/**
		 * Getter of the property <tt>image</tt>
		 * @return  Returns the image.
		 * @uml.property  name="image"
		 */
		public Collection getImage() {
			return image;
		}

		/**
		 * Setter of the property <tt>image</tt>
		 * @param image  The image to set.
		 * @uml.property  name="image"
		 */
		public void setImage(Collection image) {
			this.image = image;
		}

	}

	public class Database extends SQLiteDatabase {

	}

	/**
	 * @uml.property  name="database"
	 * @uml.associationEnd  aggregation="composite" inverse="dbAdapter:uml.pro2.Database"
	 */
	private Database database;

	/**
	 * Getter of the property <tt>database</tt>
	 * @return  Returns the database.
	 * @uml.property  name="database"
	 */
	public Database getDatabase() {
		return database;
	}

	/**
	 * Setter of the property <tt>database</tt>
	 * @param database  The database to set.
	 * @uml.property  name="database"
	 */
	public void setDatabase(Database database) {
		this.database = database;
	}

	/**
	 * @uml.property  name="mDbHelper"
	 */
	private DbAdapterDatabaseHelper mDbHelper;

	/**
	 * Getter of the property <tt>mDbHelper</tt>
	 * @return  Returns the mDbHelper.
	 * @uml.property  name="mDbHelper"
	 */
	public DbAdapterDatabaseHelper getMDbHelper() {
		return mDbHelper;
	}

	/**
	 * Setter of the property <tt>mDbHelper</tt>
	 * @param mDbHelper  The mDbHelper to set.
	 * @uml.property  name="mDbHelper"
	 */
	public void setMDbHelper(DbAdapterDatabaseHelper mDbHelper) {
		this.mDbHelper = mDbHelper;
	}

	/**
	 * @uml.property  name="mDb"
	 */
	private SQLiteDatabase mDb;

	/**
	 * Getter of the property <tt>mDb</tt>
	 * @return  Returns the mDb.
	 * @uml.property  name="mDb"
	 */
	public SQLiteDatabase getMDb() {
		return mDb;
	}

	/**
	 * Setter of the property <tt>mDb</tt>
	 * @param mDb  The mDb to set.
	 * @uml.property  name="mDb"
	 */
	public void setMDb(SQLiteDatabase mDb) {
		this.mDb = mDb;
	}

	/**
	 * @uml.property  name="fModel"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="dbAdapter:uml.pro2.FModel"
	 */
	private FModel fModel = new uml.pro2.FModel();

	/**
	 * Getter of the property <tt>fModel</tt>
	 * @return  Returns the fModel.
	 * @uml.property  name="fModel"
	 */
	public FModel getFModel() {
		return fModel;
	}

	/**
	 * Setter of the property <tt>fModel</tt>
	 * @param fModel  The fModel to set.
	 * @uml.property  name="fModel"
	 */
	public void setFModel(FModel fModel) {
		this.fModel = fModel;
	}

	/**
	 * @uml.property  name="skinModel"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="dbAdapter:uml.pro2.SkinModel"
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
