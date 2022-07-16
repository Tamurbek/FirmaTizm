package com.example.topshiriqfirma.Service;

import com.example.topshiriqfirma.Repository.HodimRepositori;
import com.example.topshiriqfirma.Repository.MaoshRepository;
import com.example.topshiriqfirma.Repository.RollRepository;
import com.example.topshiriqfirma.dto.ApiResponse;
import com.example.topshiriqfirma.dto.GetHodimDto;
import com.example.topshiriqfirma.dto.MaoshDto;
import com.example.topshiriqfirma.enums.RollName;
import com.example.topshiriqfirma.model.Hodimlar;
import com.example.topshiriqfirma.model.IshHaqi;
import com.example.topshiriqfirma.model.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaoshService {

    @Autowired
    RollRepository roleRepository;
    @Autowired
    MaoshRepository maoshRepository;
    @Autowired
    HodimRepositori hodimRepositori;

    public ApiResponse maoshBerish(MaoshDto maoshDto){
        Optional<Hodimlar> optionalStaff1=hodimRepositori.findByEmail(maoshDto.getKimdan());
        if (optionalStaff1.get().getRols().getRollName().equals(RollName.DIRECTOR) || optionalStaff1.get().getRols().getRollName().equals(RollName.MANEGER)) {
            Optional<Hodimlar> optionalStaff = hodimRepositori.findByEmail(maoshDto.getKimga());
            if (optionalStaff.isPresent()){
                IshHaqi maosh=new IshHaqi();
                maosh.setMiqdor(maoshDto.getMiqdor());
                maosh.setHodimlar(optionalStaff.get());
                maoshRepository.save(maosh);
                return new ApiResponse("To`lov muvaffaqiyatli malga oshirildi", true);
            }
            return new ApiResponse("Hodim topilmadi", false);
        }
        return new ApiResponse("To`lov qilish mumkin emas", false);
    }
    public List<IshHaqi> salaryList(GetHodimDto getHodimDto){
        Optional<Roles> optionalRoles=roleRepository.findById(getHodimDto.getId());
        if (optionalRoles.get().getRollName().equals(RollName.DIRECTOR) || optionalRoles.get().getRollName().equals(RollName.MANEGER)){
            return maoshRepository.findAll();
        }
        return null;
    }
}
