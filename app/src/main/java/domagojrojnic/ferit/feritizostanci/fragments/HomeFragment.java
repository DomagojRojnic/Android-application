package domagojrojnic.ferit.feritizostanci.fragments;

import android.graphics.Color;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import domagojrojnic.ferit.feritizostanci.database_course.Course;
import domagojrojnic.ferit.feritizostanci.database_course.CourseViewModel;
import domagojrojnic.ferit.feritizostanci.R;
import domagojrojnic.ferit.feritizostanci.adapters.RecyclerAdapter;
import domagojrojnic.ferit.feritizostanci.recyclerViewSwipe.SwipeHelper;


public class HomeFragment extends Fragment {

    private FloatingActionButton addCourse;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private CourseViewModel viewModel;

    public static HomeFragment newInstance(){
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewModel = ViewModelProviders.of(HomeFragment.this).get(CourseViewModel.class);
        viewModel.getCourses().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                recyclerAdapter.setCourseList(courses, viewModel);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        addCourse = view.findViewById(R.id.fabAddCourse);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerAdapter = new RecyclerAdapter();
        recyclerView.setAdapter(recyclerAdapter);

        SwipeHelper swipeHelper = new SwipeHelper(getContext(), recyclerView) {
            @Override
            public void instantiateUnderlayButton(final RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {

                underlayButtons.add(new SwipeHelper.UnderlayButton("Obriši", 0, Color.parseColor("#FF3C30"),
                        new SwipeHelper.UnderlayButtonClickListener(){
                            @Override
                            public void onClick(int pos) {
                                recyclerAdapter.removeCell(pos);
                            }
                        }
                ));

                underlayButtons.add(new SwipeHelper.UnderlayButton("Uredi", 0, Color.parseColor("#0000FF"),
                        new SwipeHelper.UnderlayButtonClickListener(){
                            @Override
                            public void onClick(int pos) {

                                Course course = recyclerAdapter.getCell(pos);
                                NewCourseDialogFragment dialogFragment = NewCourseDialogFragment.newInstance(recyclerAdapter, course);
                                dialogFragment.show(getFragmentManager(),"NewCourseDialogFragment");
                            }
                        }
                ));

                underlayButtons.add(new SwipeHelper.UnderlayButton("Izostani", 0, Color.parseColor("#35424a"),
                        new SwipeHelper.UnderlayButtonClickListener(){
                            @Override
                            public void onClick(int pos) {

                                Course course = recyclerAdapter.getCell(pos);
                                MissClassDialogFragment missClassDialogFragment = MissClassDialogFragment.newInstance(recyclerAdapter, course);
                                missClassDialogFragment.show(getFragmentManager(),"MissClassDialogFragment");
                            }
                        }
                ));
            }
        };

        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewCourseDialogFragment dialogFragment = NewCourseDialogFragment.newInstance(recyclerAdapter, null);
                dialogFragment.show(getFragmentManager(),"NewCourseDialogFragment");
            }
        });
    }
}
