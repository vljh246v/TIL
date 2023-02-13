package me.whiteship.refactoring._06_mutable_data._23_change_reference_to_value;

public class TelephoneNumber {

    private String areaCode;

    private String number;

    public String areaCode() {
        return areaCode;
    }

    public void areaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String number() {
        return number;
    }

    public void number(String number) {
        this.number = number;
    }
}
