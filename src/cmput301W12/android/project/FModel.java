package cmput301W12.android.project;

/* The FModel.class is the model for our application with functions to addview, deleteview
 * and notifyviews of updated information. This connects and changes all the views of our
 * application.
 */

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
}
