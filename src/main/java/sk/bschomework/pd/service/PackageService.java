package sk.bschomework.pd.service;

import org.apache.commons.cli.CommandLine;
import sk.bschomework.pd.error.CustomException;
import sk.bschomework.pd.model.Fee;
import sk.bschomework.pd.model.Package;
import sk.bschomework.pd.validator.InputValidator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static sk.bschomework.pd.main.Main.ARGUMENT_PACKAGE_INFORMATION_FILE;
import static sk.bschomework.pd.main.Main.inputs;

public class PackageService {

    public static void processPackageFile(List<Fee> fees, CommandLine cmd) {
        String line;
        String filePath = cmd.getOptionValue(ARGUMENT_PACKAGE_INFORMATION_FILE);
        BufferedReader bufferedReader = null;
        if (filePath != null && !filePath.isBlank()) {
            System.out.println("Loading " + filePath);
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(
                        new FileInputStream(filePath), StandardCharsets.UTF_8));
                while ((line = bufferedReader.readLine()) != null) {
                    processPackageLine(line, fees);
                }
                System.out.println("Packages informations loaded");
            } catch (Exception e) {
                if (e instanceof FileNotFoundException)
                    System.out.println(e.getMessage());
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

    public static void processPackageLine(String line, List<Fee> fees) {
        if (!line.isBlank()) {
            try {
                String[] input = line.split(" ");
                InputValidator.validatePackage(input);
                Double weight = Double.parseDouble(input[0]);
                Package pckg = new Package(weight, input[1],
                        fees.isEmpty() ? null : findFee(weight, fees));
                inputs.add(pckg);
            } catch (CustomException e) {
                System.out.println(e.message);
            }
        }
    }

    private static Double findFee(Double weight, List<Fee> fees) {
        Optional<Fee> fee = fees.stream().filter(x -> weight >= x.weight).findFirst();
        return fee.isPresent() ? fee.get().fee : 0.00;
    }
}
