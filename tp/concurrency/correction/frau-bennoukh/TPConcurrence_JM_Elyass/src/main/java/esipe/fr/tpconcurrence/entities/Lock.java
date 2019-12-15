package esipe.fr.tpconcurrence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;


@ApiModel(description = "un verrou")
@org.springframework.data.mongodb.core.mapping.Document("Lock")
public class Lock   {

  @Id
  @JsonIgnore
  private String id;

  @JsonIgnore
  private String lockId = null;

  private String owner = null;

  private LocalDateTime created = null;

  public Lock(String lockId, String owner, LocalDateTime created) {
    this.lockId = lockId;
    this.owner = owner;
    this.created = created;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public void setCreated(LocalDateTime created) {
    this.created = created;
  }

  public String getLockId() {
    return lockId;
  }

  public void setLockId(String lockId) {
    this.lockId = lockId;
  }
}

