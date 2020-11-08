package domagojrojnic.ferit.feritizostanci.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import domagojrojnic.ferit.feritizostanci.R;
import domagojrojnic.ferit.feritizostanci.adapters.RecyclerAdapter;
import domagojrojnic.ferit.feritizostanci.database_course.Course;

public class MissClassDialogFragment extends DialogFragment {

    private TextView hoursMissing;
    private TextView coffeeQuestion;
    private TextView coffeePrice;
    private TextView coffeeAmount;

    private EditText hoursMissingNumber;
    private EditText coffeePriceNumber;
    private EditText coffeeAmountNumber;

    private Spinner coffeeQuestionDropDown;

    private FloatingActionButton fabCancel;
    private FloatingActionButton fabConfirm;

    private static RecyclerAdapter recyclerAdapter = new RecyclerAdapter();

    private static Course course;

    public static MissClassDialogFragment newInstance(RecyclerAdapter adapter, Course course1){
        recyclerAdapter = adapter;
        course = course1;
        return new MissClassDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_miss_class, container, false);

        hoursMissing = view.findViewById(R.id.tvHoursMissing);
        coffeeQuestion = view.findViewById(R.id.tvCoffeeQ);
        coffeePrice = view.findViewById(R.id.tvCoffeePrice);
        coffeeAmount = view.findViewById(R.id.tvCoffeeAmount);

        hoursMissingNumber = view.findViewById(R.id.etHoursMissingNumber);
        coffeePriceNumber = view.findViewById(R.id.etCoffeePriceNumber);
        coffeeAmountNumber = view.findViewById(R.id.etCoffeeAmountNumber);

        coffeeQuestionDropDown = view.findViewById(R.id.spCoffeeQ);

        fabConfirm = view.findViewById(R.id.fabConfirmEntryMiss);
        fabCancel = view.findViewById(R.id.fabCancelEntryMiss);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("NE");
        arrayList.add("DA");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext().getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, arrayList);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        coffeeQuestionDropDown.setAdapter(adapter);
        coffeeQuestionDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        coffeePrice.setVisibility(View.INVISIBLE);
                        coffeePriceNumber.setVisibility(View.INVISIBLE);
                        coffeeAmount.setVisibility(View.INVISIBLE);
                        coffeeAmountNumber.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        coffeePrice.setVisibility(View.VISIBLE);
                        coffeePriceNumber.setVisibility(View.VISIBLE);
                        coffeeAmount.setVisibility(View.VISIBLE);
                        coffeeAmountNumber.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(), "Padajući izbornik mora imati odabranu opciju.", Toast.LENGTH_SHORT).show();
            }
        });

        fabCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        fabConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String missing = hoursMissingNumber.getText().toString();
                String question = coffeeQuestionDropDown.getSelectedItem().toString();
                String coffeePrice = coffeePriceNumber.getText().toString();
                String coffeeAmount = coffeeAmountNumber.getText().toString();

                SharedPreferences sharedPreferences = getContext().getApplicationContext().getSharedPreferences("coffees_data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if(question.matches("DA") && !missing.equals("") && !coffeePrice.equals("") && !coffeeAmount.equals("")){

                    int absence = Integer.parseInt(course.getAbsenceUpToNow()) + Integer.parseInt(missing);
                    course.setAbsenceUpToNow(String.valueOf(absence));
                    recyclerAdapter.updateCell(course);

                    int overallAbsence = recyclerAdapter.getAbsenceSum() + Integer.parseInt(missing);

                    String coffeesUpToNow = sharedPreferences.getString("coffees_drank", "0");
                    int newCoffeeNumber = Integer.parseInt(coffeesUpToNow) + Integer.parseInt(coffeeAmount);

                    int coffeesPriceUpToNow = Integer.parseInt(sharedPreferences.getString("coffees_price", "0"));
                    int price = Integer.parseInt(coffeePrice) * Integer.parseInt(coffeeAmount);
                    String currentPrice = String.valueOf(coffeesPriceUpToNow + price);

                    editor.putString("coffees_drank", String.valueOf(newCoffeeNumber));
                    editor.putString("coffees_price", currentPrice);
                    editor.putString("total_absence", String.valueOf(overallAbsence));

                    editor.apply();

                    getDialog().dismiss();
                }

                else if(question.matches("NE")  &&  !missing.equals("")){

                    int absence = Integer.parseInt(course.getAbsenceUpToNow()) + Integer.parseInt(missing);
                    course.setAbsenceUpToNow(String.valueOf(absence));
                    recyclerAdapter.updateCell(course);

                    int overallAbsence = recyclerAdapter.getAbsenceSum() + Integer.parseInt(missing);
                    editor.putString("total_absence", String.valueOf(overallAbsence));
                    editor.apply();

                    getDialog().dismiss();
                }
                else{
                    Toast.makeText(getContext(), "Sva polja moraju biti popunjena.", Toast.LENGTH_SHORT).show();
                }
                editor.putString("total_absence", String.valueOf(recyclerAdapter.getAbsenceSum()));
                editor.apply();
            }
        });
        return view;
    }
}
