package com.example.pensionat.Services.Imp;

import com.example.pensionat.Models.Shippers;
import com.example.pensionat.Repositories.ShippersRepo;
import com.example.pensionat.Services.ShippersService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ShippersServiceImp implements ShippersService {

    private final ShippersRepo shippersRepo;
    @Override
    public void addShippersToDB(Shippers shipper) {
        Shippers foundShipper;
        foundShipper = shippersRepo.findAll().stream().filter(s -> Objects.equals(s.getCompanyName(), shipper.getCompanyName())).findFirst().orElse(null);

        if (foundShipper==null) {
            shippersRepo.save(shipper);
            System.out.println(" --- Ett nytt fraktföretag har lagts till: \"" + shipper.getCompanyName() + "\" --- ");
        }
        else {
            System.out.println( "Fraktföretaget " + shipper.getCompanyName() + " fanns redan.");
        }
    }

}
