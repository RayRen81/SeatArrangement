/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package org.rropen.seat.arrangement.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.rropen.seat.arrangement.model.Class;
import org.rropen.seat.arrangement.model.ExamRoom;
import org.rropen.seat.arrangement.model.Student;

/**
 * @author Ray
 * @version $Revision: $
 */
public class Arrange {

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";

	public List<ExamRoom> arrangeExamSeat(List<Class> classes, int numOfExamRoom, int row, int col) {

		LinkedList<Student> allExaminee = new LinkedList<Student>();

		List<List<Student>> studentsInAllClasses = new ArrayList<List<Student>>();
		for (Class aClass : classes) {
			List<Student> studentsInAClass = new ArrayList<Student>(aClass.getNativeStudentsMap().values());
			Collections.shuffle(studentsInAClass);
			studentsInAllClasses.add(studentsInAClass);
		}
		
		while (studentsInAllClasses.size() != 0) {
			Collections.shuffle(studentsInAllClasses);
			for (List<Student> studentsInAClass : studentsInAllClasses) {
				int numOfStudents = studentsInAClass.size();
				Student examinee = studentsInAClass.remove((int) Math.random() * numOfStudents);
				if (allExaminee.size() == 0 || !allExaminee.getLast().getMyClass().equals(examinee.getMyClass())) {
					allExaminee.add(examinee);
				} else {
					boolean isAssigned = false;
					for (int j = allExaminee.size() - 2; j > 0; j--) {
						if (!allExaminee.get(j).getMyClass().equals(examinee.getMyClass())
						        && !allExaminee.get(j - 1).getMyClass().equals(examinee.getMyClass())) {
							allExaminee.add(j, examinee);
							isAssigned = true;
							break;
						}
					}
					if (!isAssigned) {
						allExaminee.add(examinee);
					}
				}
			}

			List<List<Student>> copyOfStudentsInAllClasses = new ArrayList<List<Student>>(studentsInAllClasses);
			for (List<Student> studentsInAClass : copyOfStudentsInAllClasses) {
				if (studentsInAClass.size() == 0) {
					studentsInAllClasses.remove(studentsInAClass);
				}
			}

		}
		
		List<ExamRoom> rooms = new ArrayList<ExamRoom>();

		for (int i = 0; i < numOfExamRoom; i++) {
			ExamRoom room = new ExamRoom(String.valueOf(i+1), row, col);
			rooms.add(room);
			for(int j = 0; j < col; j++){
				for(int k = 0; k < row; k++) {
					Student examinee = allExaminee.poll();
					if(examinee == null) {
						return rooms;
					}
					examinee.assignedToExamRoom(room, k, j);
				}
			}
		}
		
		return rooms;

	}

}
