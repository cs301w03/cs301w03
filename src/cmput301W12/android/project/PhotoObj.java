package cmput301W12.android.project;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class PhotoObj {
	private int photoId;
	private String location;
	private Timestamp timeStamp;
	private String name;
	protected Set<GroupObj> groups;
	protected Set<SkinConditionObj> skinConditions;

	/**
	 * Constructors for the PhotoObj object.
	 * @param photoId
	 * @param location
	 * @param timeStamp
	 * @param name
	 * @param groups
	 * @param skinConditions
	 */
	public PhotoObj(int photoId, String location, Timestamp timeStamp,
			String name, Set<GroupObj> groups, Set<SkinConditionObj> skinConditions){
		this.photoId = photoId;
		this.location = location;
		this.timeStamp = timeStamp;
		this.name = name;
		this.groups = groups;
		this.skinConditions = skinConditions;
	}

	public PhotoObj(int photoId, String location, Timestamp timeStamp, 
			Set<GroupObj> groups, Set<SkinConditionObj> skinConditions ){
		this(photoId, location, timeStamp, timeStamp.toString(), groups, skinConditions);
	}

	public PhotoObj(int photoId, String location, Timestamp timeStamp, String name){
		this(photoId, location, timeStamp, name, 
				new HashSet<GroupObj>(), new HashSet<SkinConditionObj>());
	}

	public PhotoObj(int photoId, String location, Timestamp timeStamp){
		this(photoId, location, timeStamp, timeStamp.toString(), 
				new HashSet<GroupObj>(), new HashSet<SkinConditionObj>())	;
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
	public Set<GroupObj> getGroups() {
		return groups;
	}

	/**
	 * @param groups the groups to set
	 */
	public void setGroups(Set<GroupObj> groups) {
		this.groups = groups;
	}

	/**
	 * @return the skinConditions
	 */
	public Set<SkinConditionObj> getSkinConditions() {
		return skinConditions;
	}

	/**
	 * @param skinConditions the skinConditions to set
	 */
	public void setSkinConditions(Set<SkinConditionObj> skinConditions) {
		this.skinConditions = skinConditions;
	}

	/**
	 * Methods
	 */

	public boolean attachToContainer(ContainObj containObj){
		boolean result1 = false, result2 = false;
		if(containObj instanceof GroupObj){
			result1 = ( this.groups.add((GroupObj)containObj) && containObj.photos.add(this) ) ;
		}
		if(containObj instanceof SkinConditionObj){
			result2 = ( this.skinConditions.add((SkinConditionObj) containObj) && containObj.photos.add(this) );
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
			result1 = ( this.groups.remove((GroupObj)containObj) && containObj.photos.remove(this) ) ;
		}
		if(containObj instanceof SkinConditionObj){
			result2 = ( this.skinConditions.remove((SkinConditionObj) containObj) && containObj.photos.remove(this) );
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


	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if(o instanceof PhotoObj == false){
			return false;
		}else{
			return this.photoId == ((PhotoObj) o).photoId;
		}
	}


}
