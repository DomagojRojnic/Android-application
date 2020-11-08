package domagojrojnic.ferit.feritizostanci.database_course;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Course.class}, version = 1)
public abstract class CourseAppDatabase extends RoomDatabase {
    private static CourseAppDatabase INSTANCE;
    public abstract CourseDao courseDao();

    //synchronized limits access to only one thread at given time
    public static synchronized CourseAppDatabase getDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CourseAppDatabase.class, CourseAppDatabase.class.getName()).build();
        }
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }
}
