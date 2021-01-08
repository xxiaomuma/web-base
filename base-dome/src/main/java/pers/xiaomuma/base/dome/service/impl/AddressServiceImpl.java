package pers.xiaomuma.base.dome.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.xiaomuma.base.dome.dao.AddressDAO;
import pers.xiaomuma.base.dome.domain.Address;
import pers.xiaomuma.base.dome.service.IAddressService;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressDAO, Address> implements IAddressService {


}
