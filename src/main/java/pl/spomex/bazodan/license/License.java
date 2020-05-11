package pl.spomex.bazodan.license;

import com.fasterxml.jackson.annotation.JsonFormat;
import pl.spomex.bazodan.truck.Truck;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "license")
public class License {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "issue_date")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Europe/Warsaw")
    private LocalDate issueDate;

    @Column(name = "exp_date")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Europe/Warsaw")
    private LocalDate expDate;

    @OneToOne
    @JoinColumn(name = "truck_id", referencedColumnName = "id")
    private Truck truck;

    public static License fromId(Integer id) {
        License license = new License();
        license.setId(id);
        return license;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issue_date) {
        this.issueDate = issue_date;
    }

    public LocalDate getExpDate() {
        return expDate;
    }

    public void setExpDate(LocalDate exp_date) {
        this.expDate = exp_date;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }
}
