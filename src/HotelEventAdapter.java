import ExterneLibrary.HotelEvent;
import ExterneLibrary.HotelEventListener;
import ExterneLibrary.HotelEventManager;


public class HotelEventAdapter implements HotelEventListener {
    /**
     * to create methods for starting and stopping events
     */
    private HotelEventManager manager = new HotelEventManager();
    /**
     * sends events to method eventHappend in HotelGeneral when event fires
     */
    private HotelGeneral hotel;

    /**
     * Constuctor for assigning HotelGeneral and register Listeners
     *
     * @param hotel
     */
    HotelEventAdapter(HotelGeneral hotel) {
        this.hotel = hotel;
        register();
    }

    /**
     * starts thread and events
     */
    void run() {

        manager.start();
    }

    /**
     * stops thread and events
     */
    void stop() {
        manager.stop();
        manager.pause();
    }

    /**
     * registers Listeners from manager to HotelEventAdapter
     */
    private void register() {
        manager.register(this);

    }

    /**
     * Passes events to HotelGeneral
     *
     * @param event
     */
    @Override
    public void Notify(HotelEvent event) {
        hotel.eventHappend(event);

    }

}
