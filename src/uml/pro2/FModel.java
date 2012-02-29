package uml.pro2;

import java.util.ArrayList;

public class FModel<V extends FView> {
    private ArrayList<V> views;

    public FModel() {
        views = new ArrayList<V>();
    }

    public void addView(V view) {
        if (!views.contains(view)) {
            views.add(view);
        }
    }

    public void deleteView(V view) {
        views.remove(view);
    }

    public void notifyViews() {
        for (V view : views) {
            view.update(this);
        }
    }

	/**
	 * @uml.property  name="dbAdapter"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="fModel:uml.pro2.DbAdapter"
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
}
