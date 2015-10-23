package yang.acticlescraper.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//Basic Database
public class ArticleDBHelper  extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Articles.db";
    private String SQL_CREATE_QUERY = "CREATE TABLE Articles (id integer primary key AUTOINCREMENT, link varchar(200),title varchar(200), date TIMESTAMP  DEFAULT CURRENT_TIMESTAMP);";
    private String SQL_DELETE_QUERY = "DROP TABLE IF EXISTS Articles";
    public ArticleDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_QUERY);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(SQL_DELETE_QUERY);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}