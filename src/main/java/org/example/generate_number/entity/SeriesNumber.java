package org.example.generate_number.entity;

public class SeriesNumber {
    private int id;
    private String org_code;
    private String year;
    private int counter;

    public SeriesNumber(int id, String orgCode, String year, int counter) {
        this.id = id;
        this.org_code = orgCode;
        this.year = year;
        this.counter = counter;
    }


    public SeriesNumber(String orgCode, String year) {
        this.org_code = orgCode;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrg_code() {
        return org_code;
    }

    public void setOrg_code(String org_code) {
        this.org_code = org_code;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
