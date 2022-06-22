package br.com.dlweb.maternidade_ads.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import br.com.dlweb.maternidade_ads.R;
import br.com.dlweb.maternidade_ads.medico.Medico;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "maternidade";
    private static final String TABLE_MEDICO = "medico";

    private static final String CREATE_TABLE_MEDICO = "CREATE TABLE " + TABLE_MEDICO + " (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nome VARCHAR(100), " +
            "especialidade VARCHAR(100)," +
            "celular VARCHAR(15));";

    private static final String DROP_TABLE_MEDICO = "DROP TABLE IF EXISTS " + TABLE_MEDICO;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MEDICO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_MEDICO);
        onCreate(db);
    }

    /* Início CRUD Médico */
    public long createMedico (Medico m) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", m.getNome());
        cv.put("especialidade", m.getEspecialidade());
        cv.put("celular", m.getCelular());
        long id = db.insert(TABLE_MEDICO, null, cv);
        db.close();
        return id;
    }
    public long updateMedico (Medico m) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", m.getNome());
        cv.put("especialidade", m.getEspecialidade());
        cv.put("celular", m.getCelular());
        long id = db.update(TABLE_MEDICO, cv,
                "_id = ?", new String[]{String.valueOf(m.getId())});
        db.close();
        return id;
    }
    public long deleteMedico (Medico m) {
        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.delete(TABLE_MEDICO, "_id = ?",
                new String[]{String.valueOf(m.getId())});
        db.close();
        return id;
    }
    public void getAllMedico (Context context, ListView lv) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"_id", "nome", "celular"};
        Cursor data = db.query(TABLE_MEDICO, columns, null, null,
                null, null, "nome");
        int[] to = {R.id.textViewIdListarMedico, R.id.textViewNomeListarMedico,
                R.id.textViewCelularListarMedico};
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context,
                R.layout.medico_item_list_view, data, columns, to, 0);
        lv.setAdapter(simpleCursorAdapter);
        db.close();
    }
    public Medico getByIdMedico (int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"_id", "nome", "especialidade", "celular"};
        String[] args = {String.valueOf(id)};
        Cursor data = db.query(TABLE_MEDICO, columns, "_id = ?", args,
                null, null, null);
        data.moveToFirst();
        Medico m = new Medico();
        m.setId(data.getInt(0));
        m.setNome(data.getString(1));
        m.setEspecialidade(data.getString(2));
        m.setCelular(data.getString(3));
        data.close();
        db.close();
        return m;
    }
    /* Fim CRUD Médico */
}
