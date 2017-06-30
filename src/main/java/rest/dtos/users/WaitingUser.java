package rest.dtos.users;

import org.joda.time.Duration;

import rest.dtos.generics.DurationDTO;

public class WaitingUser {

  public WaitingUser(Integer uid, Integer plid, Duration d, Integer rid) {
    userId = uid;
    productlistId = plid;
    duration = new DurationDTO(d);
    registerId = rid;
  }
  public Integer userId;
  public Integer productlistId;
  public Integer registerId;
  public DurationDTO duration;
  
}
