package domagojrojnic.ferit.feritizostanci.database_course;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseDao {

    @Query("SELECT * FROM Course")
    LiveData<List<Course>> getAll();

    @Query("SELECT SUM(CAST(absenceUpToNow AS INT)) FROM Course")
    int absenceSum();

    @Query("DELETE FROM Course")
    void deleteAll();

    @Insert
    void insert(Course course);

    @Delete
    void delete(Course course);

    @Update
    void update(Course course);
}
