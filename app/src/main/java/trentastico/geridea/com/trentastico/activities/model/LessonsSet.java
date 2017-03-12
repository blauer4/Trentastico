package trentastico.geridea.com.trentastico.activities.model;

import java.util.ArrayList;
import java.util.List;

/*
 * Created with ♥ by Slava on 12/03/2017.
 */
public class LessonsSet {

    private final List<LessonType>     lessonTypes;
    private final List<LessonSchedule> scheduledLessons;

    public LessonsSet() {
        lessonTypes      = new ArrayList<>();
        scheduledLessons = new ArrayList<>();
    }

    public List<LessonType> getLessonTypes() {
        return lessonTypes;
    }

    public List<LessonSchedule> getScheduledLessons() {
        return scheduledLessons;
    }

    public void addLessonType(LessonType lessonType) {
        lessonTypes.add(lessonType);
    }

    public void addLessonSchedule(LessonSchedule lessonSchedule) {
        scheduledLessons.add(lessonSchedule);
    }

    public void mergeWith(LessonsSet lessons) {
        //TODO: should check for double lessons and lesson types
        this.scheduledLessons.addAll(lessons.scheduledLessons);
        this.lessonTypes.addAll(lessons.lessonTypes);
    }
}
