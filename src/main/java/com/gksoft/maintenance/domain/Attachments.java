package com.gksoft.maintenance.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Attachments.
 */
@Entity
@Table(name = "attachments")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Attachments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "attach_name")
    private String attachName;

    @Lob
    @Column(name = "attach")
    private byte[] attach;

    @Column(name = "attach_content_type")
    private String attachContentType;

    @ManyToOne
    @JsonIgnoreProperties(value = { "status", "action", "attachments", "workOrders" }, allowSetters = true)
    private WorkOrdersHistory workOrdersHistory;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Attachments id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttachName() {
        return this.attachName;
    }

    public Attachments attachName(String attachName) {
        this.setAttachName(attachName);
        return this;
    }

    public void setAttachName(String attachName) {
        this.attachName = attachName;
    }

    public byte[] getAttach() {
        return this.attach;
    }

    public Attachments attach(byte[] attach) {
        this.setAttach(attach);
        return this;
    }

    public void setAttach(byte[] attach) {
        this.attach = attach;
    }

    public String getAttachContentType() {
        return this.attachContentType;
    }

    public Attachments attachContentType(String attachContentType) {
        this.attachContentType = attachContentType;
        return this;
    }

    public void setAttachContentType(String attachContentType) {
        this.attachContentType = attachContentType;
    }

    public WorkOrdersHistory getWorkOrdersHistory() {
        return this.workOrdersHistory;
    }

    public void setWorkOrdersHistory(WorkOrdersHistory workOrdersHistory) {
        this.workOrdersHistory = workOrdersHistory;
    }

    public Attachments workOrdersHistory(WorkOrdersHistory workOrdersHistory) {
        this.setWorkOrdersHistory(workOrdersHistory);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attachments)) {
            return false;
        }
        return id != null && id.equals(((Attachments) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Attachments{" +
            "id=" + getId() +
            ", attachName='" + getAttachName() + "'" +
            ", attach='" + getAttach() + "'" +
            ", attachContentType='" + getAttachContentType() + "'" +
            "}";
    }
}
