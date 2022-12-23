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
 * A WorkOrdersHistory.
 */
@Entity
@Table(name = "work_orders_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WorkOrdersHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "is_shared")
    private Boolean isShared;

    @Column(name = "action_date")
    private Instant actionDate;

    @Column(name = "note")
    private String note;

    @OneToOne
    @JoinColumn(unique = true)
    private Status status;

    @OneToOne
    @JoinColumn(unique = true)
    private Actions action;

    @OneToMany(mappedBy = "workOrdersHistory")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "workOrdersHistory" }, allowSetters = true)
    private Set<Attachments> attachments = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "status", "client", "itemModels", "itemBrand", "assignedStaffs", "workOrdersHistories" },
        allowSetters = true
    )
    private WorkOrders workOrders;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public WorkOrdersHistory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsShared() {
        return this.isShared;
    }

    public WorkOrdersHistory isShared(Boolean isShared) {
        this.setIsShared(isShared);
        return this;
    }

    public void setIsShared(Boolean isShared) {
        this.isShared = isShared;
    }

    public Instant getActionDate() {
        return this.actionDate;
    }

    public WorkOrdersHistory actionDate(Instant actionDate) {
        this.setActionDate(actionDate);
        return this;
    }

    public void setActionDate(Instant actionDate) {
        this.actionDate = actionDate;
    }

    public String getNote() {
        return this.note;
    }

    public WorkOrdersHistory note(String note) {
        this.setNote(note);
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public WorkOrdersHistory status(Status status) {
        this.setStatus(status);
        return this;
    }

    public Actions getAction() {
        return this.action;
    }

    public void setAction(Actions actions) {
        this.action = actions;
    }

    public WorkOrdersHistory action(Actions actions) {
        this.setAction(actions);
        return this;
    }

    public Set<Attachments> getAttachments() {
        return this.attachments;
    }

    public void setAttachments(Set<Attachments> attachments) {
        if (this.attachments != null) {
            this.attachments.forEach(i -> i.setWorkOrdersHistory(null));
        }
        if (attachments != null) {
            attachments.forEach(i -> i.setWorkOrdersHistory(this));
        }
        this.attachments = attachments;
    }

    public WorkOrdersHistory attachments(Set<Attachments> attachments) {
        this.setAttachments(attachments);
        return this;
    }

    public WorkOrdersHistory addAttachments(Attachments attachments) {
        this.attachments.add(attachments);
        attachments.setWorkOrdersHistory(this);
        return this;
    }

    public WorkOrdersHistory removeAttachments(Attachments attachments) {
        this.attachments.remove(attachments);
        attachments.setWorkOrdersHistory(null);
        return this;
    }

    public WorkOrders getWorkOrders() {
        return this.workOrders;
    }

    public void setWorkOrders(WorkOrders workOrders) {
        this.workOrders = workOrders;
    }

    public WorkOrdersHistory workOrders(WorkOrders workOrders) {
        this.setWorkOrders(workOrders);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkOrdersHistory)) {
            return false;
        }
        return id != null && id.equals(((WorkOrdersHistory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkOrdersHistory{" +
            "id=" + getId() +
            ", isShared='" + getIsShared() + "'" +
            ", actionDate='" + getActionDate() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }
}
