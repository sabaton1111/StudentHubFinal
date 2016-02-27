package com.example.dimitar.studenthub;

import android.util.Log;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by martin on 2/23/16.
 */

// Model of the Forum, for caching app data in RAM
public class ForumModel {
    public static List<ParseObject> subjects;
    public static HashMap<String, List<ParseObject>> categories;
    public static HashMap<String, List<ParseObject>> threads;
    public static HashMap<String, List<ParseObject>> posts;

    public static void Initialize() {
        subjects = new ArrayList<ParseObject>();
        categories = new HashMap<String, List<ParseObject>>();
        threads = new HashMap<String, List<ParseObject>>();
        posts = new HashMap<String, List<ParseObject>>();
    }

    public static void UpdateSubjectsData(List<ParseObject> subjectList) {
        subjects = subjectList;
    }

    public static List<ParseObject> getCategories(String subjectId) {
        return categories.get(subjectId);
    }

    public static List<ParseObject> getThreads(String categoryId) {
        Log.d("categoryId e ravno na  ", categoryId);
        /*if (threads.get(categoryId) == null) {
            Log.d("a thredovete na ", " null the fuck zashto?");
        }*/
        return threads.get(categoryId);
    }

    public static List<ParseObject> getPosts(String threadId) {
        return posts.get(threadId);
    }

    public static void UpdateCategoryData(String subjectId, List<ParseObject> categoryList) {
        categories.put(subjectId, categoryList);
    }

    public static void UpdateThreadData(String categoryId, List<ParseObject> threadList) {
        Log.d("Updatenatthreads kat - ", categoryId);
        if (threadList == null) {
            Log.d("lista e", "null");
        } else {
            Log.d("lista e", threadList.toString());
        }
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
