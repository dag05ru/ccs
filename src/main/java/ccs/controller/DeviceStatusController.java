package ccs.controller;

import ccs.exception.InternalServerException;
import ccs.exception.NotFoundException;
import ccs.model.DeviceStatus;
import ccs.ping.PingService;
import ccs.repository.DeviceStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;


@RestController
@RequestMapping("/")
public class DeviceStatusController {
    @Autowired
    private DeviceStatusRepository repository;

    @Autowired
    PingService pingService;

    @GetMapping
    public Iterable<DeviceStatus> list() {
        Iterable<DeviceStatus> listSt = repository.findAll();
        return listSt;
    }

    @GetMapping("{id}")
    public DeviceStatus getOne(@PathVariable String id) {
        DeviceStatus ds = repository.findById(UUID.fromString(id));
        if(ds == null) throw new NotFoundException();
        return ds;
    }

    @PostMapping
    public DeviceStatus add(@RequestBody DeviceStatus ds) {
        DeviceStatus fds = repository.findById(ds.getId());
        if(fds != null) throw new InternalServerException();
        ds.setStatus(false);
        ds.setUpdatedAt(LocalDateTime.now());
        repository.save(ds);
        return ds;
    }


    @PutMapping("{id}")
    public DeviceStatus update(@PathVariable String id, @RequestBody DeviceStatus ds) {
        DeviceStatus rds = repository.findById(UUID.fromString(id));
        if(rds == null) throw new NotFoundException();
        ds.setId(rds.getId());
        ds.setUpdatedAt(LocalDateTime.now());
        ds.setStatus(false);
        repository.save(ds);
        return ds;
    }

    @DeleteMapping("{id}")
    public DeviceStatus delete(@PathVariable String id) {
        DeviceStatus ds = repository.findById(UUID.fromString(id));
        if(ds == null) throw new NotFoundException();
        repository.delete(ds);
        return ds;
    }

}
