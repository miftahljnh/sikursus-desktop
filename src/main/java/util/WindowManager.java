package util;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public final class WindowManager {
    private static final Map<Class<? extends JFrame>, JFrame> WINDOWS = new HashMap<>();

    private WindowManager() {}

    public static synchronized <T extends JFrame> void open(Class<T> type, Supplier<T> factory) {
        JFrame frame = WINDOWS.get(type);
        if (frame == null || !frame.isDisplayable()) {
            frame = factory.get();
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            JFrame trackedFrame = frame;
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    synchronized (WindowManager.class) {
                        WINDOWS.remove(type, trackedFrame);
                    }
                }
            });
            WINDOWS.put(type, frame);
        }
        if ((frame.getExtendedState() & Frame.ICONIFIED) == Frame.ICONIFIED) {
            frame.setExtendedState(Frame.NORMAL);
        }
        frame.setVisible(true);
        frame.toFront();
        frame.requestFocus();
    }

    public static synchronized void closeAll() {
        for (JFrame frame : new ArrayList<>(WINDOWS.values())) {
            frame.dispose();
        }
        WINDOWS.clear();
    }
}