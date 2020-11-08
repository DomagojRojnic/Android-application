package domagojrojnic.ferit.feritizostanci.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;


import domagojrojnic.ferit.feritizostanci.R;
import domagojrojnic.ferit.feritizostanci.database_course.CourseViewModel;

public class InfoFragment extends Fragment {
    private TextView overallAbsence;
    private TextView overallAbsenceNumber;
    private TextView coffeeConsumed;
    private TextView coffeeConsumedNumber;
    private TextView spentOnCoffee;
    private TextView spentOnCoffeeNumber;
    private Button concludeSemester;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;

    public static InfoFragment newInstance(){
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sharedPreferences = getContext().getApplicationContext().getSharedPreferences("coffees_data", Context.MODE_PRIVATE);
        return inflater.inflate(R.layout.fragment_info, container, false);
}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final CourseViewModel viewModel = ViewModelProviders.of(this).get(CourseViewModel.class);

        overallAbsence = view.findViewById(R.id.tvOverallAbsence);
        overallAbsenceNumber = view.findViewById(R.id.tvOverallAbsenceNumber);
        coffeeConsumed = view.findViewById(R.id.tvCoffeeConsumed);
        coffeeConsumedNumber = view.findViewById(R.id.tvCoffeeConsumedNumber);
        spentOnCoffee = view.findViewById(R.id.tvSpentOnCoffee);
        spentOnCoffeeNumber = view.findViewById(R.id.tvSpentOnCoffeeNumber);
        concludeSemester = view.findViewById(R.id.btnConcludeSemester);

        this.concludeSemester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.deleteAllCourses();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("coffees_drank", "0");
                editor.putString("coffees_price", "0");
                editor.putString("total_absence", "0");
                editor.apply();
            }
        });

        coffeeConsumedNumber.setText(sharedPreferences.getString("coffees_drank", "0"));
        spentOnCoffeeNumber.setText(sharedPreferences.getString("coffees_price", "0"));
        overallAbsenceNumber.setText(sharedPreferences.getString("total_absence", "0"));

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

                if(key.matches("coffees_drank")){
                    coffeeConsumedNumber.setText(sharedPreferences.getString(key, "0"));
                }

                if(key.matches("coffees_price")){
                    spentOnCoffeeNumber.setText(sharedPreferences.getString("coffees_price", "0"));
                }

                if(key.matches("total_absence")){
                    overallAbsenceNumber.setText(sharedPreferences.getString("total_absence", "0"));
                }
            }
        };
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }
}