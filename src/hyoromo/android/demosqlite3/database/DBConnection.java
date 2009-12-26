package hyoromo.android.demosqlite3.database;

import hyoromo.android.demosqlite3.database.chema.DemoSchema;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DB制御
 * 
 * @author hyoromo
 */
public class DBConnection extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "test.db";  // 拡張子は合ってもなくても良い
    private static final int DATABASE_VERSION = 1;          // DBはバージョン管理されている
    private static DBConnection mCon;

    public static DBConnection getInstance(Context context) {
        if (mCon == null) {
            mCon = new DBConnection(context);
        }
        return mCon;
    }

    public DBConnection(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * DBが無いときに呼ばれる
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + DemoSchema.TABLE_NAME + " (" + DemoSchema.ID + " text primary key, "
                + DemoSchema.VALUE + " text)";
        db.execSQL(sql);
    }

    /**
     * DBを作り直す時に呼ばれる
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop " + DemoSchema.TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }
}
