package supplyClasses;

public class FloatToString {
    public FloatToString(float floatNumber) {
        toStringFloat(floatNumber);
    }

    public String toStringFloat(float floatNumber){
        String var = Float.toString(floatNumber);
        var= var.replace(',','.');
        return var;
    }
}
