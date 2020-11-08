package domagojrojnic.ferit.feritizostanci.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import domagojrojnic.ferit.feritizostanci.CourseViewHolder;
import domagojrojnic.ferit.feritizostanci.R;
import domagojrojnic.ferit.feritizostanci.database_course.Course;
import domagojrojnic.ferit.feritizostanci.database_course.CourseViewModel;

public class RecyclerAdapter extends RecyclerView.Adapter<CourseViewHolder> {

    private List<Course> courseList = new ArrayList<>();
    private CourseViewModel viewModel;

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cellView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_course, parent, false);
        return new CourseViewHolder(cellView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CourseViewHolder holder, final int position) {
        final Course course = courseList.get(position);

        holder.setCourseName(course.getName());
        holder.setAbsenceUpToNow(course.getAbsenceUpToNow());

        final float totalAbsence = Integer.parseInt(course.getTotalHours())- ((Float.parseFloat(course.getMinimumAttendance())/100) * Integer.parseInt(course.getTotalHours()));
        holder.setTotalAbsenceAllowed(String.valueOf((int)totalAbsence));
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public void setCourseList(List<Course> courses, CourseViewModel viewModel){
        this.viewModel = viewModel;
        this.courseList.clear();
        this.courseList.addAll(courses);
        notifyDataSetChanged();
    }

    public void addNewCell(String courseName, String totalHours, String minimumAttendance, int position){
        if(courseList.size() >= position){
            courseList.add(new Course(courseName, totalHours, minimumAttendance, "0"));
            viewModel.insertCourse(new Course(courseName, totalHours, minimumAttendance, "0"));
            notifyItemInserted(position);
        }
    }

    public int getAbsenceSum(){
        return viewModel.getAbsenceSum();
    }

    public void updateCell(Course course){
        viewModel.updateCourse(course);
    }

    public void removeCell(int position){
        if(courseList.size() > position){
            viewModel.deleteCourse(courseList.get(position));
            courseList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Course getCell(int position){
        return courseList.get(position);
    }
}
