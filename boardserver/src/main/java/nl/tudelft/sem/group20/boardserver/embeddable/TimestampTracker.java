package nl.tudelft.sem.group20.boardserver.embeddable;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Embeddable
public class TimestampTracker {
    @CreationTimestamp
    @Column
    private LocalDateTime created;

    @UpdateTimestamp
    @Column
    private LocalDateTime edited;

    public TimestampTracker() {
        this.created = LocalDateTime.now();
        this.edited = this.created;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public boolean isEdited() {
        return !this.edited.isEqual(this.getCreated());
    }

    public LocalDateTime getEdited() {
        return edited;
    }

    public void setEdited(LocalDateTime edited) {
        this.edited = edited;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TimestampTracker)) {
            return false;
        }
        TimestampTracker that = (TimestampTracker) o;
        return created.equals(that.created)
                && edited.equals(that.edited);
    }

    @Override
    public int hashCode() {
        return Objects.hash(created, edited);
    }

    @Override
    public String toString() {
        return "created=" + created + ", edited=" + edited;
    }
}
