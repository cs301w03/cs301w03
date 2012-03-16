package cmput301W12.android.project;

import java.util.ArrayList;

/**
 * @author Hieu Ngo
 * @date Mar 14, 2012
 * 
 * The Model of the system
 */
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
}
