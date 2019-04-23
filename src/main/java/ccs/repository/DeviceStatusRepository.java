package ccs.repository;

import ccs.model.DeviceStatus;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;


public interface DeviceStatusRepository extends CrudRepository<DeviceStatus, Integer> {

    @Cacheable(value = "deviceStatus", sync = true)
    Iterable<DeviceStatus> findAll();

    @Cacheable(value = "deviceStatus" , sync = true)
    DeviceStatus findById(UUID id);

    @CacheEvict(value = "deviceStatus", allEntries = true)
    void delete(DeviceStatus ds);

    @CacheEvict(value = "deviceStatus", allEntries = true)
    DeviceStatus save(DeviceStatus ds);
}
