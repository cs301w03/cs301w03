package cmput301W12.android.model;

import java.util.ArrayList;

import cmput301W12.android.project.view.FView;

/** 
 * The Model class obtained from FillerCreep Application.
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
