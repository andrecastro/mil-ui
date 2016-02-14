package br.edu.ifce.ppd.testproject.view;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by andrecoelho on 2/13/16.
 */
public class MyTransferable implements Transferable {

    private JComponent jComponent;

    public static final DataFlavor componentFlavor = new DataFlavor(JComponent.class, "Component");

    public MyTransferable(JComponent jComponent) {
        this.jComponent = jComponent;
    }

    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] { componentFlavor };
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return Arrays.asList(getTransferDataFlavors()).contains(flavor);
    }

    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        return jComponent;
    }
}
