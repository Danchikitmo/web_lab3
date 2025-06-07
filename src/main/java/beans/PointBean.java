package beans;

import entity.Result;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;
import util.AreaChecker;


import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

@Named("pointBean")
@SessionScoped
@Data
public class PointBean implements Serializable {
    private double x = 0.0;
    private HashMap<Double, Boolean> checkboxesX = new HashMap<>();
    private double y = 0.0;
    private double r = 3.0;

    private static final List<Double> X_VALUES = List.of(-4.0, -3.0, -2.0, -1.0, 0.0, 1.0, 2.0, 3.0, 4.0);

    @Inject
    private ResultBean resultBean;

    public PointBean() {
        X_VALUES.forEach(k -> checkboxesX.put(k, false));
        checkboxesX.put(0.0, true);
    }

    public List<Double> getXValues() {
        return X_VALUES;
    }

    public void checkPoint() {
        Instant start = Instant.now();
        boolean isInside = AreaChecker.checkInside(x, y, r);
        Instant end = Instant.now();

        double execTime = Duration.between(start, end).toNanos() / 1_000_000.0;
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Result result = new Result(x, y, r, isInside, currentTime, execTime);
        resultBean.addResult(result);
    }

    public void onCheckboxChange(double newX) {
        checkboxesX.replaceAll((key, value) -> false);
        checkboxesX.put(newX, true);
    }
}