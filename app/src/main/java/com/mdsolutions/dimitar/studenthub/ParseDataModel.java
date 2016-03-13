package com.mdsolutions.dimitar.studenthub;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by martin on 2/23/16.
 */

// Model of the Forum, for caching app data in RAM
public class ParseDataModel {
    public static List<ParseObject> subjects;
    public static HashMap<String, List<ParseObject>> categories;
    public static HashMap<String, List<ParseObject>> threads;
    public static HashMap<String, List<ParseObject>> posts;
    public static HashMap<String, List<ParseObject>> courses;

    public static void Initialize() {
        subjects = new ArrayList<ParseObject>();
        categories = new HashMap<String, List<ParseObject>>();
        threads = new HashMap<String, List<ParseObject>>();
        posts = new HashMap<String, List<ParseObject>>();
        courses = new HashMap<String, List<ParseObject>>();
    }

    public static void UpdateSubjectsData(List<ParseObject> subjectList) {
        subjects = subjectList;
    }

    public static List<ParseObject> getCategories(String subjectId) {
        return categories.get(subjectId);
    }

    public static List<ParseObject> getCourses(String subjectId) {
        return courses.get(subjectId);
    }

    public static List<ParseObject> getThreads(String categoryId) {
        return threads.get(categoryId);
    }

    public static List<ParseObject> getPosts(String threadId) {
        return posts.get(threadId);
    }

    public static void UpdateCategoryData(String subjectId, List<ParseObject> categoryList) {
        categories.put(subjectId, categoryList);
    }

    public static void UpdateCourseData(String subjectId, List<ParseObject> courseList) {
        courses.put(subjectId, courseList);
    }

    public static void UpdateThreadData(String categoryId, List<ParseObject> threadList) {
        threads.put(categoryId, threadList);
    }

    public static void UpdatePostData(String threadId, List<ParseObject> postList) {
        posts.put(threadId, postList);
    }

    public static void ClearThreadsData(String categoryId)
    {
        threads.put(categoryId, new ArrayList<ParseObject>());
    }

    public static void AddThread(String categoryId, ParseObject thread)
    {
        threads.get(categoryId).add(thread);
    }
}
