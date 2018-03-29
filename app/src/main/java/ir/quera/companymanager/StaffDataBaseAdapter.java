package ir.quera.companymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Shahrzad on 26/03/2018.
 */

public class StaffDataBaseAdapter {

    private Context mContext;
    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase database;


    public StaffDataBaseAdapter(Context mContext) {
        this.mContext = mContext;
        sqLiteOpenHelper = new SQLiteOpenHelper(mContext, "company", null, 1) {

            @Override
            public void onCreate(SQLiteDatabase db) {

                String sql = "create table Staff (ID integer primary key, FIRSTNAME text, LASTNAME text, " +
                        "SALARY int, HOUR int, POSITION text)";
                db.execSQL(sql);
                Log.d("Database", sql);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            }
        };
    }

    public long saveStaff(Staff staff) {

        String name = staff.getS_name();
        String family = staff.getS_Family();
        int salary = staff.getS_per_hour();
        int hour = staff.getS_hour();
        int position = staff.getS_position();
        long id = -1;

        try {
            ContentValues values = new ContentValues();
            // value.put("ID",id);
            values.put("FIRSTNAME", name);
            values.put("LASTNAME", family);
            values.put("SALARY", salary);
            values.put("HOUR", hour);
            values.put("POSITION", position);

            database = sqLiteOpenHelper.getWritableDatabase();
            id = database.insert("Staff", null, values);

        } catch (Exception ex) {
            Log.d("Database", "Exception:" + ex.getMessage());
        } finally {
            if (database != null && database.isOpen()) {
                database.close();
            }
        }

        return id;
    }

    public int deleteStaff(long id) {

        int noOfDeleteRecords = 0;
        String whereClause = "id=?";
        String[] whereArgs = new String[]{String.valueOf(id)};

        database = null;

        try {

            database = sqLiteOpenHelper.getWritableDatabase();
            noOfDeleteRecords = database.delete("Staff", whereClause, whereArgs);

        } catch (Exception ex) {
            Log.d("DataBase", "Exeption" + ex.getMessage());
        } finally {

            if (database != null && database.isOpen()) {
                database.close();
            }

        }

        return noOfDeleteRecords;

    }

    public int updateStaff(Staff staff) {
        int noOfUpdatingRecords = 0;
        String whereCluse = "id=?";
        String[] whereArgs = new String[]{String.valueOf(staff.getId())};

        database = null;

        try {
            ContentValues values = new ContentValues();
            values.put("Code",staff.getId());
            values.put("FIRSTNAME", staff.getS_name());
            values.put("LASTNAME", staff.getS_Family());
            values.put("SALARY", staff.getS_per_hour());
            values.put("HOUR", staff.getS_hour());
            values.put("POSITION", staff.getS_position());

            database = sqLiteOpenHelper.getWritableDatabase();
            noOfUpdatingRecords = database.update("Staff", values, whereCluse, whereArgs);
        } catch (Exception ex) {
            Log.d("DataBase", "Exeption" + ex.getMessage());
        } finally {
            if (database != null && database.isOpen()) {
                database.close();
            }
        }

        return noOfUpdatingRecords;

    }

    public Staff readStaff(long id) {

        Staff staff = null;
        String[] columns = new String[]{"*"};
        String selection = "id=?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;

        SQLiteDatabase database = null;
        try {
            database = sqLiteOpenHelper.getWritableDatabase();
            Cursor cursor = database.query("Staff", columns, selection, selectionArgs, groupBy, having, orderBy, limit);
            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = 0;
                int nameIndex = 1;
                int familyIndex = 2;

                long staffId = cursor.getLong(idIndex);
                String staffName = cursor.getString(nameIndex);
                String staffFamily = cursor.getString(familyIndex);

                staff = new Staff();
                staff.setId(staffId);
                staff.setS_name(staffName);
                staff.setS_Family(staffFamily);
            }

        } catch(Exception ex){
            Log.d("Database", "Exception:" + ex.getMessage());
        } finally{
            if (database != null && database.isOpen()) {
                database.close();
                }
        }
        return staff;
    }

    public int getSalary(int position){
        Staff staff = null;
        String[] columns = new String[]{"Salary"};
        String selection = "position=?";
        String[] selectionArgs = new String[]{String.valueOf(position)};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;

        SQLiteDatabase database = null;
        try {
            database = sqLiteOpenHelper.getWritableDatabase();
            //Cursor cursor = database.query("Staff", columns, selection, selectionArgs, groupBy, having, orderBy, limit);
            Cursor cursor = database.rawQuery("SELECT SUM("+ columns +") FROM Staff",null);
            //"SELECT SUM(" + DbHelper.CART_TOTAL + ") as Total FROM " + DbHelper.CART_TABLE, null);
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getInt(0);
            }

        } catch(Exception ex){
            Log.d("Database", "Exception:" + ex.getMessage());
        } finally{
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
        return 0;
    }

    public String getPerson(int code){

        Staff staff = null;
        String[] columns = new String[]{"name" , "family"};
        String selection = "code=?";
        String[] selectionArgs = new String[]{String.valueOf(code)};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;

        String FullName = null;

        SQLiteDatabase database = null;
        String staffName = null;
        String staffFamily = null ;

        try {
            database = sqLiteOpenHelper.getWritableDatabase();
            Cursor cursor = database.query("Staff", columns, selection, selectionArgs, groupBy, having, orderBy, limit);
            //Cursor cursor = database.rawQuery("SELECT SUM("+ columns +") FROM Staff",null);
            //"SELECT SUM(" + DbHelper.CART_TOTAL + ") as Total FROM " + DbHelper.CART_TABLE, null);
            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = 0;
                int nameIndex = 1;
                int familyIndex = 2;

                long staffId = cursor.getLong(idIndex);
                staffName = cursor.getString(nameIndex);
                staffFamily = cursor.getString(familyIndex);
            }

        } catch(Exception ex){
            Log.d("Database", "Exception:" + ex.getMessage());
        } finally{
            if (database != null && database.isOpen()) {
                database.close();
            }
        }

        return staffName + " " + staffFamily;
    }

    public String getTop(int top){

        String FullInfo = null;
        String[] columns = new String[]{"name" , "family" , "salary"};
        String selection = "position=?";
        String[] selectionArgs = new String[]{String.valueOf(top)};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;

        String FullName = null;

        SQLiteDatabase database = null;
        String staffName = null;
        String staffFamily = null ;
        String staffSalary = null;


        try {
            database = sqLiteOpenHelper.getWritableDatabase();
            Cursor cursor = database.query("Staff", columns, selection, selectionArgs, groupBy, having, orderBy, limit);
            //Cursor cursor = database.rawQuery("SELECT SUM("+ columns +") FROM Staff",null);
            //"SELECT SUM(" + DbHelper.CART_TOTAL + ") as Total FROM " + DbHelper.CART_TABLE, null);
            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = 0;
                int nameIndex = 1;
                int familyIndex = 2;
                int salaryIndex = 3;

                long staffId = cursor.getLong(idIndex);
                staffName = cursor.getString(nameIndex);
                staffFamily = cursor.getString(familyIndex);
                staffSalary = cursor.getString(salaryIndex);
            }

        } catch(Exception ex){
            Log.d("Database", "Exception:" + ex.getMessage());
        } finally{
            if (database != null && database.isOpen()) {
                database.close();
            }
        }

        return staffName + " " + staffFamily + " " + staffSalary;

    }
}