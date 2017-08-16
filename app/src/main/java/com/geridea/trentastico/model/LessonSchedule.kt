package com.geridea.trentastico.model

import com.geridea.trentastico.model.cache.CachedLesson
import com.geridea.trentastico.network.request.LessonsDiffResult
import com.geridea.trentastico.utils.AppPreferences
import com.geridea.trentastico.utils.NumbersUtils
import com.geridea.trentastico.utils.StringUtils
import com.geridea.trentastico.utils.time.CalendarInterval
import com.geridea.trentastico.utils.time.CalendarUtils
import com.geridea.trentastico.utils.time.CalendarUtils.debuggableToday
import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern


class LessonSchedule(
        /**
         * @return the unique identifier of the lesson
         */
        val id: Long,
        var room: String?,
        val subject: String,
        val startsAt: Long,
        val endsAt: Long,
        val fullDescription: String,
        val color: Int,
        val lessonTypeId: Long) : Serializable {

    constructor(cachedLesson: CachedLesson) : this(
            cachedLesson.lesson_id,
            cachedLesson.room,
            cachedLesson.subject,
            cachedLesson.starts_at_ms,
            cachedLesson.finishes_at_ms,
            cachedLesson.description,
            cachedLesson.color,
            cachedLesson.teaching_id
    )

    private fun isMeaningfullyEqualTo(that: LessonSchedule): Boolean {
        return id == that.id
                && startsAt == that.startsAt
                && endsAt == that.endsAt
                && room == that.room
                && subject == that.subject
                && fullDescription == that.fullDescription
    }

    val startCal: Calendar
        get() {
            val calendar = debuggableToday
            calendar.setTimeInMillis(startsAt)
            return calendar
        }

    val endCal: Calendar
        get() {
            val calendar = debuggableToday
            calendar.setTimeInMillis(endsAt)
            return calendar
        }

    val synopsis: String
        get() {
            val room = room

            val hhmm = SimpleDateFormat("HH:mm")
            val startTime = hhmm.format(startCal.time)
            val endTime = hhmm.format(endCal.time)

            return if (room!!.isEmpty()) {
                String.format("%s-%s", startTime, endTime)
            } else {
                String.format("%s-%s | %s", startTime, endTime, room)
            }
        }

    fun hasLessonType(lessonType: LessonType): Boolean {
        return lessonTypeId == lessonType.id.toLong()
    }

    fun matchesPartitioningType(partitioningType: PartitioningType): Boolean {
        return StringUtils.containsMatchingRegex(partitioningType.regex, fullDescription)
    }

    fun matchesAnyOfPartitioningCases(partitionings: ArrayList<PartitioningCase>): Boolean {
        for (partitioning in partitionings) {
            if (fullDescription.contains(partitioning.case)) {
                return true
            }
        }

        return false
    }

    override fun toString(): String {
        return String.format("[id: %d lessonType: %d description: %s ]", id, lessonTypeId, fullDescription)
    }

    val durationInMinutes: Int
        get() = TimeUnit.MILLISECONDS.toMinutes(endsAt - startsAt).toInt()

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false

        val that = o as LessonSchedule?

        return id == that!!.id
                && startsAt == that.startsAt
                && endsAt == that.endsAt
                && color == that.color
                && lessonTypeId == that.lessonTypeId
                && room == that.room
                && subject == that.subject
                && fullDescription == that.fullDescription
    }

    fun startsBefore(currentMillis: Long): Boolean {
        return startsAt < currentMillis
    }

    fun isHeldInMilliseconds(ms: Long): Boolean {
        return startsAt >= ms && ms <= endsAt
    }

    fun hasRoomSpecified(): Boolean {
        return !room!!.isEmpty()
    }

    fun toExpandedCalendarInterval(typeOfTime: Int, delta: Int): CalendarInterval {
        val calFrom = CalendarUtils.getCalendarInitializedAs(startsAt)
        calFrom.add(typeOfTime, -delta)

        val calTo = CalendarUtils.getCalendarInitializedAs(endsAt)
        calTo.add(typeOfTime, delta)

        return CalendarInterval(calFrom, calTo)
    }

    fun hasPartitioning(partitioningText: String): Boolean {
        return fullDescription.contains("($partitioningText)")
    }

    companion object {

        fun diffLessons(
                cachedLessons: ArrayList<LessonSchedule>,
                fetchedLessons: ArrayList<LessonSchedule>): LessonsDiffResult {
            val diffResult = LessonsDiffResult()

            sortByStartDateOrId(fetchedLessons)
            sortByStartDateOrId(cachedLessons)

            val fetchedNotCachedLesson = ArrayList(fetchedLessons)
            for (cached in cachedLessons) {

                var cacheLessonFound = false
                for (fetched in fetchedLessons) {
                    if (cached.id == fetched.id) {
                        //We found the lesson
                        fetchedNotCachedLesson.remove(fetched)

                        if (!cached.isMeaningfullyEqualTo(fetched)) {
                            diffResult.addChangedLesson(cached, fetched)
                        }

                        cacheLessonFound = true
                        break
                    }
                }

                if (!cacheLessonFound) {
                    diffResult.addRemovedLesson(cached)
                }
            }

            for (lessonNotInCache in fetchedNotCachedLesson) {
                diffResult.addAddedLesson(lessonNotInCache)
            }

            return diffResult
        }

        fun findCourseAndYearForLesson(lesson: LessonSchedule): CourseAndYear {
            for (extraCourse in AppPreferences.extraCourses) {
                if (extraCourse.lessonTypeId.toLong() == lesson.lessonTypeId) {
                    return extraCourse.courseAndYear
                }
            }

            return AppPreferences.studyCourse.courseAndYear
        }

        fun getLessonsOfType(
                lessonType: LessonType,
                lessons: Collection<LessonSchedule>): ArrayList<LessonSchedule> =
                lessons.filterTo(ArrayList()) { it.lessonTypeId == lessonType.id.toLong() }

        fun filterLessons(lessonsToFilter: MutableCollection<LessonSchedule>) {
            val lessonsIterator = lessonsToFilter.iterator()

            while (lessonsIterator.hasNext()) {
                val lesson = lessonsIterator.next()

                var wasRemoved = false

                //Checking if is filtered because of the lesson type
                for (lessonTypeIdToHide in AppPreferences.lessonTypesIdsToHide) {
                    if (lesson.lessonTypeId == lessonTypeIdToHide) {
                        lessonsIterator.remove()
                        wasRemoved = true
                        break
                    }
                }

                //Checking if is filtered because of the partitioning
                if (!wasRemoved) {
                    for (partitioning in AppPreferences.getHiddenPartitionings(lesson.lessonTypeId)) {
                        if (lesson.hasPartitioning(partitioning)) {
                            lessonsIterator.remove()
                            break
                        }
                    }
                }
            }
        }


        @Throws(JSONException::class)
        fun fromJson(json: JSONObject): LessonSchedule {
            val titleToParse = json.getString("title")

            val id = java.lang.Long.valueOf(json.getString("url").substring(1))!! //url: "#123453"

            val room = getRoomFromTitle(titleToParse)
            val subject = getSubjectFromTitle(titleToParse)

            val start = json.getLong("start") * 1000
            val end = json.getLong("end") * 1000

            val teachingId = json.getInt("id").toLong()

            val color = LessonType.getColorFromCSSStyle(json.getString("className"))

            return LessonSchedule(id, room, subject, start, end, titleToParse, color, teachingId)
        }

        private fun getSubjectFromTitle(titleToParse: String): String {
            val pattern = Pattern.compile("^(.+)\\n")
            val matcher = pattern.matcher(titleToParse)
            matcher.find()
            return matcher.group(1)
        }

        private fun getRoomFromTitle(titleToParse: String): String {
            val pattern = Pattern.compile("\\[(.+)\\]$")
            val matcher = pattern.matcher(titleToParse)
            return if (matcher.find()) {
                matcher.group(1)
            } else {
                ""
            }
        }

        fun sortByStartDateOrId(lessons: ArrayList<LessonSchedule>) {
            Collections.sort(lessons) { a, b ->
                var compare = NumbersUtils.compare(a.startsAt, b.startsAt)
                if (compare == 0) {
                    compare = NumbersUtils.compare(a.id, b.id)
                }

                compare
            }
        }
    }
}
