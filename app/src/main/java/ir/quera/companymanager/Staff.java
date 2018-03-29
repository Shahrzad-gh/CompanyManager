package ir.quera.companymanager;

/**
 * Created by Shahrzad on 26/03/2018.
 */

public class Staff {

    private long id ;
    private String S_name;
    private String S_Family;
    private int S_per_hour;
    private int S_hour;
    private int S_position;

    public Staff(){

    }

    public Staff(long id, String s_name, String s_Family, int s_per_hour, int s_hour, int s_position) {
        this.id = id;
        S_name = s_name;
        S_Family = s_Family;
        S_per_hour = s_per_hour;
        S_hour = s_hour;
        S_position = s_position;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getS_name() {
        return S_name;
    }

    public void setS_name(String s_name) {
        S_name = s_name;
    }

    public String getS_Family() {
        return S_Family;
    }

    public void setS_Family(String s_Family) {
        S_Family = s_Family;
    }

    public int getS_per_hour() {
        return S_per_hour;
    }

    public void setS_per_hour(int s_per_hour) {
        S_per_hour = s_per_hour;
    }

    public int getS_hour() {
        return S_hour;
    }

    public void setS_hour(int s_hour) {
        S_hour = s_hour;
    }

    public int getS_position() {
        return S_position;
    }

    public void setS_position(int s_position) {
        S_position = s_position;
    }
}
