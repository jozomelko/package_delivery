package sk.bschomework.pd.timer;

import sk.bschomework.pd.model.Package;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class SummaryTimer extends TimerTask {

    private static final DecimalFormat WEIGHT_DECIMAL_FORMAT = new DecimalFormat("0.000");
    private static final DecimalFormat FEE_DECIMAL_FORMAT = new DecimalFormat("0.00");
    public List<Package> inputs;
    public Boolean hasFee;

    public SummaryTimer(List<Package> inputs, Boolean hasFee) {
        this.inputs = inputs;
        this.hasFee = hasFee;
    }

    @Override
    public void run() {
        List<Package> result = inputs.stream().collect(
                Collectors.groupingBy(Package::getPostalCode))
                .entrySet().stream()
                .map((x) -> {
                    Package p = new Package(0.000, x.getKey(), hasFee ? 0.00 : null);
                    x.getValue().forEach(pi -> {
                        p.weight += pi.weight;
                        if (pi.fee != null)
                            p.fee += pi.fee;
                    });
                    return p;
                }).collect(Collectors.toList())
                .stream().sorted(Comparator.comparingDouble(Package::getWeight).reversed()).collect(Collectors.toList());

        result.forEach(p ->
                System.out.println(p.postalCode + " " + WEIGHT_DECIMAL_FORMAT.format(p.weight)
                        + (hasFee ? " " + FEE_DECIMAL_FORMAT.format(p.fee) : "")));
    }
}
