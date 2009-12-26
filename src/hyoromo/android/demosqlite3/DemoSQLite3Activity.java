package hyoromo.android.demosqlite3;

import hyoromo.android.demosqlite3.database.dao.DemoDao;
import hyoromo.android.demosqlite3.database.dto.DemoDto;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class DemoSQLite3Activity extends Activity {
    private static ArrayAdapter<String> mAdapter;
    private static ListView mListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,
                new ArrayList<String>());
        mListView = (ListView) findViewById(R.id.listview);
        mListView.setAdapter(mAdapter);
    }

    /**
     * DemoテーブルにInsert
     * 
     * @param v
     */
    public void onClickInsert(View v) {
        DemoDto dto = new DemoDto();
        dto.id = getNewID();
        dto.value = getValue();
        DemoDao dao = new DemoDao(getApplicationContext());
        dao.insert(dto);
        clearValue();
    }

    /**
     * DemoテーブルでチェックされたデータをUpdate
     * 
     * @param v
     */
    public void onClickUpdate(View v) {
        DemoDto dto = new DemoDto();
        dto.id = getOldID();
        dto.value = getValue();
        DemoDao dao = new DemoDao(getApplicationContext());
        dao.update(dto);
        clearValue();
    }

    /**
     * DemoテーブルでチェックされたデータをDelete
     * 
     * @param v
     */
    public void onClickDelete(View v) {
        DemoDto dto = new DemoDto();
        dto.id = getOldID();
        DemoDao dao = new DemoDao(getApplicationContext());
        dao.delete(dto);
    }

    /**
     * Demoテーブルの全データをSelect
     * 
     * @param v
     */
    public void onClickSelect(View v) {
        DemoDao dao = new DemoDao(getApplicationContext());
        Cursor cursor = dao.selectAll();

        // ListViewの中身を作り直す
        mAdapter.clear();
        CharSequence[] list = new CharSequence[cursor.getCount()];
        for (int i = 0; i < list.length; i++) {
            mAdapter.add(cursor.getString(0) + ", " + cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
    }

    /**
     * Demoテーブルに登録するIDを取得
     * 
     * @return タイムスタンプを取得。たぶんこの方法は冗長。
     */
    private String getNewID() {
        Calendar cal = Calendar.getInstance();

        String year = Integer.toString(cal.get(Calendar.YEAR));
        String month = Integer.toString(cal.get(Calendar.MONTH));
        String day = Integer.toString(cal.get(Calendar.DATE));
        String hour = Integer.toString(cal.get(Calendar.HOUR));
        String minute = Integer.toString(cal.get(Calendar.MINUTE));
        String second = Integer.toString(cal.get(Calendar.SECOND));

        return year + month + day + hour + minute + second;
    }

    /**
     * Demoテーブルで一番古いIDを取得
     * 
     * @return ListViewで一番上に表示されているID
     */
    private String getOldID() {
        String text = (String) mListView.getItemAtPosition(0);
        String[] key = text.split(",");

        return key[0];
    }

    /**
     * Demoテーブルに登録するValueを取得
     * 
     * @return Editに入力されたText
     */
    private String getValue() {
        EditText edit = (EditText) findViewById(R.id.edit);

        return edit.getText().toString();
    }

    private void clearValue() {
        EditText edit = (EditText) findViewById(R.id.edit);
        edit.setText("");
    }
}