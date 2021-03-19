package sk.bschomework.pd.service;

import org.apache.commons.cli.CommandLine;
import sk.bschomework.pd.error.CustomException;
import sk.bschomework.pd.model.Fee;
import sk.bschomework.pd.validator.InputValidator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static sk.bschomework.pd.main.Main.ARGUMENT_FEES_INFORMATION_FILE;

public class FeeService {

    public static void processFeeFile(List<Fee> fees, CommandLine cmd) {
        String line;
        String filePath = cmd.getOptionValue(ARGUMENT_FEES_INFORMATION_FILE);
        BufferedReader bufferedReader = null;
        if (filePath != null && !filePath.isBlank()) {
            System.out.println("Loading " + filePath);
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(
                        new FileInputStream(filePath), StandardCharsets.UTF_8));
                while ((line = bufferedReader.readLine()) != null) {
                    processFeeLine(line, fees);
                }
                fees = fees.stream().sorted(Comparator.comparingDouble(Fee::getWeight).reversed()).collect(Collectors.toList());
                System.out.println("Fees loaded");
            } catch (Exception e) {
                if (e instanceof FileNotFoundException)
                    System.out.println(e.getMessage());
                if (e instanceof CustomException) {
                    System.out.println(e.getMessage());
                    fees.clear();
                }
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        System.out.println("Couldn't close the reader");
                    }
                }
            }
        }
    }

    private static void processFeeLine(String line, List<Fee> fees) throws CustomException {
        if (!line.isBlank()) {
            try {
                String[] input = line.split(" ");
                InputValidator.validateFee(input);
                Fee fee = new Fee(Double.parseDouble(input[0]), Double.parseDouble(input[1]));
                fees.add(fee);
            } catch (CustomException e) {
                System.out.println(e.message);
                throw new CustomException("If you need fees please fix file and rerun program with arguments again");
            }
        }
    }
}
