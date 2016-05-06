package guava;

import javax.swing.event.ChangeEvent;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * @author tracy
 *
 */
public class EventBusChangeRecorder {
  @Subscribe
  public void recordCustomerChange(ChangeEvent e) {
    System.out.println(((Subevent)e.getSource()).getName());
  }

  public static void main(String[] args) {
    EventBus eventBus = new EventBus();
    eventBus.register(new EventBusChangeRecorder());
    Subevent eSubevent = new Subevent();
    eSubevent.setName("Tracy");
    ChangeEvent event = new ChangeEvent(eSubevent);
    eventBus.post(event);
  }
}

class Subevent {
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
