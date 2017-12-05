package info.rajdhany.rajdhanyall.adapter;

/**
 * Created by TONMOYPC on 12/5/2017.
 */
public interface CallbackItemTouch {

    /**
     * Called when an item has been dragged
     * @param oldPosition start position
     * @param newPosition end position
     */
    void itemTouchOnMove(int oldPosition,int newPosition);
}
