package com.zomb.here;

public class CourseItem {
    private int courseId;
    private String courseName;

    public CourseItem(int courseId, String className) {
        this.courseId = courseId;
        this.courseName = className;
    }

    public int getClassId() {
        return courseId;
    }

    public void setClassId(int courseId) {
        this.courseId = courseId;
    }

    public String getClassName() {
        return courseName;
    }

    public void setClassName(String courseName) {
        this.courseName = courseName;
    }
}
