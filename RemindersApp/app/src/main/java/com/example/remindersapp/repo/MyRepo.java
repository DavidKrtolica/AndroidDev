package com.example.remindersapp.repo;

import com.example.remindersapp.Updateable;
import com.example.remindersapp.model.Reminder;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyRepo {

    private static MyRepo repo = new MyRepo();
    public ArrayList<Reminder> reminders = new ArrayList<>();
    private Updateable activity;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static MyRepo r() {
        return repo;
    }

    public void readReminders() {
        db.collection("reminders").addSnapshotListener((values, error) -> {
            reminders.clear();
            for (DocumentSnapshot snap: values.getDocuments()) {
                if (snap.get("title") != null) {
                    reminders.add(new Reminder(snap.getId(), snap.get("title").toString(), snap.get("description").toString(),
                                               snap.get("date").toString(), snap.get("time").toString()));
                }
                activity.update(null);
            }
        });
    }

    public void addReminder(String title, String description, String date, String time) {
        DocumentReference new_reminder = db.collection("reminders").document();
        Reminder reminder = new Reminder(new_reminder.getId(), title, description, date, time);
        Map<String, String> map = new HashMap<>();
        map.put("id", new_reminder.getId());
        map.put("title", reminder.getTitle());
        map.put("description", reminder.getDescription());
        map.put("date", reminder.getDate());
        map.put("time", reminder.getTime());
        new_reminder.set(map);
        System.out.println("Done inserting new document at: " + new_reminder.getId());
    }

    public void updateReminder(Reminder reminder) {
        DocumentReference ref = db.collection("reminders").document(reminder.getId());
        Map<String,String> map = new HashMap<>();
        map.put("id", reminder.getId());
        map.put("title", reminder.getTitle());
        map.put("description", reminder.getDescription());
        map.put("date", reminder.getDate());
        map.put("time", reminder.getTime());
        ref.set(map);
        System.out.println("Done updating document " + ref.getId());
    }

    public void deleteReminder(String id){
        DocumentReference docRef = db.collection("reminders").document(id);
        docRef.delete();
    }

    public Reminder getReminderWith(String id){
        for(Reminder reminder : reminders){
            if(reminder.getId().equals(id)){
                return reminder;
            }
        }
        return null;
    }

    public void setUp(Updateable a, ArrayList<Reminder> _list) {
        activity = a;
        reminders = _list;
        readReminders();
    }

}
