/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package org.rropen.seat.arrangement.model;

import java.util.LinkedHashMap;

/**
 * @author Ray
 * @version $Revision: $
 */
public class Class {

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	private LinkedHashMap<String, Student> nativeStudentsMap = new LinkedHashMap<String, Student>();
	
	private String className;
	
	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((className == null) ? 0 : className.hashCode());
	    return result;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (obj == null)
		    return false;
	    if (getClass() != obj.getClass())
		    return false;
	    Class other = (Class) obj;
	    if (className == null) {
		    if (other.className != null)
			    return false;
	    } else if (!className.equals(other.className))
		    return false;
	    return true;
    }

	/**
     * @param className
     */
    public Class(String className) {
	    this.className = className;
    }

	public LinkedHashMap<String, Student> getNativeStudentsMap() {
    	return nativeStudentsMap;
    }

	public String getClassName() {
    	return className;
    }
	
	public void addStudents(String studentNo, String studentName, String sex){
		Student oldValue = nativeStudentsMap.put(studentNo, new Student(studentNo, studentName, sex, this));
		if(oldValue != null) {
			throw new RuntimeException("Student with NO " + studentNo + " Exists already");
		}
	}
	
}
