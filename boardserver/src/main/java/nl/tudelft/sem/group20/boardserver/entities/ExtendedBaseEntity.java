package nl.tudelft.sem.group20.boardserver.entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import nl.tudelft.sem.group20.boardserver.embeddable.TimestampTracker;

/**
 * Entities in this server might inherit from this class.
 * This class reduces lack of cohesion.
 */
@MappedSuperclass
public class ExtendedBaseEntity extends BaseEntity {
    @Column
    private String username;

    @Embedded
    TimestampTracker timestampTracker;

    public ExtendedBaseEntity() {
        this.timestampTracker = new TimestampTracker();
    }

    public ExtendedBaseEntity(long id, String name, String username) {
        super(id, name);
        this.username = username;
        this.timestampTracker = new TimestampTracker();
    }

    public ExtendedBaseEntity(String name, String username) {
        super(name);
        this.username = username;
        this.timestampTracker = new TimestampTracker();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public TimestampTracker getTimestampTracker() {
        return timestampTracker;
    }

    public void setTimestampTracker(TimestampTracker timestampTracker) {
        this.timestampTracker = timestampTracker;
    }

    /**
     * Checks the creation time of an entity.
     * If it is a new entity - set created and
     * set edited to current time.
     *
     * @param baseEntity - entity that needs to be checked.
     */
    public static void checkCreationTime(ExtendedBaseEntity baseEntity) {
        if (baseEntity.timestampTracker.getCreated() == null) {
            baseEntity.timestampTracker.setCreated(LocalDateTime.now());
            baseEntity.timestampTracker.setEdited(LocalDateTime.now());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ExtendedBaseEntity) {
            ExtendedBaseEntity that = (ExtendedBaseEntity) o;
            return super.equals(o)
                    && this.username.equals(that.username)
                    && this.timestampTracker.equals(that.timestampTracker);
        }

        return false;
    }

    @Override
    public String toString() {
        return "BaseEntity{"
                + "id=" + this.getId()
                + ", name='" + this.getName() + '\''
                + ", username='" + username + '\''
                + ", " + this.timestampTracker.toString()
                + '}';
    }
}
