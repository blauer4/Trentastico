package com.geridea.trentastico

import android.app.Application
import com.alexvasilkov.android.commons.utils.AppContext
import com.amitshekhar.DebugDB
import com.evernote.android.job.JobManager
import com.geridea.trentastico.database.Cacher
import com.geridea.trentastico.logger.BugLogger
import com.geridea.trentastico.network.Networker
import com.geridea.trentastico.services.*
import com.geridea.trentastico.utils.*


/*
 * Created with ♥ by Slava on 03/03/2017.
 */

//TODO: reenable ACRA
//@ReportsCrashes(formUri = "http://collector.tracepot.com/20579ea2", mode = ReportingInteractionMode.TOAST, resToastText = R.string.crash_toast_text)
class TrentasticoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AppPreferences.init(this)

        //Disabling db debug page if in debug mode
        if (!IS_IN_DEBUG_MODE) {
            DebugDB.shutDown()
        }

        AppContext.init(this)

        Networker.init(Cacher(this))

        ColorDispenser.init(this)

        BugLogger.init(this)
        BugLogger.onNewDebugMessageArrived.connect { message ->
            if (IS_IN_DEBUG_MODE) {
                UIUtils.showToastIfInDebug(applicationContext, message)
            }
        }

        JobManager.create(this).addJobCreator(ServicesJobCreator())

        //Lessons updated notifications
        LessonsUpdaterJob.onLessonsChanged.connect { diffResult, courseName ->
            LessonsUpdaterJob.showLessonsChangedNotification(applicationContext, diffResult, courseName)
        }

        //Next lesson notification
        NextLessonNotificationHideService.onLessonNotificationExpired.connect { id ->
            NextLessonNotificationShowService.clearNotificationWithId(this, id)
        }

        NextLessonNotificationShowService.onLessonNotificationToShow.connect { lesson ->
            NextLessonNotificationShowService.showNotificationForLessons(applicationContext, lesson)
        }

        // Showing the new app notification in a specific day
        ShowNewAppNotificationService.showNewAppNotificationIfNeeded()

        //Leave this last since it might have some other dependencies of other singletons
        VersionManager.checkForVersionChangeCode(this)

        //This has to be below version manager in case there are bugs in the scheduling processes
        if(AppPreferences.nextLessonNotificationsEnabled){
            NextLessonNotificationShowService.scheduleNowIfEnabled()
        }

    }

}
