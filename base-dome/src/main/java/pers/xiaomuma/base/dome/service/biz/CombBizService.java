package pers.xiaomuma.base.dome.service.biz;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pers.xiaomuma.base.dome.constant.DataSourceName;
import pers.xiaomuma.base.dome.domain.Address;
import pers.xiaomuma.base.dome.domain.Vehicle;
import pers.xiaomuma.base.db.DynamicDataSourceContextHolder;
import pers.xiaomuma.base.db.aop.DataSource;
import pers.xiaomuma.base.dome.service.IAddressService;
import pers.xiaomuma.base.dome.service.IVehicleService;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CombBizService {

    private final IAddressService addressService;
    private final IVehicleService vehicleService;

    //@DataSource(DataSourceName.DOME1)
    public void findAddress() {
        List<Address>  addresses = DynamicDataSourceContextHolder
                .callInDataSource(DataSourceName.DOME1, addressService::list);
        //List<Address> addresses = addressService.list();
        System.out.println(addresses.toString());
    }

    @DataSource(DataSourceName.DOME2)
    public void findVehicle() {
        List<Vehicle> vehicles = vehicleService.list();
        System.out.println(vehicles.toString());
    }
}
