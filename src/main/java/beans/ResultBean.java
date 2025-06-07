package beans;

import db.ResultDAO;
import entity.Result;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Named("resultBean")
@SessionScoped
@Data
public class ResultBean implements Serializable {
    private LinkedList<Result> results = new LinkedList<>();

    @Inject
    private ResultDAO resultDAO;

    @PostConstruct
    public void init() {
        loadData();
    }

    public void addResult(Result result) {
        results.addFirst(result);
        resultDAO.save(result);
    }

    public void loadData() {
        List<Result> resultsList = resultDAO.loadAllResults();

        results = new LinkedList<>();
        for (int i = resultsList.size() - 1; i >= 0; i--) {
            results.add(resultsList.get(i));
        }
    }
}