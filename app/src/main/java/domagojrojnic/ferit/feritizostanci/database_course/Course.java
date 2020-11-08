package domagojrojnic.ferit.feritizostanci.database_course;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Course {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private String name;
    private String totalHours;
    private String minimumAttendance;
    private String absenceUpToNow;

    public Course(){}

    @Ignore
    public Course(String name, String totalHours, String minimumAttendance, String absenceUpToNow){
        this.name = name;
        this.totalHours = totalHours;
        this.minimumAttendance = minimumAttendance;
        this.absenceUpToNow = absenceUpToNow;
    }

    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(String totalHours) {
        this.totalHours = totalHours;
    }

    public String getMinimumAttendance() {
        return minimumAttendance;
    }

    public void setMinimumAttendance(String minimumAttendance) {
        this.minimumAttendance = minimumAttendance;
    }

    public String getAbsenceUpToNow() {
        return absenceUpToNow;
    }

    public void setAbsenceUpToNow(String absenceUpToNow) {
        this.absenceUpToNow = absenceUpToNow;
    }
}
