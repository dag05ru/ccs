package ccs.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class DeviceStatus {
    @Id
    @Column(name = "device_id")
    @Type(type="pg-uuid")
    private UUID id;

    @Column
    @EqualsAndHashCode.Exclude
    private String ip;

    @Column
    @EqualsAndHashCode.Exclude
    private boolean status;

    @Column(name = "updated_at")
    @EqualsAndHashCode.Exclude
    private LocalDateTime updatedAt;

    public DeviceStatus() {
    }

    public DeviceStatus(UUID id, String ip, boolean status, LocalDateTime updatedAt) {
        this.id = id;
        this.ip = ip;
        this.status = status;
        this.updatedAt = updatedAt;
    }

}
