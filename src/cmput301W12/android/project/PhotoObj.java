package cmput301W12.android.project;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class PhotoObj {
	private int photoId;
	private String location;
	private Timestamp timeStamp;
	private String name;
	private Set<GroupObj> groups;
	private Set<SkinConditionObj> skinConditions;

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
	 * getters for properties of the PhotoObj object.
	 * @return
	 */
	public int getPhotoId(){
		return this.photoId;
	}
	
	public String getLocation(){
		return this.location;
	}
	
	public Timestamp getTimeStamp(){
		return this.timeStamp;
	}
	
	public String getName(){
		return this.name;
	}
	
	public Set<GroupObj> getGroups(){
		return this.groups;
	}
	
	public Set<SkinConditionObj> getSkinConditions(){
		return this.skinConditions;
	}
	
	/**
	 * setters for properties of the PhotoObj object.
	 */
	
	public void setPhotoId(int newPhotoId){
		this.photoId = newPhotoId;
	}
	
	public void setLocation(String newLocation){
	 this.location = newLocation;
	}
	
	public void setTimeStamp(Timestamp newTimeStamp){
		this.timeStamp = newTimeStamp;
	}
	
	public void setName(String newName){
	 this.name = newName;
	}
	
	public void setGroups(Set<GroupObj> newGroups){
		this.groups = newGroups;
	}
	
	public void setSkinConditions(Set<SkinConditionObj> newSkinConditions){
		this.skinConditions = newSkinConditions;
	}
	
	/**
	 * Methods
	 */
	
	public boolean addGroup(GroupObj groupObj){
		return this.groups.add(groupObj);
	}
	
	public boolean addSkinCondition(SkinConditionObj skinConditionObj){
		return this.skinConditions.add(skinConditionObj);
	}
	
	public boolean addManyGroups(Collection<GroupObj> groups){
		return this.groups.addAll(groups);
	}
	
	public boolean addManySkinConditions(Collection<SkinConditionObj> skinConditions){
		return this.skinConditions.addAll(skinConditions);
	}
	
	public boolean removeGroup(GroupObj groupObj){
		return this.groups.remove(groupObj);
	}
	
	public boolean removeSkinCondition(SkinConditionObj skinObj){
		return this.skinConditions.remove(skinObj);
	}
	
	public boolean removeManyGroups(Collection<GroupObj> groups){
		return this.groups.removeAll(groups);
	}
	
	public boolean removeManySkinConditions(Collection<SkinConditionObj> skinConditions){
		return this.skinConditions.removeAll(skinConditions);
	}
}
