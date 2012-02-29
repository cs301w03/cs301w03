package uml.pro2;

import java.util.Collection;


public class Group {

	/**
	 * @uml.property  name="image"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="group:uml.pro2.Image"
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
