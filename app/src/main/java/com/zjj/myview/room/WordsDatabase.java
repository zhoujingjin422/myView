package com.zjj.myview.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * @author : "zhoujingjin"
 * @timer : 2020/8/5
 */
@Database(entities = {Words.class},version = 5,exportSchema = false)
public abstract class WordsDatabase extends RoomDatabase {
    private static WordsDatabase INSTANCE;
    static synchronized WordsDatabase getWordsDatabase(Context context){
        if (INSTANCE==null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),WordsDatabase.class,"word_database")
//                    .allowMainThreadQueries()//允许在主线程访问数据库
//                    .fallbackToDestructiveMigration()//暴力删除所有数据，不保存
//                    .fallbackToDestructiveMigrationFrom(2,4)//暴力删除这两个版本的数据
                    .addMigrations(MIGRATION_1_2)
                    .addMigrations(MIGRATION_2_3)
                    .addMigrations(MIGRATION_3_4)
                    .addMigrations(MIGRATION_4_5)
                    .build();
        }
        return INSTANCE;
    }
    public abstract WordsDao getWordsDao();
    //版本从1-2，增加一列数据,默认值是0
    static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE words ADD COLUMN foo_data INTEGER NOT NULL DEFAULT 0");
        }
    };
    //版本从2-3，增加一列数据,默认值是1
    static final Migration MIGRATION_2_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE words ADD COLUMN can_show INTEGER NOT NULL DEFAULT 1");
        }
    };
    //版本从3-4，增加一列数据,默认值是""
    static final Migration MIGRATION_3_4 = new Migration(3,4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE words ADD COLUMN japanese_word TEXT");
        }
    };

    //版本从4-5，删除两列
    static final Migration MIGRATION_4_5 = new Migration(4,5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //首先创建一个临时的表
            database.execSQL("CREATE TABLE words_tamp (id INTEGER PRIMARY KEY NOT NULL,english_word TEXT," +
                    "chinese_word TEXT,foo_data INTEGER NOT NULL DEFAULT 0)");
            //获取原先的数据并放到临时表中
            database.execSQL("INSERT INTO words_tamp (id,english_word,chinese_word,foo_data) SELECT " +
                    "id,english_word,chinese_word,foo_data FROM words");
            //删除原来的表
            database.execSQL("DROP TABLE words");
            //将临时表重新命名为原来的名字
            database.execSQL("ALTER TABLE words_tamp RENAME TO words");
        }
    };
}
