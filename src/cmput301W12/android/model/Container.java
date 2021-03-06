
package cmput301W12.android.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

import cmput301W12.android.project.controller.DbAdapter;


import android.content.Context;

/**
 * @author: Hieu Ngo
 * @date: Mar 16, 2012
 * 
 * This is an abtract data class for program's container class.
 * Known extends classes: Group and SkinCondition      
 */
public abstract class Container implements Comparable<Container>, 
Serializable {

	boolean selected;
	private static final long serialVersionUID = 1L;
	private int itemId = DbAdapter.INVALID_ID;
	private String name;
	protected Set<Integer> photos;

	public Container(String name, Set<Integer> photos){
		this.name = name;
		this.photos = photos;
	}

	public Container(String name){
		this.name = name;
		this.photos = new HashSet<Integer>();
	}


	/**
	 * @return the itemId
	 */
	public int getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the photos
	 */
	public Set<Integer> getPhotos() {
		return photos;
	}

	/**
	 * @return the photos
	 */
	public abstract SortedSet<Photo> getPhotos(Context context);

	/**
	 * @param photos the photos to set
	 */
	public void setPhotos(Set<Integer> photos) {
		this.photos = photos;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public int compareTo(Container another) {
		return this.getName().compareTo(another.getName());
	}

}
