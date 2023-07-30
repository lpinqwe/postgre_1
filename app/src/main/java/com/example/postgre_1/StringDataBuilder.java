package com.example.postgre_1;

public class StringDataBuilder {
public String defString="insert into newdata\n" +
                        "(%s, data1, data2, data3,\"date\")\n" +
                        "values\n" +
                        "('volodar',%f,%f,%f,%s)\n" +
                        ";";

    public StringDataBuilder(String defString) {
        this.defString = defString;
    }
    public StringDataBuilder() {
        //defString = "";
    }
    public String AddData(String nameColumn, float[] arr){
        defString=String.format(defString, nameColumn,arr[0],arr[1],arr[2],System.currentTimeMillis());
        return defString;
    }

}
