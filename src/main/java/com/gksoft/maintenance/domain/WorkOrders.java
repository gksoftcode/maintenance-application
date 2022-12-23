package com.gksoft.maintenance.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A WorkOrders.
 */
@Entity
@Table(name = "work_orders")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WorkOrders implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "budget")
    private Long budget;

    @Column(name = "item_serial")
    private String itemSerial;

    @Column(name = "is_waranty")
    private Boolean isWaranty;

    @Column(name = "notes")
    private String notes;

    @OneToOne
    @JoinColumn(unique = true)
    private Status status;

    @JsonIgnoreProperties(value = { "appintments" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Client client;

    @OneToOne
    @JoinColumn(unique = true)
    private ItemModels itemModels;

    @OneToOne
    @JoinColumn(unique = true)
    private ItemBrand itemBrand;

    @OneToMany(mappedBy = "workOrders")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "jobs", "manager", "department", "workOrders" }, allowSetters = true)
    private Set<Employee> assignedStaffs = new HashSet<>();

    @OneToMany(mappedBy = "workOrders")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "status", "action", "attachments", "workOrders" }, allowSetters = true)
    private Set<WorkOrdersHistory> workOrdersHistories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public WorkOrders id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public WorkOrders title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public WorkOrders description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public WorkOrders startDate(Instant startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public WorkOrders endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Long getBudget() {
        return this.budget;
    }

    public WorkOrders budget(Long budget) {
        this.setBudget(budget);
        return this;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }

    public String getItemSerial() {
        return this.itemSerial;
    }

    public WorkOrders itemSerial(String itemSerial) {
        this.setItemSerial(itemSerial);
        return this;
    }

    public void setItemSerial(String itemSerial) {
        this.itemSerial = itemSerial;
    }

    public Boolean getIsWaranty() {
        return this.isWaranty;
    }

    public WorkOrders isWaranty(Boolean isWaranty) {
        this.setIsWaranty(isWaranty);
        return this;
    }

    public void setIsWaranty(Boolean isWaranty) {
        this.isWaranty = isWaranty;
    }

    public String getNotes() {
        return this.notes;
    }

    public WorkOrders notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public WorkOrders status(Status status) {
        this.setStatus(status);
        return this;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public WorkOrders client(Client client) {
        this.setClient(client);
        return this;
    }

    public ItemModels getItemModels() {
        return this.itemModels;
    }

    public void setItemModels(ItemModels itemModels) {
        this.itemModels = itemModels;
    }

    public WorkOrders itemModels(ItemModels itemModels) {
        this.setItemModels(itemModels);
        return this;
    }

    public ItemBrand getItemBrand() {
        return this.itemBrand;
    }

    public void setItemBrand(ItemBrand itemBrand) {
        this.itemBrand = itemBrand;
    }

    public WorkOrders itemBrand(ItemBrand itemBrand) {
        this.setItemBrand(itemBrand);
        return this;
    }

    public Set<Employee> getAssignedStaffs() {
        return this.assignedStaffs;
    }

    public void setAssignedStaffs(Set<Employee> employees) {
        if (this.assignedStaffs != null) {
            this.assignedStaffs.forEach(i -> i.setWorkOrders(null));
        }
        if (employees != null) {
            employees.forEach(i -> i.setWorkOrders(this));
        }
        this.assignedStaffs = employees;
    }

    public WorkOrders assignedStaffs(Set<Employee> employees) {
        this.setAssignedStaffs(employees);
        return this;
    }

    public WorkOrders addAssignedStaff(Employee employee) {
        this.assignedStaffs.add(employee);
        employee.setWorkOrders(this);
        return this;
    }

    public WorkOrders removeAssignedStaff(Employee employee) {
        this.assignedStaffs.remove(employee);
        employee.setWorkOrders(null);
        return this;
    }

    public Set<WorkOrdersHistory> getWorkOrdersHistories() {
        return this.workOrdersHistories;
    }

    public void setWorkOrdersHistories(Set<WorkOrdersHistory> workOrdersHistories) {
        if (this.workOrdersHistories != null) {
            this.workOrdersHistories.forEach(i -> i.setWorkOrders(null));
        }
        if (workOrdersHistories != null) {
            workOrdersHistories.forEach(i -> i.setWorkOrders(this));
        }
        this.workOrdersHistories = workOrdersHistories;
    }

    public WorkOrders workOrdersHistories(Set<WorkOrdersHistory> workOrdersHistories) {
        this.setWorkOrdersHistories(workOrdersHistories);
        return this;
    }

    public WorkOrders addWorkOrdersHistory(WorkOrdersHistory workOrdersHistory) {
        this.workOrdersHistories.add(workOrdersHistory);
        workOrdersHistory.setWorkOrders(this);
        return this;
    }

    public WorkOrders removeWorkOrdersHistory(WorkOrdersHistory workOrdersHistory) {
        this.workOrdersHistories.remove(workOrdersHistory);
        workOrdersHistory.setWorkOrders(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkOrders)) {
            return false;
        }
        return id != null && id.equals(((WorkOrders) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkOrders{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", budget=" + getBudget() +
            ", itemSerial='" + getItemSerial() + "'" +
            ", isWaranty='" + getIsWaranty() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
