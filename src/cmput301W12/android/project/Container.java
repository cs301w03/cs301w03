package cmput301W12.android.project;

import java.util.HashSet;
import java.util.Set;

public abstract class Container {
	private int itemId = Photo.INVALID_ID;
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
	 * @param photos the photos to set
	 */
	public void setPhotos(Set<Integer> photos) {
		this.photos = photos;
	}
/*
	public boolean attachPhoto(PhotoObj phoObj){
		return phoObj.attachToContainer(this);
	}

	public boolean addManyPhotos(Collection<PhotoObj> photos){
		boolean result = true;

		for(PhotoObj po : photos){
			if(po.attachToContainer(this) == false){
				result = false;
			}
		}

		return result;
	}

	public boolean detachPhoto(Integer phoObj){
		return phoObj.detachFromContainer(this);
	}

	public boolean removeManyPhotos(Collection<Integer> photos){
		boolean result = true;

		for(Integer po : photos){
			if(po.detachFromContainer(this) == false){
				result = false;
			}
		}

		return result;

	}
	*/
}
