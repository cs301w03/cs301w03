package cmput301W12.android.project;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ContainObj {
	private int itemId;
	private String name;
	protected Set<PhotoObj> photos;

	public ContainObj(int itemId, String name, Set<PhotoObj> photos){
		this.itemId = itemId;
		this.name = name;
		this.photos = photos;
	}

	public ContainObj(int itemId, String name){
		this.itemId = itemId;
		this.name = name;
		this.photos = new HashSet<PhotoObj>();
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
	public Set<PhotoObj> getPhotos() {
		return photos;
	}

	/**
	 * @param photos the photos to set
	 */
	public void setPhotos(Set<PhotoObj> photos) {
		this.photos = photos;
	}

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

	public boolean detachPhoto(PhotoObj phoObj){
		return phoObj.detachFromContainer(this);
	}

	public boolean removeManyPhotos(Collection<PhotoObj> photos){
		boolean result = true;

		for(PhotoObj po : photos){
			if(po.detachFromContainer(this) == false){
				result = false;
			}
		}

		return result;

	}
}
