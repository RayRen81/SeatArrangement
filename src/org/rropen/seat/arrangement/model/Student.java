/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package org.rropen.seat.arrangement.model;

/**
 * @author Ray
 * @version $Revision: $
 */
public class Student {

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	private String id;
	
	private String name;
	
	private String sex;
	
	private Class myClass;
	
	private ExamRoom myExamRoom;

	/**
     * @param name
     * @param myClass
     */
    protected Student(String id, String name, String sex, Class myClass) {
    	this.id = id;
	    this.name = name;
	    this.sex = sex;
	    this.myClass = myClass;
    }
    
    public String getSex() {
    	return sex;
    }

	public String getId() {
    	return id;
    }
    
    public String getName() {
    	return name;
    }

	public Class getMyClass() {
    	return myClass;
    }
	
	public ExamRoom getMyExamRoom() {
    	return myExamRoom;
    }
	
	public void assignedToExamRoom(ExamRoom room, int row, int column) {
		this.myExamRoom = room;
		room.setExaminee(this, row, column);
	}
}
