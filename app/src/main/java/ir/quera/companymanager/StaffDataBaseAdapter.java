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

                String sql = "create table Staff (ID integer, FirstName text, LastName text, " +
                        "Salary int, Hour int, Position text)";
                db.execSQL(sql);
                Log.d("Database", sql);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            }
        };
    }

    public long saveStaff(Staff staff) {
        if (staff.getId() == null || staff.getS_name() == null ||
                staff.getS_Family() == null || staff.getS_hour() == 0 || staff.getS_hour() == 0){
            return 0;
        }
        String name = staff.getS_name();
        String family = staff.getS_Family();
        int salary = staff.getS_per_hour();
        int hour = staff.getS_hour();
        String position = staff.getS_position();
        Long id = Long.parseLong(staff.getId());

        try {
            ContentValues values = new ContentValues();
            values.put("ID",id);
            values.put("FirstName", name);
            values.put("LastName", family);
            values.put("Salary", salary);
            values.put("Hour", hour);
            values.put("Position", position);

            database = sqLiteOpenHelper.getWritableDatabase();
            id = database.insert("Staff", null, values);
            Log.d("saveStaff ","id = " + id);

        } catch (Exception ex) {
            Log.d("saveStaffDatabase", "Exception:" + ex.getMessage());
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
            Log.d("deleteDataBase", "Exeption" + ex.getMessage());
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
        String[] whereArgs = new String[]{staff.getId()};

        database = null;

        try {
            ContentValues values = new ContentValues();
            values.put("ID",staff.getId());
            values.put("FirstName", staff.getS_name());
            values.put("LastName", staff.getS_Family());
            values.put("Salary", staff.getS_per_hour());
            values.put("Hour", staff.getS_hour());
            values.put("Position", staff.getS_position());

            database = sqLiteOpenHelper.getWritableDatabase();
            noOfUpdatingRecords = database.update("Staff", values, whereCluse, whereArgs);
            Log.d("updateStaff ","noOfUpdatingRecord "+ noOfUpdatingRecords);
        } catch (Exception ex) {
            Log.d("updateStaffDataBase", "Exeption " + ex.getMessage());
        } finally {
            if (database != null && database.isOpen()) {
                database.close();
            }
        }

        return noOfUpdatingRecords;

    }

    public Staff readStaff(String id) {

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
            database = sqLiteOpenHelper.getReadableDatabase();
            Cursor cursor = database.query("Staff", columns, selection, selectionArgs, groupBy, having, orderBy, limit);
            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = 0;
                int nameIndex = 1;
                int familyIndex = 2;

                String staffId = cursor.getString(idIndex);
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
        //Log.d("ReadStaff ", "Return" + staff.getId().toString());
        return staff;

    }

    public String getSalary(int position){

        String s_Position = "";

        switch(position){
            case 0:
                s_Position = "all";
                break;
            case 1:
                s_Position = "manager";
                break;
            case 2:
                s_Position = "developer";
                break;
            case 3:
                s_Position = "employee";
                break;
            default:
                s_Position = "all";
        }
        Log.d("Position","IS " + s_Position);

        Staff staff = null;
        String column1 ="Salary";
        String column2 = "Hour";
        String[] columns = new String[]{column1 + "*" +column2};
        String selection = "Position=?";
        String[] selectionArgs = new String[]{s_Position};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;

        SQLiteDatabase database = null;
        try {
            database = sqLiteOpenHelper.getWritableDatabase();
            Cursor cursor;
            if(s_Position == "all")
                cursor = database.rawQuery("select sum(Hour*Salary) From Staff",null);
            else
                cursor = database.rawQuery("select sum(Hour*Salary) From Staff Where Position=?",selectionArgs);
            if (cursor != null && cursor.moveToFirst()) {
                Log.d("getSalary", "cursor:" + cursor.getInt(0));

                int idIndex = 0;

                String staffId = cursor.getString(idIndex);

                staff = new Staff();
                staff.setId(staffId);

            }

        } catch(Exception ex){
            Log.d("Database", "Exception:" + ex.getMessage());
        } finally{
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
        Log.d("getSalary", "Return is :" + staff.getId());

        return staff.getId();
    }

    public String getPerson(String id){

        String[] columns = new String[]{"FirstName","LastName"};
        String selection = "ID=?";
        Log.d("ID","IS:"+id);
        String[] selectionArgs = new String[]{id};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;

        SQLiteDatabase database = null;
        String staffName = "";
        String staffFamily = "";

        try {
            database = sqLiteOpenHelper.getWritableDatabase();

            Cursor cursor = database.query("Staff", columns, selection, selectionArgs, groupBy, having, orderBy, limit);

            if (cursor != null && cursor.moveToFirst()) {

                Log.d("Cursor ","FirstName IS "+cursor.getString(0));
                Log.d("Cursor ","LastName IS "+cursor.getString(1));

                int nameIndex = 0;
                int familyIndex = 1;

                staffName = cursor.getString(nameIndex);
                staffFamily = cursor.getString(familyIndex);

            }

        } catch(Exception ex){
            Log.d("getPersonDatabase", "Exception:" + ex.getMessage());
        } finally{
            if (database != null && database.isOpen()) {
                database.close();
            }
        }

        return staffName + " " + staffFamily;
    }

    public String getTop(int top){

        String s_top;

        switch(top){
            case 1:
                s_top = "manager";
                break;
            case 2:
                s_top = "developer";
                break;
            case 3:
                s_top = "employee";
                break;
            default:
                s_top = "";
        }

        String[] selectionArgs = new String[]{s_top};

        SQLiteDatabase database = null;
        String staffName = "";
        String staffFamily = "" ;
        String staffSalary = "";

        try {
            database = sqLiteOpenHelper.getWritableDatabase();
            Cursor cursor = database.rawQuery("select FirstName ,LastName ,Max(Salary * Hour) From Staff Where position =?",selectionArgs);
            if (cursor != null && cursor.moveToFirst()) {
                Log.d("getTopDatabase", "Cursor IS:" + cursor);

                int nameIndex = 0;
                int familyIndex = 1;
                int salaryIndex = 2;

                staffName = cursor.getString(nameIndex);
                staffFamily = cursor.getString(familyIndex);
                staffSalary = cursor.getString(salaryIndex);

                Log.d("getTopDatabase", "Cursor IS:" + staffName +" "+staffFamily + " "+staffSalary);

            }

        } catch(Exception ex){
            Log.d("getTopDatabase", "Exception:" + ex.getMessage());
        } finally{
            if (database != null && database.isOpen()) {
                database.close();
            }
        }

        return staffName +" "+ staffFamily +" "+ staffSalary;

    }
}