package com.mickaelbrenoit.demo.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

import com.mickaelbrenoit.demo.database.dao.UserDao;
import com.mickaelbrenoit.demo.database.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

@Database(entities = {User.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class DatabaseSingleton extends RoomDatabase {

    public abstract UserDao userDao();

    private static DatabaseSingleton INSTANCE;

    public static DatabaseSingleton getAppDatabase(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room
                    .databaseBuilder(context.getApplicationContext(), DatabaseSingleton.class, Names.DATABASE_NAME)
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                @Override
                                public void run() {
                                    List<User> users = new ArrayList<>();
                                    users.add(new User(1, "Leanne Graham", "Bret", "Sincere@april.biz", "b12345"));
                                    users.add(new User(2, "Ervin Howell", "Antonette", "Shanna@melissa.tv", "a12345"));
                                    users.add(new User(3, "Clementine Bauch", "Samantha", "Nathan@yesenia.net", "s12345"));
                                    users.add(new User(4, "Patricia Lebsack", "Karianne", "Julianne.OConner@kory.org", "k12345"));
                                    users.add(new User(5, "Chelsey Dietrich", "Kamren", "Lucio_Hettinger@annie.ca", "k12345"));
                                    users.add(new User(6, "Mrs. Dennis Schulist", "Leopoldo_Corkery", "Karley_Dach@jasper.info", "lc12345"));
                                    users.add(new User(7, "Kurtis Weissnat", "Elwyn.Skiles", "Telly.Hoeger@billy.biz", "es12345"));
                                    users.add(new User(8, "Nicholas Runolfsdottir V", "Maxime_Nienow", "Sherwood@rosamond.me", "mn12345"));
                                    users.add(new User(9, "Glenna Reichert", "Delphine", "Chaim_McDermott@dana.io", "d12345"));
                                    users.add(new User(10, "Clementina DuBuque", "Moriah.Stanton", "Rey.Padberg@karina.biz", "ms12345"));
                                    getAppDatabase(context).userDao().insertAllUsers(users);
                                }
                            });
                        }
                    })
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
