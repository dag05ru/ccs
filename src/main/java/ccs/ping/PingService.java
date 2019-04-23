package ccs.ping;

import ccs.model.DeviceStatus;
import ccs.repository.DeviceStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;


@Component
public class PingService implements CommandLineRunner {
    @Autowired
    DeviceStatusRepository repository;

    @Value("${check_connection_timeout}")
    private int waitTimeout;

    @Value("${check_connection_poll_timeout}")
    private int sendTimeout;

    private ExecutorService executor = Executors.newCachedThreadPool();

    Callable<DeviceStatus> callable(DeviceStatus ds, int waitTimeout) {
        return () -> {
            try {
                InetAddress inetAddress = InetAddress.getByName(ds.getIp());
                ds.setUpdatedAt(LocalDateTime.now());
                ds.setStatus(inetAddress.isReachable(waitTimeout));
            }  catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                return ds;
            }
        };
    }

    void updateDS(DeviceStatus ds) {
        repository.save(ds);
    }

    @Override
    public void run(String... args) {
            ArrayList<Callable<DeviceStatus>> pings = new ArrayList<>();
            while (true) {
                try {
                    Iterable<DeviceStatus> ids = repository.findAll();
                    for (DeviceStatus ds : ids) {
                        long now = System.currentTimeMillis();
                        long updatedAt = Timestamp.valueOf(ds.getUpdatedAt()).getTime();
                        if (now - updatedAt > sendTimeout) {
                            pings.add(callable(ds, waitTimeout));
                        }
                    }
                    executor.invokeAll(pings)
                            .stream()
                            .map(future -> {
                                try {
                                    return future.get();
                                } catch (Exception e) {
                                    throw new IllegalStateException(e);
                                }
                            })
                            .forEach(this::updateDS);
                    pings.clear();
                    sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }

}
