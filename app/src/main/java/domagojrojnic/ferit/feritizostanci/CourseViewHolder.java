package domagojrojnic.ferit.feritizostanci;

import android.view.View;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CourseViewHolder extends RecyclerView.ViewHolder {

    private TextView tvCourseName, tvAbsenceUpToNow, tvTotalAbsenceAllowed;

    public CourseViewHolder(@NonNull final View itemView) {
        super(itemView);
        tvCourseName = itemView.findViewById(R.id.tvCourseName);
        tvAbsenceUpToNow = itemView.findViewById(R.id.tvAbsenceUpToNow);
        tvTotalAbsenceAllowed = itemView.findViewById(R.id.tvTotalAbsenceAllowed);
    }

    public void setCourseName(String name){
        tvCourseName.setText(name);
    }

    public void setAbsenceUpToNow(String number){ tvAbsenceUpToNow.setText(number); }

    public void setTotalAbsenceAllowed(String number) { tvTotalAbsenceAllowed.setText(number);}
}
