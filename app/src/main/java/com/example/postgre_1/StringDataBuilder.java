package com.example.postgre_1;

public class StringDataBuilder {
    FloatToString floatToString = new FloatToString((float) 0);

    public String defString = "insert into newdata\n" +
            "(%s, data1, data2, data3,\"date\")\n" +
            "values\n" +
            "('volodar',%s,%s,%s,%s)\n" +
            ";";

    public StringDataBuilder(String defString) {
        this.defString = defString;
    }

    public StringDataBuilder() {
        //defString = "";
    }

    public String AddData(String nameColumn, float[] arr) {
        defString = String.format(defString, nameColumn,
                floatToString.toStringFloat(arr[0]),
                floatToString.toStringFloat(arr[1]),
                floatToString.toStringFloat(arr[2]),
                System.currentTimeMillis());
        return defString;
    }

}
