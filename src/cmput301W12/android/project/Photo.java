package cmput301W12.android.project;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Photo implements Comparable<Photo>, Serializable {
	private int photoId = INVALID_ID;
	private String location;
	private Timestamp timeStamp;
	private String name;
	protected Set<Integer> groups;
	protected Set<Integer> skinConditions;

	protected static final int INVALID_ID = -1;


	/**
	 * Constructors for the PhotoObj object. NOTICE: the default photoId is INVALID_ID. The photoId 
	 * can only be changed by setter.
	 * @param photoId
	 * @param location
	 * @param timeStamp
	 * @param name
	 * @param groups
	 * @param skinConditions
	 */
	public Photo(String location, Timestamp timeStamp,
			String name, Set<Integer> groups, Set<Integer> skinConditions){
		//this.photoId = photoId;
		this.location = location;
		this.timeStamp = timeStamp;

		if(name == null){
			this.name = this.timeStamp.toString();
		}else{
			this.name = name;
		}

		if(groups == null){
			this.groups = new HashSet<Integer>();
		}else{			
			this.groups = groups;
		}

		if(skinConditions == null){
			this.groups = new HashSet<Integer>();
		}else{
			this.skinConditions = skinConditions;
		}
	}


	/**
	 * @return the photoId
	 */
	public int getPhotoId() {
		return photoId;
	}

	/**
	 * @param photoId the photoId to set
	 */
	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the timeStamp
	 */
	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
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
	 * @return the groups
	 */
	public Set<Integer> getGroups() {
		return groups;
	}

	/**
	 * @param groups the groups to set
	 */
	public void setGroups(Set<Integer> groups) {
		this.groups = groups;
	}

	/**
	 * @return the skinConditions
	 */
	public Set<Integer> getSkinConditions() {
		return skinConditions;
	}

	/**
	 * @param skinConditions the skinConditions to set
	 */
	public void setSkinConditions(Set<Integer> skinConditions) {
		this.skinConditions = skinConditions;
	}

	/**
	 * Methods
	 */
	
	/*
	public boolean attachToContainer(int itemId, OptionType option){
		boolean result1 = false, result2 = false;
		if(option == OptionType.GROUP){
			result1 = ( this.groups.add(itemId) && containObj.photos.add(this.photoId) ) ;
		}
		if(containObj instanceof SkinConditionObj){
			result2 = ( this.skinConditions.add(containObj.getItemId()) && containObj.photos.add(this.photoId) );
		}
		return result1 && result2;
	}

	public boolean attachToManyContainers(Collection<? extends ContainObj> containers){
		boolean result = true;

		for(ContainObj co : containers){
			if(this.attachToContainer(co) == false){
				result = false;
			}
		}

		return result;
	}

	public boolean detachFromContainer(ContainObj containObj){
		boolean result1 = false, result2 = false;
		if(containObj instanceof GroupObj){
			result1 = ( this.groups.remove(containObj.getItemId()) && containObj.photos.remove(this.photoId) ) ;
		}
		if(containObj instanceof SkinConditionObj){
			result2 = ( this.skinConditions.remove(containObj.getItemId()) && containObj.photos.remove(this.photoId) );
		}
		return result1 && result2;
	}

	public boolean detachFromManyContainers(Collection<? extends ContainObj> containers){
		boolean result = true;

		for(ContainObj co : containers){
			if(this.detachFromContainer(co) == false){
				result = false;
			}
		}

		return result;
	}
*/

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if(o instanceof Photo == false){
			return false;
		}else{
			return ( this.photoId == ((Photo) o).photoId || this.location.equals(((Photo) o).location)) ;
		}
	}

	public int compareTo(Photo obj){
		return (int) (this.timeStamp.getTime() - obj.timeStamp.getTime());
	}
}
