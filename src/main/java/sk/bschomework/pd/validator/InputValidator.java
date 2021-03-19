package sk.bschomework.pd.validator;

import sk.bschomework.pd.error.CustomException;

import java.util.Arrays;
import java.util.regex.Pattern;

public class InputValidator {

    private static final String WEIGHT_PATTERN = "^([0-9]*)(\\.{0,1}[0-9]{1,3}){0,1}";
    private static final String FEE_PATTERN = "^([0-9]*)(\\.[0-9]{2})";
    private static final String POSTALCODE_PATTERN = "^[0-9]{5}";

    public static void validatePackage(String[] input) throws CustomException {
        try {
            String weight = input[0];
            String postalCode = input[1];

            if (Double.parseDouble(weight)<=0)
                throw new CustomException("Please insert weight higher than 0.000 for package input " + Arrays.toString(input));
            else if (!Pattern.matches(WEIGHT_PATTERN, weight))
                throw new CustomException("Please insert correct weight according to the rules for package input " + Arrays.toString(input));
            else if (!Pattern.matches(POSTALCODE_PATTERN, postalCode))
                throw new CustomException("Please insert correct postal code according to the rules for package input " + Arrays.toString(input));
        } catch (Exception e) {
            if (e instanceof NumberFormatException)
                throw new CustomException("Please insert correct number format for package input " + Arrays.toString(input));
            else if (e instanceof IndexOutOfBoundsException)
                throw new CustomException("Missing manadatory information about package " + Arrays.toString(input));
            else if (e instanceof CustomException)
                throw new CustomException(((CustomException) e).message);
        }
    }

    public static void validateFee(String[] input) throws CustomException {
        try {
            String weight = input[0];
            String fee = input[1];

            if (Double.parseDouble(weight)<=0)
                throw new CustomException("Please insert weight higher than 0.000 for fee input " + Arrays.toString(input));
            else if (Double.parseDouble(fee)<0)
                throw new CustomException("Please insert fee as positive number for fee input " + Arrays.toString(input));
            else if (!Pattern.matches(WEIGHT_PATTERN, weight))
                throw new CustomException("Please insert correct weight according to the rules for fee input " + Arrays.toString(input));
            else if (!Pattern.matches(FEE_PATTERN, fee))
                throw new CustomException("Please insert correct postal code according to the rules for fee input " + Arrays.toString(input));
        } catch (Exception e) {
            if (e instanceof NumberFormatException)
                throw new CustomException("Please insert correct number format for fee input " + Arrays.toString(input));
            else if (e instanceof IndexOutOfBoundsException)
                throw new CustomException("Missing manadatory information about package for fee input " + Arrays.toString(input));
            else if (e instanceof CustomException)
                throw new CustomException(((CustomException) e).message);
        }
    }
}
