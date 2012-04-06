package cmput301W12.android.project;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.PaintDrawable;
/**
 * Provide the default color for selected items and unselected items.
 * @author hieungo
 *
 */
public class SelectiveColor extends Color {
	
	private final static PaintDrawable SELECTED_COLOR_DRAWABLE = new PaintDrawable(Color.GREEN);
	private final static PaintDrawable UNSELECTED_COLOR_DRAWABLE = new PaintDrawable(Color.TRANSPARENT);
	
	/**
	 * Return an {@link PaintDrawable} of a {@link Color} indicates selected components
	 * @return solid color {@link PaintDrawable} of selected components
	 */
	public static PaintDrawable getSelectedDrawable()
	{
		return SELECTED_COLOR_DRAWABLE;
	}
	
	/**
	 * Return an {@link PaintDrawable} of a {@link ColorStateList} indicates unselected components 
	 * @return solid color {@link PaintDrawable} of unselected components
	 */
	public static PaintDrawable getUnselectedDrawable()
	{
		return UNSELECTED_COLOR_DRAWABLE;
	}
	
}
