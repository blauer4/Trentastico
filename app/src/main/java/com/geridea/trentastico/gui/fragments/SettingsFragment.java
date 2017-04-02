
package com.geridea.trentastico.gui.fragments;

/*
 * Created with ♥ by Slava on 19/03/2017.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.alexvasilkov.android.commons.utils.Views;
import com.geridea.trentastico.R;
import com.geridea.trentastico.database.Cacher;
import com.geridea.trentastico.gui.views.CourseSelectorView;
import com.geridea.trentastico.model.ExtraCourse;
import com.geridea.trentastico.model.StudyCourse;
import com.geridea.trentastico.services.LessonsUpdaterService;
import com.geridea.trentastico.utils.AppPreferences;
import com.threerings.signals.Listener1;
import com.threerings.signals.Signal1;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class SettingsFragment extends IFragmentWithMenuItems {

    //Study course
    @BindView(R.id.current_study_course) TextView currentStudyCourse;

    //Lessons updates
    @BindView(R.id.search_for_lesson_changes) Switch searchForLessonChanges;
    @BindView(R.id.lesson_change_show_notification) Switch shownNotificationOnLessonChanges;

    //Next lesson notification
    @BindView(R.id.current_notification_anticipation) TextView currentNotificationAnticipation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);

        //Study courses
        StudyCourse studyCourse = AppPreferences.getStudyCourse();
        currentStudyCourse.setText(studyCourse.generateFullDescription());

        //Lesson changes
        searchForLessonChanges.setChecked(AppPreferences.isSearchForLessonChangesEnabled());
        shownNotificationOnLessonChanges.setChecked(AppPreferences.isNotificationForLessonChangesEnabled());

        return view;
    }

    ///////////////////////////
    ////LESSONS UPDATES
    ///////////////////////////

    @OnClick(R.id.change_study_course_button)
    void onChangeStudyCourseButtonPressed(){
        ChangeStudyCourseDialog dialog = new ChangeStudyCourseDialog(getActivity());
        dialog.onChoiceMade.connect(new Listener1<StudyCourse>() {
            @Override
            public void apply(StudyCourse studyCourse) {
                currentStudyCourse.setText(studyCourse.generateFullDescription());
            }
        });
        dialog.show();
    }

    @OnCheckedChanged(R.id.search_for_lesson_changes)
    void onSearchForLessonsSwitchChanged(boolean enabled){
        AppPreferences.setSearchForLessonChangesEnabled(enabled);
        shownNotificationOnLessonChanges.setEnabled(enabled);

        if (enabled) {
            getActivity().startService(
                LessonsUpdaterService.createServiceIntent(getActivity(), LessonsUpdaterService.STARTER_SETTING_CHANGED)
            );
        } else {
            LessonsUpdaterService.cancelSchedules(getActivity(), LessonsUpdaterService.STARTER_SETTING_CHANGED);
        }
    }

    @OnCheckedChanged(R.id.search_for_lesson_changes)
    void onShowLessonChangeNotificationSwitchChanged(boolean checked){
        AppPreferences.setNotificationForLessonChangesEnabled(checked);
    }

    ///////////////////////////
    ////NEXT LESSON NOTIFICATION
    ///////////////////////////

    @OnCheckedChanged(R.id.show_next_lesson_notification)
    void onShowNextLessonNotificationSwitchChanged(boolean checked){

    }

    @OnCheckedChanged(R.id.make_notifications_fixed)
    void onMakeNotificationsFixedSwitchChanged(boolean checked){

    }

    @OnClick(R.id.next_lesson_notification_anticipation_button)
    void onChangeNextLessonNotificationAnticipationButtonPressed(){

    }

    @Override
    public int[] getIdsOfMenuItemsToMakeVisible() {
        return new int[0];
    }

    @Override
    public void bindMenuItem(MenuItem item) {
        //Does not uses menus, nothing to bind!
    }


    protected class ChangeStudyCourseDialog extends AlertDialog {

        /**
         * Dispatched when the user changed or did not change the study course and just pressed OK.
         */
        public final Signal1<StudyCourse> onChoiceMade = new Signal1<>();

        @BindView(R.id.course_selector) CourseSelectorView courseSelector;

        protected ChangeStudyCourseDialog(@NonNull Context context) {
            super(context);

            final View view = Views.inflate(context, R.layout.dialog_change_study_course);
            ButterKnife.bind(this, view);

            courseSelector.setStudyCourse(AppPreferences.getStudyCourse());

            setView(view);
        }

        @OnClick(R.id.change_button)
        void onChangeStudyCourseButtonClicked(){
            StudyCourse selectedCourse = courseSelector.getSelectedStudyCourse();
            if (AppPreferences.getStudyCourse().equals(selectedCourse)) {
                //We just clicked ok without changing our course...
                onChoiceMade.dispatch(selectedCourse);
            } else {

                AppPreferences.removeAllHiddenCourses(); //No longer need them
                AppPreferences.removeAllHiddenPartitionings(); //No longer need them
                clearCache(selectedCourse);
                AppPreferences.removeExtraCoursesHaving(selectedCourse.getCourseId(), selectedCourse.getYear());

                AppPreferences.setStudyCourse(selectedCourse);

                onChoiceMade.dispatch(selectedCourse);
            }

            dismiss();
        }

        private void clearCache(StudyCourse selectedCourse) {
            //We changed our course, let's wipe out all the cache!
            Cacher.purgeStudyCourseCache();

            //If we've just selected a course that we already had in our extra course, we need to
            //delete that course from cache
            ArrayList<ExtraCourse> overlappingExtraCourses = AppPreferences.getExtraCoursesHaving(
                    selectedCourse.getCourseId(), selectedCourse.getYear()
            );

            for (ExtraCourse overlappingExtraCourse : overlappingExtraCourses) {
                Cacher.removeExtraCoursesWithLessonType(overlappingExtraCourse.getLessonTypeId());
            }
        }

    }

}
