package br.com.neto.adauto.commom;

import br.com.neto.adauto.model.Package;
import br.com.neto.adauto.model.Router;
import org.apache.log4j.Logger;

import java.net.DatagramPacket;


public class RouterProcessingThread extends Thread {

    private static final Logger logger = Logger.getLogger(RouterProcessingThread.class);

    private Router router;

    private DatagramPacket receivePacket;

    RouterProcessingThread(Router router, DatagramPacket receivePacket) {
        this.router = router;
        this.receivePacket = receivePacket;
    }

    public void run() {
        try {
            String sentence = new String(receivePacket.getData(), "UTF-8");
            Package packageReceive = new Package(sentence);
            router.processReceipt(packageReceive);
        } catch (Exception e) {
            logger.error(e);
        }
    }

}
