package pers.xiaomuma.web.dome.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.xiaomuma.web.dome.dao.AddressDAO;
import pers.xiaomuma.web.dome.domain.Address;
import pers.xiaomuma.web.dome.service.IAddressService;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressDAO, Address> implements IAddressService {


}
