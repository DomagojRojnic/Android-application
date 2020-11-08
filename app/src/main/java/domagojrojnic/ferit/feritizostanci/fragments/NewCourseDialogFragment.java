package domagojrojnic.ferit.feritizostanci.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import domagojrojnic.ferit.feritizostanci.database_course.Course;
import domagojrojnic.ferit.feritizostanci.R;
import domagojrojnic.ferit.feritizostanci.adapters.RecyclerAdapter;

public class NewCourseDialogFragment extends DialogFragment {

    private TextView newCourseName;
    private TextView newCourseTotalHours;
    private TextView newCourseMinimumAttendance;

    private EditText newCourseName_edit;
    private EditText newCourseTotalHours_edit;
    private EditText newCourseMinimumAttendance_edit;

    private FloatingActionButton fabCancel;
    private FloatingActionButton fabConfirm;

    private static RecyclerAdapter recyclerAdapter = new RecyclerAdapter();

    private static Course editCourse;

    public static NewCourseDialogFragment newInstance(RecyclerAdapter adapter, Course course){
        recyclerAdapter = adapter;
        editCourse = course;
        return new NewCourseDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_course, container, false);
        newCourseName = view.findViewById(R.id.tvNewCourseName);
        newCourseTotalHours = view.findViewById(R.id.tvNewCourseTotalHours);
        newCourseMinimumAttendance = view.findViewById(R.id.tvNewCourseMinimumAttendance);
        newCourseName_edit = view.findViewById(R.id.etNewCourseName);
        newCourseTotalHours_edit = view.findViewById(R.id.etNewCourseTotalHours);
        newCourseMinimumAttendance_edit = view.findViewById(R.id.etNewCourseMinimumAttendance);
        fabCancel = view.findViewById(R.id.fabCancelEntry);
        fabConfirm = view.findViewById(R.id.fabConfirmEntry);

        if(editCourse != null){
            newCourseName_edit.setText(editCourse.getName());
            newCourseTotalHours_edit.setText(editCourse.getTotalHours());
            newCourseMinimumAttendance_edit.setText(editCourse.getMinimumAttendance());
        }

        fabCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        fabConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseName = newCourseName_edit.getText().toString();
                String courseTotalHours = newCourseTotalHours_edit.getText().toString();
                String courseMinimumAttendance = newCourseMinimumAttendance_edit.getText().toString();

                if(!courseName.equals("") && !courseTotalHours.equals("") && !courseMinimumAttendance.equals("")){
                    if(editCourse != null){
                        editCourse.setName(courseName);
                        editCourse.setTotalHours(courseTotalHours);
                        editCourse.setMinimumAttendance(courseMinimumAttendance);
                        recyclerAdapter.updateCell(editCourse);
                        getDialog().dismiss();
                    }
                    else{
                        recyclerAdapter.addNewCell(courseName, courseTotalHours, courseMinimumAttendance, recyclerAdapter.getItemCount());
                        getDialog().dismiss();
                    }
                }
                else{
                    Toast.makeText(getContext(), "Sva polja moraju biti popunjena.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}
