package cmput301W12.android.model;

import java.io.Serializable;
import java.sql.Timestamp;

import cmput301W12.android.project.controller.DbAdapter;


public class Alarm implements Comparable<Alarm>, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int alarmId = DbAdapter.INVALID_ID;
	private Timestamp alarmTime;
	private String alarmNote;


	public Alarm(Timestamp time, String note){
		alarmTime = time;
		if(note != null){
			alarmNote = note;
		} else {
			alarmNote = "";
		}
	}

	/**
	 * @return the alarmId
	 */
	public int getAlarmId() {
		return alarmId;
	}

	/**
	 * @param alarmId the alarmId to set
	 */
	public void setAlarmId(int alarmId) {
		this.alarmId = alarmId;
	}

	/**
	 * @return the alarmTime
	 */
	public Timestamp getAlarmTime() {
		return alarmTime;
	}

	/**
	 * @param alarmTime the alarmTime to set
	 */
	public void setAlarmTime(Timestamp alarmTime) {
		this.alarmTime = alarmTime;
	}

	/**
	 * @return the alarmNote
	 */
	public String getAlarmNote() {
		return alarmNote;
	}

	/**
	 * @param alarmNote the alarmNote to set
	 */
	public void setAlarmNote(String alarmNote) {
		this.alarmNote = alarmNote;
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Alarm){
			return this.alarmId == ((Alarm) o).alarmId;
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(Alarm obj) {
		return (int) (this.alarmTime.getTime() - obj.alarmTime.getTime());
	}

}
