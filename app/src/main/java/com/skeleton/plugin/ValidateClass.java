package com.skeleton.plugin;

import android.app.Activity;
import android.util.Patterns;
import android.widget.EditText;

import com.skeleton.R;


/**
 * created by Kashish
 */
public class ValidateClass {

    private Activity context;

    public ValidateClass(Activity context) {

        this.context = context;
    }

    /**
     * Method to validate field is empty or not
     *
     * @param et
     * @return
     */
    public boolean genericEmpty(EditText et) {
        return et.getText().toString().trim().isEmpty();
    }

    /**
     * Method to validate field is empty or not and display error
     * like on state,city and any other whose specific validations are not required
     *
     * @param et
     * @param msg
     * @return boolean
     */
    public boolean genericEmpty(MaterialEditText et, String msg) {
        if (genericEmpty(et)) {
            return setErrorAndRequestFoucs(et, msg);
        }
        return true;
    }

    /**
     * set Error on materialEditText and return false
     *
     * @param et
     * @param errorMessage
     * @return
     */
    public boolean setErrorAndRequestFoucs(MaterialEditText et, String errorMessage) {
        et.setSelection(et.getText().toString().length());
        et.setHovered(true);
        et.requestFocus();
        et.setError(errorMessage);
        return false;
    }

    /**
     * Password validation
     *
     * @param et
     * @return
     */
    public Boolean checkPassword(MaterialEditText et) {

        if (genericEmpty(et)) {
            return setErrorAndRequestFoucs(et, context.getString(R.string.PleaseEnterPassword));
        } else if (et.getText().toString().length() < 6) {
            return setErrorAndRequestFoucs(et, context.getString(R.string.PasswordContainAtleastSixChar));
        }
        return true;
    }

    /**
     * for password matching
     *
     * @param passEt
     * @param confirmPassEt
     * @return
     */
    public Boolean passwordMatch(MaterialEditText passEt, MaterialEditText confirmPassEt) {
        if (genericEmpty(confirmPassEt)) {
            return setErrorAndRequestFoucs(confirmPassEt, context.getString(R.string.PleaseEnterConfrimPassword));

        } else if (!passEt.getText().toString().equals(confirmPassEt.getText().toString())) {
            return setErrorAndRequestFoucs(confirmPassEt, context.getString(R.string.PasswordDoesntMatch));

        }
        return true;

    }

    /**
     * first name validation
     *
     * @param et
     * @return
     */
    public Boolean checkName(MaterialEditText et) {
        if (genericEmpty(et)) {
            return setErrorAndRequestFoucs(et, context.getString(R.string.PleaseEnterFirstName));
        } else if (et.getText().toString().trim().length() < 3) {
            return setErrorAndRequestFoucs(et, context.getString(R.string.NameThreeCharLong));
        } else if (!et.getText().toString().matches("^[\\p{L} .'-]+$")) {//It takes alphabets and spaces and dots...
            return setErrorAndRequestFoucs(et, context.getString(R.string.NameCannotContainSpecialCharacters));
        }
        return true;

    }

    /**
     * last name validation
     *
     * @param et
     * @return
     */
    public Boolean checkLastName(MaterialEditText et) {

        if (genericEmpty(et)) {
            return setErrorAndRequestFoucs(et, context.getString(R.string.PleaseEnterLastName));
        } else if (!et.getText().toString().trim().matches("\\p{L}+")) { //It takes alphabets only...
            return setErrorAndRequestFoucs(et, context.getString(R.string.LastNameCannotContainSpecialChar));
        }
        return true;

    }

    /**
     * email address validation
     * @param et
     * @return
     */
    public Boolean checkEmail(MaterialEditText et) {
        String email = et.getText().toString().trim();
        if (genericEmpty(et))
            return setErrorAndRequestFoucs(et, context.getString(R.string.PleaseEnterEmailId));

        if (!email.matches(Patterns.EMAIL_ADDRESS.toString()))
            return setErrorAndRequestFoucs(et, context.getString(R.string.PleaseEnterValidEmail));

        return true;

    }

    /**
     * phone number validation
     * @param et
     * @return
     */
    public Boolean checkPhoneNumber(MaterialEditText et) {

        try {

            if (genericEmpty(et)) {
                return setErrorAndRequestFoucs(et, context.getString(R.string.PleaseEnterPhoneNo));
            } else if (et.getText().toString().trim().charAt(0) == '0') {
                return setErrorAndRequestFoucs(et, context.getString(R.string.PhoneNoCannotStartFromZero));
            } else if (et.getText().toString().trim().length() > 15) {
                return setErrorAndRequestFoucs(et, context.getString(R.string.PhoneLengthAtmostfifteen));

            } else if (et.getText().toString().trim().length() < 6) {
                return setErrorAndRequestFoucs(et, context.getString(R.string.PhoneLengthAtleastSix));

            } else if (Long.valueOf(et.getText().toString().trim()) == 0) {

                return setErrorAndRequestFoucs(et, context.getString(R.string.PleaseEnterValidPhoneNo));
            }

            return true;
        } catch (NumberFormatException e) {
            return setErrorAndRequestFoucs(et, context.getString(R.string.PleaseEnterValidPhoneNo));
        }
    }

    /**
     * OTP validation
     * @param otp
     * @param et
     * @return
     */
    public Boolean checkOTP(String otp, MaterialEditText et) {

        if (otp.compareTo("") == 0) {
            return setErrorAndRequestFoucs(et, context.getString(R.string.PleaseEnterOtp));
        }
        if (otp.length() < 4) {
            return setErrorAndRequestFoucs(et, context.getString(R.string.PleaseEnterFourDigitOtp));
        }

        return true;
    }

}
