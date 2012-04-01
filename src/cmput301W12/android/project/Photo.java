package cmput301W12.android.project;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

import android.content.Context;
import android.util.Log;

/**
 * 
 * @author Hieu Ngo
 * @date Mar 16, 2012
 * 
 * Data class, store information of Photo
 * includes image uri, timestamp and name
 * 
 * Note: Expect to implement Proxy design pattern
 */
public class Photo implements Comparable<Photo>, Serializable {
	private int photoId = DbAdapter.INVALID_ID;
	private String location;
	private Timestamp timeStamp;
	private String name;
	private String annotation;
	protected Set<Integer> groups;
	protected Set<Integer> skinConditions;

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
			Log.d("SKINOBSERVER", "69");
			this.skinConditions = new HashSet<Integer>();
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
	 * @return the skinConditions
	 */
	public Set<Group> getGroups(Context context) {
		FController controller = SkinObserverApplication.getSkinObserverController(context);
		if (photoId != DbAdapter.INVALID_ID)
		{
			SortedSet<Group> groupList = (SortedSet<Group>) controller.getAllContainersOfAPhoto(photoId, OptionType.GROUP);
			return groupList;
		}
		else
			return null;
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
	 * @return the skinConditions
	 */
	public Set<SkinCondition> getSkinConditions(Context context) {
		FController controller = SkinObserverApplication.getSkinObserverController(context);
		if (photoId != DbAdapter.INVALID_ID)
		{
			SortedSet<SkinCondition> scList = (SortedSet<SkinCondition>) controller.getAllContainersOfAPhoto(photoId, OptionType.GROUP);
			return scList;
		}
		else
			return null;
	}

	/**
	 * @param skinConditions the skinConditions to set
	 */
	public void setSkinConditions(Set<Integer> skinConditions) {
		this.skinConditions = skinConditions;
	}

	public String getAnnotation(){
		return this.annotation;
	}

	public void setAnnotation(String newAnnotation){
		this.annotation = newAnnotation;
	}

	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Photo == false){
			return false;
		}else{
			return ( this.photoId == ((Photo) o).photoId || this.location.equals(((Photo) o).location)) ;
		}
	}

	public int compareTo(Photo obj){
		return (int) (this.timeStamp.getTime() - obj.timeStamp.getTime());
	}
	
	/**
	 * 
	 * @return true if photo is not saved in database,
	 * otherwise return false
	 */
	public boolean isNew()
	{
		if (photoId == DbAdapter.INVALID_ID)
			return true;
		else
			return false;
	}
}
