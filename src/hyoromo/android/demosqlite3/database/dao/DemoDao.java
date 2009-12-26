package hyoromo.android.demosqlite3.database.dao;

import hyoromo.android.demosqlite3.database.DBConnection;
import hyoromo.android.demosqlite3.database.dto.DemoDto;
import hyoromo.android.demosqlite3.database.schema.DemoSchema;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DemoDao {
    private DBConnection mCon;

    public DemoDao(Context context) {
        mCon = DBConnection.getInstance(context);
    }

    public void insert(DemoDto dto) {
        ContentValues values = new ContentValues();
        values.put(DemoSchema.ID, dto.id);
        values.put(DemoSchema.VALUE, dto.value);

        SQLiteDatabase db = mCon.getWritableDatabase();
        db.insertOrThrow(DemoSchema.TABLE_NAME, null, values);
        db.close();
    }

    public void update(DemoDto dto) {
        ContentValues values = new ContentValues();
        values.put(DemoSchema.VALUE, dto.value);

        String where = DemoSchema.ID + " = " + dto.id;

        SQLiteDatabase db = mCon.getWritableDatabase();
        db.update(DemoSchema.TABLE_NAME, values, where, null);
        db.close();
    }

    public void delete(DemoDto dto) {
        String where = DemoSchema.ID + " = " + dto.id;

        SQLiteDatabase db = mCon.getWritableDatabase();
        db.delete(DemoSchema.TABLE_NAME, where, null);
        db.close();
    }

    public Cursor selectAll() {
        String[] selection = {
                DemoSchema.ID,
                DemoSchema.VALUE,
        };
        SQLiteDatabase db = mCon.getReadableDatabase();
        Cursor cursor = db.query(DemoSchema.TABLE_NAME, selection, null, null, null, null, null);
        cursor.moveToFirst();

        return cursor;
    }
}