package server.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "process", schema = "portfolio", catalog = "")
public class ProcessEntity {
    private String id;
    private Integer userId;
    private String startDate;
    private String endDate;
    private Integer steps;
    private String processName;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "start_date")
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "end_date")
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "steps")
    public Integer getSteps() {
        return steps;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }

    @Basic
    @Column(name = "process_name")
    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessEntity that = (ProcessEntity) o;
        return id == that.id &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(steps, that.steps) &&
                Objects.equals(processName, that.processName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, startDate, endDate, steps, processName);
    }
}
