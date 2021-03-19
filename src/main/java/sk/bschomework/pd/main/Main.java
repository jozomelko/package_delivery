package sk.bschomework.pd.main;

import org.apache.commons.cli.*;
import sk.bschomework.pd.service.FeeService;
import sk.bschomework.pd.service.PackageService;
import sk.bschomework.pd.model.Fee;
import sk.bschomework.pd.model.Package;
import sk.bschomework.pd.timer.SummaryTimer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static volatile List<Package> inputs = new ArrayList<>();
    public static final String ARGUMENT_PACKAGE_INFORMATION_FILE = "packages";
    public static final String ARGUMENT_FEES_INFORMATION_FILE = "fees";

    public static void main(String[] args) throws IOException {
        Options options = new Options();
        Option packagesArgument = new Option("p", ARGUMENT_PACKAGE_INFORMATION_FILE, true, "File with packages informations");
        Option feesArgument = new Option("f", ARGUMENT_FEES_INFORMATION_FILE, true, "File with fees informations");
        options.addOption(packagesArgument);
        options.addOption(feesArgument);
        CommandLineParser cmdParser = new DefaultParser();
        HelpFormatter helpFormatter = new HelpFormatter();
        CommandLine cmd = null;
        try {
            cmd = cmdParser.parse(options, args);
        } catch (ParseException e) {
            System.out.println("Argument exception");
            helpFormatter.printHelp("./gradlew run --args='[-f <arg>] [-p <arg>]'", options);
            System.exit(0);
        }
        List<Fee> fees = new ArrayList<Fee>();
        if (cmd != null) {
            FeeService.processFeeFile(fees, cmd);
            PackageService.processPackageFile(fees, cmd);
            runTimer(fees);
        }
    }

    private static void runTimer(List<Fee> fees) throws IOException {
        String line;
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        Timer timer = new Timer();

        try {
            timer.schedule(new SummaryTimer(inputs, !fees.isEmpty()), 0, 60000);
            while (!(line = bufferedReader.readLine()).equals("quit")) {
                PackageService.processPackageLine(line, fees);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            timer.cancel();
            bufferedReader.close();
        }
    }
}
