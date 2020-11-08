package domagojrojnic.ferit.feritizostanci.database_course;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {
    private CourseDao courseDao;
    private LiveData<List<Course>> courses;
    private static int sum;

    public CourseViewModel(@NonNull Application application) {
        super(application);
        CourseAppDatabase database = CourseAppDatabase.getDatabase(application);
        courseDao = database.courseDao();
        courses = courseDao.getAll();
    }

    public LiveData<List<Course>> getCourses(){
        return courses;
    }

    public void insertCourse(Course course){
        new InsertCourseAsync(courseDao).execute(course);
    }

    public void updateCourse(Course course){
        new UpdateCourseAsync(courseDao).execute(course);
    }

    public void deleteCourse(Course course){
        new DeleteCourseAsync(courseDao).execute(course);
    }

    public int getAbsenceSum() {
        new GetSummaryAsync(courseDao).execute();
        return sum;
    }

    public void deleteAllCourses(){
        new DeleteAllAsync(courseDao).execute();
    }

    public static class InsertCourseAsync extends AsyncTask<Course, Void, Void>{
        private CourseDao courseDao;

        public InsertCourseAsync(CourseDao courseDao){
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Course... courses) {
            courseDao.insert(courses[0]);
            return null;
        }
    }

    public static class UpdateCourseAsync extends AsyncTask<Course, Void, Void>{

        private CourseDao courseDao;

        public UpdateCourseAsync(CourseDao courseDao){
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Course... courses) {
            courseDao.update(courses[0]);
            return null;
        }
    }

    public static class DeleteCourseAsync extends AsyncTask<Course, Void, Void>{
        private CourseDao courseDao;

        public DeleteCourseAsync(CourseDao courseDao){
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Course... courses) {
            courseDao.delete(courses[0]);
            return null;
        }
    }


    public static class GetSummaryAsync extends AsyncTask<Void, Void, Void>{
        private CourseDao courseDao;

        public GetSummaryAsync(CourseDao courseDao){
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            sum = courseDao.absenceSum();
            return null;
        }
    }

    private class DeleteAllAsync extends AsyncTask<Void, Void, Void>{
        private CourseDao courseDao;
        public DeleteAllAsync(CourseDao courseDao) {
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            courseDao.deleteAll();
            return null;
        }
    }
}


