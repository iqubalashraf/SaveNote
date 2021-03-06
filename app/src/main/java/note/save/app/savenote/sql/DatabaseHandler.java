package note.save.app.savenote.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import note.save.app.savenote.dataModel.Note;
import note.save.app.savenote.utils.GeneralUtil;

/**
 * Created by ashrafiqubal on 21/01/18.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String TAG = DatabaseHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "SAVE_NOTES";                      // Database Name
    private static final String TABLE_ALL_NOTES = "NOTES";                         // Note table name
    private static final String KEY_ID = "ID";                                     // ID of each note
    private static final String KEY_TITLE = "TITLE";                               // TITLE of each note
    private static final String KEY_DESCRIPTION = "DESCRIPTION";                   // description of each note
    private static final String KEY_LAST_UPDATE_TIME = "LAST_UPDATE_TIME";         // last updated time of note
    private static final String KEY_IS_HEARTED = "KEY_IS_HEARTED";                 // is user heart the note
    private static final String KEY_IS_STAR = "KEY_IS_STAR";                       // is user star the note
    private static final String KEY_IS_POEM = "KEY_IS_POEM";                       // is this a poem note or story, if poem return true else false



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_ALL_NOTES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT," + KEY_DESCRIPTION + " TEXT,"
                + KEY_LAST_UPDATE_TIME + " INTEGER,"+ KEY_IS_HEARTED + " INTEGER," + KEY_IS_STAR + " INTEGER," +KEY_IS_POEM + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALL_NOTES);

        // Create tables again
        onCreate(db);
    }

    // Adding new Note
    public boolean addNote(String note_title, String note_description ){
        try{
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_TITLE, note_title);
            values.put(KEY_DESCRIPTION, note_description);
            values.put(KEY_LAST_UPDATE_TIME, new Date().getTime());//address title
            values.put(KEY_IS_HEARTED, 0);
            values.put(KEY_IS_STAR, 0);
            values.put(KEY_IS_POEM, 0);

            // Inserting Row
            db.insert(TABLE_ALL_NOTES,null,values);
            db.close(); // Closing database connection
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }

    // Updating old Note
    public boolean updateNote(int id, String note_title, String note_description ){
        try{
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_TITLE, note_title);
            values.put(KEY_DESCRIPTION, note_description);
            values.put(KEY_LAST_UPDATE_TIME, new Date().getTime());//address title
            db.update(TABLE_ALL_NOTES, values, KEY_ID + "=" + id, null);
            db.close(); // Closing database connection
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }

    // Getting Note by ID
    public Note getNote(int id){
        Note note = new Note();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ALL_NOTES, new String[] { KEY_ID,
                        KEY_TITLE, KEY_DESCRIPTION, KEY_LAST_UPDATE_TIME, KEY_IS_HEARTED, KEY_IS_STAR, KEY_IS_POEM }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        note.setId(id);
        note.setTitle(cursor.getString(1));
        note.setDescription(cursor.getString(2));
        note.setLast_updated_time(cursor.getLong(3));
        note.setIs_hearted(cursor.getInt(4));
        note.setIs_star(cursor.getInt(5));
        note.setIs_poem(cursor.getInt(6));
        db.close();
        return note;
    }

    // Getting All Notes
    public List<Note> getAllNotes(){
        // Select All Query
        List<Note> notes = new ArrayList<>();
        String selectQuery = "";
        if(!GeneralUtil.isStar() && !GeneralUtil.isHearted()){
            selectQuery = "SELECT  * FROM " + TABLE_ALL_NOTES;
        }else if(!GeneralUtil.isStar()){
            selectQuery = "SELECT  * FROM " + TABLE_ALL_NOTES + " WHERE " + KEY_IS_HEARTED + " = " + 1/*(GeneralUtil.isHearted()?1:0)*/;
        }else if(!GeneralUtil.isHearted()){
            selectQuery = "SELECT  * FROM " + TABLE_ALL_NOTES + " WHERE " + KEY_IS_STAR + " = " + 1/*(GeneralUtil.isStar()?1:0)*/;
        }else {
            selectQuery = "SELECT  * FROM " + TABLE_ALL_NOTES + " WHERE " + KEY_IS_STAR + " = " + 1/*(GeneralUtil.isStar()?1:0)*/ +
                    " AND " +KEY_IS_HEARTED + " = " + 1/*(GeneralUtil.isHearted()?1:0)*/;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(0));
                note.setTitle(cursor.getString(1));
                note.setDescription(cursor.getString(2));
                note.setLast_updated_time(cursor.getLong(3));
                note.setIs_hearted(cursor.getInt(4));
                note.setIs_star(cursor.getInt(5));
                note.setIs_poem(cursor.getInt(6));
                notes.add(note);
            } while (cursor.moveToNext());
        }
        Collections.sort(notes);
        db.close();
        return notes;
    }

    // Getting Notes Count
    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ALL_NOTES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int len = cursor.getCount();
        cursor.close();
        db.close();
        return len;
    }

    // Deleting single Note
    public boolean deleteNote(int id) {
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_ALL_NOTES, KEY_ID + " = ?",
                    new String[] { String.valueOf(id) });
            db.close();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }


    public boolean updateHeartStatus(int id, int new_status){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(KEY_IS_HEARTED, new_status);
            db.update(TABLE_ALL_NOTES, cv, KEY_ID + "=" + id, null);
            db.close();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateStarStatus(int id, int new_status){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(KEY_IS_STAR, new_status);
            db.update(TABLE_ALL_NOTES, cv, KEY_ID + "=" + id, null);
            db.close();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }


}
