package ccs.ping;

import ccs.model.DeviceStatus;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.concurrent.Callable;


public class Ping implements Callable<DeviceStatus> {
    private InetAddress inetAddress;
    private int waitTimeout;
    private DeviceStatus ds;

    public Ping(DeviceStatus ds, int waitTimeout){
        this.waitTimeout = waitTimeout;
        this.ds = ds;
        try {
            inetAddress = InetAddress.getByName(ds.getIp());
        } catch (UnknownHostException e){
            e.printStackTrace();
        }
    }

    @Override
    public DeviceStatus call() {
        try {
            ds.setUpdatedAt(LocalDateTime.now());
            ds.setStatus(inetAddress.isReachable(waitTimeout));
        }  catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            return ds;
        }
    }

}
