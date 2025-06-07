package entity;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "result")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double x;

    @Column(nullable = false)
    private double y;

    @Column(nullable = false)
    private double r;

    @Column(nullable = false)
    private boolean inside;

    @Column(nullable = false)
    private String currentTime;

    @Column(nullable = false)
    private double execTime;

    public Result() {
    }

    public Result(double x, double y, double r, boolean in, String currentTime, double execTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.inside = in;
        this.currentTime = currentTime;
        this.execTime = execTime;
    }

}
